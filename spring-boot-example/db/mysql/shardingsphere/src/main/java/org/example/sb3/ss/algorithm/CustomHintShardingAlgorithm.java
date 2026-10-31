package org.example.sb3.ss.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomHintShardingAlgorithm implements HintShardingAlgorithm<Long> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, HintShardingValue<Long> shardingValue) {
        return List.of();
    }
}
