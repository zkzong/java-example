package io.code.java.collection.comparator;

import lombok.Data;

/**
 * @Author: zongz
 * @Date: 2024/11/16
 */
@Data
public class StudentComparable implements Comparable {
    private String name;
    private Integer score;

    public StudentComparable(String name, Integer score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        StudentComparable n = (StudentComparable) o;
        int a = score.compareTo(n.score);
        return (a != 0 ? a : name.compareTo(n.name));
    }

    @Override
    public String toString() {
        return "StudentComparable{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

}
