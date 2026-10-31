package org.example.sb3.ss.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;

public class CustomTableShardingAlgorithm implements StandardShardingAlgorithm<Long> {

    //private Properties props = new Properties();
    // 每个库4张表
    private static final int TABLE_COUNT = 2;

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        // 根据订单ID决定分表
        long orderId = shardingValue.getValue();
        long tableSuffix = orderId % TABLE_COUNT;

        for (String each : availableTargetNames) {
            if (each.endsWith(String.valueOf(tableSuffix))) {
                return each;
            }
        }
        throw new IllegalArgumentException("未找到匹配的数据表");
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Long> shardingValue) {
        // 范围查询时返回所有表
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
        return "CUSTOM_TABLE_SHARDING";
    }
}
