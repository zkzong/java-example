package org.example.sb3.ss.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;

public class CustomDatabaseShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    //private Properties props = new Properties();

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        // 根据订单ID的哈希值模2决定分库
        long orderId = shardingValue.getValue();
        int hash = Long.hashCode(orderId);
        int dbIndex = Math.abs(hash) % 2;

        for (String each : availableTargetNames) {
            if (each.endsWith(String.valueOf(dbIndex))) {
                return each;
            }
        }
        throw new IllegalArgumentException("未找到匹配的数据库");
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        // 范围查询时返回所有库
        return availableTargetNames;
    }

    //@Override
    //public Properties getProps() {
    //    return props;
    //}
    //
    //@Override
    //public void init(Properties props) {
    //    this.props = props;
    //}

    @Override
    public String getType() {
        return "CUSTOM_DB_SHARDING";
    }
}
