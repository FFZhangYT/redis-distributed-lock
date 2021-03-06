package com.snowalker.lock.redisson.config.strategy;

import com.snowalker.lock.redisson.constant.GlobalConstant;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author snowalker
 * @date 2018/7/12
 * @desc 主从方式Redisson配置
 * 连接方式：主节点,子节点,子节点
 *          格式为: 127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381
 */
public class MasterslaveRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(String address) {
        Config config = new Config();
        try {
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];
            /**设置主节点ip*/
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            /**设置从节点，移除第一个节点，默认第一个为主节点*/
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add(GlobalConstant.REDIS_CONNECTION_PREFIX.getConstant_value() + addrToken);
            }
            slaveList.remove(0);

            config.useMasterSlaveServers().addSlaveAddress((String[]) slaveList.toArray());
            LOGGER.info("初始化[MASTERSLAVE]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            LOGGER.error("MASTERSLAVE Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }

    @Override
    public Config createRedissonConfig(String address, String password) {
        return null;
    }

    @Override
    public Config createRedissonConfig(String address, String password, int database) {
        return null;
    }

}
