package org.example.sb3.ss.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {

        Map<String, Collection<Long>> columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Map<String, Range<Long>> columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();

        Collection<Long> orderIdColl = columnNameAndShardingValuesMap.get("order_id");
        Range<Long> userIdRange = columnNameAndRangeValuesMap.get("user_id");

        String logicTableName = shardingValue.getLogicTableName();

        return List.of();
    }
}
