package com.chsi.knowledge.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chsi.cache.memcached.CacheExpireTimeUtil;
import com.chsi.cache.memcached.spy.SpyMemcachedClient;
import com.chsi.cache.memcached.spy.SpyMemcachedClientFactory;
import com.chsi.framework.util.ValidatorUtil;
import com.chsi.knowledge.Constants;

/**
 * 缓存工具
 * @author chenjian
 *
 */
public class MemCachedUtil {
    protected static final Log log = LogFactory.getLog(MemCachedUtil.class);
    private static String memCachePoolName;
    private static String memCacheAppName;
    static {
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            log.error("环境变量未配置propertyPath");
        } else {
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                /*
                 * if (null == in) { log.error("环境变量propertyPath指向的文件不存在"); }
                 */
                Properties properties = new Properties();
                properties.load(in);
                memCachePoolName = properties.getProperty("sys.com.chsi.knowledge.memcache.pool_name");
                memCacheAppName = properties.getProperty("sys.com.chsi.knowledge.memcache.app_name");

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != in) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private static final SpyMemcachedClient cacheClient = SpyMemcachedClientFactory.getSpyMemcachedClient(
            memCachePoolName, memCacheAppName, "com.chsi.cache.cfg.RemotePoolConfigServiceImpl");

    private MemCachedUtil() {

    }
    
    /**
     * 读取缓存
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        if (ValidatorUtil.isNull(key)) {
            throw new IllegalArgumentException("读取缓存:key未赋值");
        }
        return (T) cacheClient.get(key);
    }

    /**
     * 删除缓存
     * @param key
     */
    public static void removeByKey(String key) {
        if (ValidatorUtil.isNull(key)) {
            throw new IllegalArgumentException("删除缓存:key未赋值");
        }
        cacheClient.delete(key);
    }
    
    /**
     * 增加缓存-指定了缓存有效时间,超过有效时间缓存失效
     * @param key
     * @param obj
     * @return
     */
    public static boolean add(String key, Object obj) {
        if (ValidatorUtil.isNull(key)) {
            throw new IllegalArgumentException("增加缓存:key未赋值");
        }
        if (null == obj) {
            throw new IllegalArgumentException("增加缓存:obj未赋值");
        }
        return cacheClient.add(key, obj, CacheExpireTimeUtil.getExpireTimeInHour(Constants.CACHE_EXPIRE_TIME_IN_HOUR));
    }
    
    /**
     * 重置缓存
     * @param key
     * @param obj
     * @return
     */
    public static boolean replace(String key, Object obj) {
        if (ValidatorUtil.isNull(key)) {
            throw new IllegalArgumentException("重置缓存:key未赋值");
        }
        if (null == obj) {
            throw new IllegalArgumentException("重置缓存:obj未赋值");
        }
        return cacheClient.replace(key, obj, CacheExpireTimeUtil.getExpireTimeInHour(Constants.CACHE_EXPIRE_TIME_IN_HOUR));
    }

    /**
     * 设置缓存
     * @param key
     * @param obj
     * @return
     */
    public static boolean set(String key, Object obj) {
        if (StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("设置缓存:key未赋值");
        }
        if (null == obj) {
            throw new IllegalArgumentException("设置缓存:obj未赋值");
        }
        return cacheClient.set(key, obj, CacheExpireTimeUtil.getExpireTimeInHour(Constants.CACHE_EXPIRE_TIME_IN_HOUR));
    }
    
    
}
