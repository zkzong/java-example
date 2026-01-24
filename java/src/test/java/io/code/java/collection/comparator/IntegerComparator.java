package io.code.java.collection.comparator;

import java.util.Comparator;

/**
 * @Author: zongz
 * @Date: 2024/11/16
 */
public class IntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        if (o2 > o1) {
            return 1;
        } else if (o2 < o1) {
            return -1;
        }
        return 0;
    }

}
