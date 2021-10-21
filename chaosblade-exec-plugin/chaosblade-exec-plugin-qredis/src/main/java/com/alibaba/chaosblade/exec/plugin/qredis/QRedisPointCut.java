package com.alibaba.chaosblade.exec.plugin.qredis;

import com.alibaba.chaosblade.exec.common.aop.PointCut;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.ClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.NameClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.clazz.OrClassMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.ManyNameMethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.MethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.NameMethodMatcher;
import com.alibaba.chaosblade.exec.common.aop.matcher.method.OrMethodMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class QRedisPointCut implements PointCut {

    private static String[] jedisCommands = {"setex", "ttl", "lrem", "expire", "get", "type", "append", "set", "set", "set", "exists",
            "sort", "sort", "move", "getSet", "zincrby", "zincrby", "lpush", "substr", "rpushx", "decrBy", "hincrBy",
            "hgetAll", "incrBy", "pfcount", "expireAt", "brpop", "brpop", "lrange", "lset", "zcount", "zcount", "zscan",
            "zscan", "zscan", "hscan", "hscan", "hscan", "zrem", "lpushx", "strlen", "zrange", "smembers", "sadd",
            "blpop", "blpop", "lpop", "ltrim", "hlen", "zcard", "echo", "spop", "spop", "rpush", "pfadd", "hvals",
            "hsetnx", "rpop", "hmset", "linsert", "hset", "setnx", "setrange", "hmget", "psetex", "llen", "setbit",
            "setbit", "incr", "getrange", "hget", "getbit", "persist", "bitcount", "bitcount", "pttl", "del", "pexpire",
            "decr", "zadd", "zadd", "zadd", "zadd", "hexists", "lindex", "hkeys", "sismember", "srandmember",
            "srandmember", "srem", "scard", "hdel", "zrevrange", "zrevrank", "zscore", "zrank", "zrangeByScore",
            "zrangeByScore", "zrangeByScore", "zrangeByScore", "zrangeByScoreWithScores", "zrangeByScoreWithScores",
            "zrangeByScoreWithScores", "zrangeByScoreWithScores", "zrangeWithScores", "zremrangeByRank",
            "zremrangeByScore", "zremrangeByScore", "zrevrangeByScore", "zrevrangeByScore", "zrevrangeByScore",
            "zrevrangeByScore", "zrevrangeByScoreWithScores", "zrevrangeByScoreWithScores", "zrevrangeByScoreWithScores",
            "zrevrangeByScoreWithScores", "zrevrangeWithScores", "zlexcount", "zrangeByLex", "zrangeByLex",
            "zremrangeByLex", "sscan", "sscan", "sscan", "bitfield", "bitpos", "bitpos", "geoadd", "geoadd", "geodist",
            "geodist", "geohash", "geopos", "georadius", "georadius", "georadiusByMember", "georadiusByMember",
            "hincrByFloat", "incrByFloat", "pexpireAt", "zrevrangeByLex", "zrevrangeByLex",
            "mset", "mget", "mexpire", "mexpireAt"};
    private static final Set<String> methods = new HashSet<String>(Arrays.asList(jedisCommands));


    @Override
    public ClassMatcher getClassMatcher() {
        return new OrClassMatcher().or(new NameClassMatcher(QRedisConstant.QCLIENT_REDIS_CLASS_NAME))
                .or(new NameClassMatcher("com.qunar.redis.storage.Sedis"))
                .or(new NameClassMatcher("com.qunar.redis.storage.Sedis2"))
                .or(new NameClassMatcher("com.qunar.redis.storage.Sedis3"))
                .or(new NameClassMatcher("com.qunar.redis.storage.AdvancedSedis"))
                .or(new NameClassMatcher("com.qunar.redis.storage.AdvancedSedis2"))
                .or(new NameClassMatcher("com.qunar.redis.storage.AdvancedSedis3"));
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return new OrMethodMatcher().or(new NameMethodMatcher(QRedisConstant.QCLIENT_REDIS_METHOD_NAME))
                .or(new ManyNameMethodMatcher(methods));
    }


}
