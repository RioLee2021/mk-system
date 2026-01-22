package net.system.mk.commons.redis.lock;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author USER
 */
@Aspect
public class DistributedLockAspect {

    @Resource
    private IDistributedLock distributedLock;

    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(DistributedLock)")
    public Object around(ProceedingJoinPoint point)throws Throwable{
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DistributedLock ann = AnnotationUtils.findAnnotation(method,DistributedLock.class);
        assert ann != null;
        String lockKey = this.getLockKey(point,ann);
        ILock lockObj = null;
        try {
            // 加锁，tryLok = true,并且tryTime > 0时，尝试获取锁，获取不到超时异常
            if (ann.tryLok()) {
                if(ann.tryTime() <= 0){
                    throw new GlobalException(GlobalErrorCode.PARAMS_NOT_LEGAL);
                }
                lockObj = tryLock(lockKey,ann);
            } else {
                lockObj = lock(lockKey,ann);
            }

            if (Objects.isNull(lockObj)) {
                throw new GlobalException(GlobalErrorCode.TRANS_LOCKED,"事务："+lockKey+"已被锁定");
            }
            return point.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            // 解锁
            this.unLock(lockObj);
        }
    }

    private ILock tryLock(String key,DistributedLock ann) throws Exception {
        return this.distributedLock.tryLock(key,ann.tryTime(),ann.lockTime(),ann.unit(),ann.fair());
    }

    private ILock lock(String key,DistributedLock ann)throws Exception{
        return this.distributedLock.lock(key,ann.lockTime(),ann.unit(),ann.fair());
    }

    private void unLock(ILock lock){
        if (Objects.isNull(lock)){
            return;
        }
        this.distributedLock.unLock(lock);
    }

    private String getLockKey(ProceedingJoinPoint pjp,DistributedLock distributedLock){
        String lockKey = distributedLock.key();
        String keyPrefix = distributedLock.keyPrefix();
        if (StringUtils.isBlank(lockKey)){
            throw new GlobalException(GlobalErrorCode.PARAMS_EMPTY);
        }
        if (lockKey.contains("#")){
            this.checkSpEL(lockKey);
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Object[] args = pjp.getArgs();
            lockKey = getValBySpEL(lockKey,methodSignature,args);
        }
        return StringUtils.isBlank(keyPrefix)?lockKey:keyPrefix+lockKey;
    }

    private String getValBySpEL(String spEL, MethodSignature methodSignature,Object[] args){
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames==null||paramNames.length<1){
            throw new GlobalException(GlobalErrorCode.PARAMS_EMPTY);
        }
        Expression expression = spelExpressionParser.parseExpression(spEL);
        EvaluationContext context = new StandardEvaluationContext();
        for (int i=0;i<args.length;i++){
            context.setVariable(paramNames[i],args[i]);
        }
        Object value = expression.getValue(context);
        if (value==null){
            throw new GlobalException(GlobalErrorCode.PARAMS_EMPTY);
        }
        return value.toString();
    }

    private void checkSpEL(String spEL){
        try {
            ExpressionParser parser = new SpelExpressionParser();
            parser.parseExpression(spEL,new TemplateParserContext());
        } catch (ParseException e) {
            throw new GlobalException(GlobalErrorCode.PARAMS_NOT_LEGAL);
        }
    }
}
