package io.code.java.collection.comparator;

import java.util.Comparator;

/**
 * 可以使用匿名内部类
 *
 * @Author: zongz
 * @Date: 2024/11/16
 */
public class MapKeyComparator implements Comparator<String> {
    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
