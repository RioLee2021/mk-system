package net.system.mk.commons.ip2region;

import cn.hutool.core.io.resource.ClassPathResource;
import net.system.mk.commons.conf.AppConfig;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author USER
 */
@Component
public class IpSearcher {

    private static Searcher searcher;

    @Resource
    private AppConfig config;

    @PostConstruct
    public void load() throws IOException {
        String dbPath;
        if (config.getIpXdbPath()!=null){
            dbPath = config.getIpXdbPath();
        }else {
            dbPath = new ClassPathResource("ip2region.xdb").getAbsolutePath();
        }
        byte[] cBuff = Searcher.loadContentFromFile(dbPath);
        searcher = Searcher.newWithBuffer(cBuff);
    }

    @PreDestroy
    public void close() throws IOException {
        if (searcher!=null){
            searcher.close();
        }
    }

    public String search(String ip){
        try {
           return searcher.search(ip);
        } catch (Exception e) {
           return "unknown";
        }
    }
}
