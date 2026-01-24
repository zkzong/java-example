package io.code.lang3;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.MutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;

/**
 * @Author: zongz
 * @Date: 2026-01-24
 */
public class TripleTest {

    @Test
    public void testTriple() {
        Triple<Integer, String, Integer> triple = Triple.of(1, "io", 20);
        System.out.println(triple);
        System.out.println(triple.getLeft());
        System.out.println(triple.getMiddle());
        System.out.println(triple.getRight());

        Triple<Integer, String, Integer> mutableTriple = new MutableTriple<>(1, "io", 20);
        System.out.println(mutableTriple);
        System.out.println(mutableTriple.getLeft());
        System.out.println(mutableTriple.getMiddle());
        System.out.println(mutableTriple.getRight());

        Triple<Integer, String, Integer> immutableTriple = new ImmutableTriple<>(2, "code", 30);
        System.out.println(immutableTriple);
        System.out.println(immutableTriple.getLeft());
        System.out.println(immutableTriple.getMiddle());
        System.out.println(immutableTriple.getRight());
    }

    @Test
    public void testTriple2() {
        A a = A.builder().id(1L).build();
        B b = B.builder().name("io").build();
        C c = C.builder().age(20).build();
        Triple<A, B, C> triple = Triple.of(a, b, c);
        System.out.println(triple);
        System.out.println(triple.getLeft());
        System.out.println(triple.getMiddle());
        System.out.println(triple.getRight());
    }
}
