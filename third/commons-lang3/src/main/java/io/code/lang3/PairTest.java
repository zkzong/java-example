package io.code.lang3;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

/**
 * @Author: zongz
 * @Date: 2026-01-24
 */
public class PairTest {

    @Test
    public void pairTest() {
        Pair<String, Integer> pair = Pair.of("a", 1);
        System.out.println(pair);
        System.out.println(pair.getKey());
        System.out.println(pair.getValue());
        System.out.println(pair.getLeft());
        System.out.println(pair.getRight());

        Pair<String, Integer> immutablePair = new ImmutablePair<>("a", 1);
        System.out.println(immutablePair);
        System.out.println(immutablePair.getKey());
        System.out.println(immutablePair.getValue());
        System.out.println(immutablePair.getLeft());
        System.out.println(immutablePair.getRight());
    }

    @Test
    public void pairTest2() {
        A a = A.builder().id(1L).build();
        B b = B.builder().name("io").build();
        Pair<A, B> pair = Pair.of(a, b);
        System.out.println(pair);
        System.out.println(pair.getKey());
        System.out.println(pair.getValue());
        System.out.println(pair.getLeft());
        System.out.println(pair.getRight());
    }

}
