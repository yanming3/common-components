package com.fansz.redis;

import com.fansz.redis.support.JedisCallback;
import com.wandoulabs.jodis.JedisResourcePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class JedisTemplate implements JedisOperations {

    private static final Logger logger = LoggerFactory.getLogger(JedisTemplate.class);

    private JedisResourcePool jedisPool;

    public Jedis getRedisClient() {
        try {
            Jedis shardJedis = jedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            logger.error("can't get jedit from pool!", e);
        }
        return null;
    }

    public void returnResource(Jedis jedis) {
        jedis.close();
    }

    public <T> T execute(JedisCallback<T> redisCallback) {
        Jedis jedis = getRedisClient();
        if (jedis == null) {
            return null;
        }
        try {
            return redisCallback.doInRedis(jedis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                throw new RuntimeException(e);
            }
        } finally {
            returnResource(jedis);
        }
    }

    public void setJedisPool(JedisResourcePool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
