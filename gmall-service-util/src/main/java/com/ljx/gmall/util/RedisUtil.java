package com.ljx.gmall.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    private JedisPool jedisPool;
    private static Log logger = LogFactory.getLog(RedisUtil.class);

    public void initPool(String host, int port, int database) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大连接数
        poolConfig.setMaxTotal(50);
        //最小空闲连接数
        poolConfig.setMaxIdle(10);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(10 * 1000);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(10);
        jedisPool = new JedisPool(poolConfig, host, port, 20 * 1000);
    }

    /**
     * 获取Jedis对象
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        Jedis jedis = null;
        if (jedisPool != null) {
            try {
                if (jedis == null) {
                    jedis = jedisPool.getResource();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return jedis;
    }

}
