package io.code.java.collection.set;

import io.code.java.collection.comparator.IntegerComparator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Zong on 2016/11/14.
 */
public class TreeSetTest {
    @Test
    public void sort() {
        Set<Integer> set = new TreeSet<>(new IntegerComparator());
        set.add(3);
        set.add(5);
        set.add(4);
        set.add(2);

        System.out.println(set);
    }
}
