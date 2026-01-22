package net.system.mk.commons.constant;

/**
 * @date   2024年8月22日 下午2:01:39  
 */
public class RedisCodeKey {

	
	/**
	 * 后端特有的key
	 */
	public static class Backend {
		
		/**
		 * hash backend 用户登陆token，field：token，value：登录账号json数据
		 */
		public static final String BACKEND_USER_LOGIN_TOKEN = "backend:perm:user:login:token";
		

		/**
		 * hash backend 用户登录授权列表，field：userId，value：授权列表
		 */
		public static final String BACKEND_USER_LOGIN_TOKEN_PRIVILEGE = "backend:perm:user:login:token:privilege";
		

	}

	public static class Device {

		public static final String DEVICE_LOGIN_TOKEN = "device:login:token";

		public static final String DEVICE_UID_TOKEN  = "device:uid:token";

	}

	public static class Web {
		public static final String WEB_USER_LOGIN_TOKEN  = "web:user:login:token";
	}

	/**
	 * 前后端共同使用的key
	 *
	 */
	public static class Common {
		
		public static final String COMMON_TASK_ONLINE = "common:task:online:";

		/**
		 * 限流
		 */
		public static final String COMMON_RATE_LIMIT = "common:rate:limit:";

		/**
		 * 通用重复提交锁
		 */
		public static final String COMMON_REPEAT_SUBMIT = "common:repeat:submit:";

		public static final String MEMBER_UID_OTP  = "member:uid:otp";


		public static final String TG_BOT_COUNT = "tg:bot:count";

        public static final String UPI_LOCK_TIMES = "upi:lock:times";

        public static final String LAST_CALLBACK_TIMESTAMP = "last:callback:timestamp";
    }
	
	
	

	
}