package org.example.java.collection.list;

import org.example.java.collection.entity.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Zong on 2016/10/10.
 */
public class ListTest {
    @Test
    public void AddValue() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("id", 1);
        map1.put("name", "zong");
        list.add(map1);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("id", 2);
        map2.put("name", "liu");
        list.add(map2);

        // 遍历修改list中对象的值
        handle(list);

        System.out.println(list);
    }

    private void handle(List<Map<String, Object>> list) {
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            m.put("age", i + 20);
        }
    }

    @Test
    public void add() {
        List<String> strings = new ArrayList<>();
        strings.add("135");
        System.out.println(strings);

        if (strings.contains("135")) {
            strings.add("137");
        }
        System.out.println(strings);


        // 先把map添加到list，再修改map值
        Map<String, String> map = new HashMap<String, String>();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(map);
        map.put("id", "id");
        map.put("name", "name");
        map.put("age", "age");
        System.out.println(list.size());
        System.out.println(list.get(0).get("id"));

        // List list = new ArrayList(10);
        // list.add(1, 1);

        String[] arr = new String[5];
        // null
        System.out.println(arr[0]);
    }

    /**
     * 修改list值
     */
    @Test
    public void modify() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(list);

        list.set(1, "1111");
        System.out.println(list);
    }

    @Test
    public void isEmpty() {
        List list = Collections.emptyList();
        System.out.println(list.isEmpty());
    }

    @Test
    public void contains() {
        List<Integer> list1 = Collections.emptyList();
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);

        boolean b = list2.containsAll(list1);
        System.out.println(b);

    }

    /**
     * 使用工具类Arrays.asList()把数组转换成集合时，不能使用其修改集合相关的方法，它的add/remove/clear方法会抛出UnsupportedOperationException异常。
     * asList() 的返回对象是一个 Arrays 内部类，并没有实现集合的修改方法。
     * 但是可以使用set方法修改元素值
     */
    @Test
    public void asList() {
        String[] str = new String[]{"a", "b"};
        List list = Arrays.asList(str);
        list.add("c"); // 报错
        list.set(0, "aa"); // 不报错
        System.out.println(list);
    }

    @Test
    public void subList() {
        List<String> list = new ArrayList<>();
        list.add("hello");

        // 当原始集合大小没有那么大时，毫无疑问抛异常。
        //java.lang.IndexOutOfBoundsException
        //list.subList(0, 4);

        // 得到一个新的集合，往新集合中增加一条数据。
        List<String> newList = list.subList(0, 1);
        newList.add("zong");

        // 遍历原始集合，竟然 size=2 了，而且往新集合中增加的数据存在于原始集合。
        System.out.println("list size : " + list.size());
        for (String str : list) {
            System.out.println(str);
        }

        // 移除新集合中一条数据，遍历新集合。
        newList.remove("zong");
        for (String str : newList) {
            System.out.println(str);
        }

        System.out.println("-------------------------");
        // 原始集合增加一条数据并遍历。
        list.add("zong");
        for (String str : list) {
            System.out.println(str);
        }

        // 遍历新集合，抛出 ConcurrentModificationException 异常。
        // java.util.ConcurrentModificationException
        for (String str : newList) {
            System.out.println(str);
        }

        // 返回的新集合是靠原来的集合支持的，修改都会影响到彼此对方。
        // 在 subList 场景中，高度注意对原集合元素个数的修改，会导致子列表的遍历、增加、删除均产生异常。

    }

    @Test
    public void toArray() {
        List<String> list = new ArrayList<>(2);
        list.add("guan");
        list.add("bao");

        // 直接使用 toArray() 无参方法返回值只能是 Object[]类，若强转其它类型数组将会抛异常。
        String[] arr = (String[]) list.toArray();
        System.out.println(arr);

        // 使用 <T> T[] toArray(T[] a); 有参数这个方法
        String[] array = new String[list.size()];
        array = list.toArray(array);
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            System.out.println(s);
        }
    }

    /**
     * initialCapacity
     * List设置初始化大小
     */
    @Test
    public void initialCapacity() {
        Student student = null;
        long begin1 = System.currentTimeMillis();
        List<Student> list1 = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            student = new Student(i, "zong", i);
            list1.add(student);
        }
        Long end1 = System.currentTimeMillis();
        System.out.println("list1 time：" + (end1 - begin1)); // 5912

        long begin2 = System.currentTimeMillis();
        List<Student> list2 = new ArrayList<>(10000000);
        for (int i = 0; i < 10000000; i++) {
            student = new Student(i, "zong", i);
            list2.add(student);
        }
        Long end2 = System.currentTimeMillis();
        System.out.println("list2 time：" + (end2 - begin2)); // 928
    }

    @Test
    public void sort() {
        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student(1, "java", 1);
        studentList.add(student1);
        Student student2 = new Student(2, "python", 2);
        studentList.add(student2);
        studentList.sort(
                Comparator.comparing(Student::getName, Comparator.nullsFirst(Comparator.naturalOrder()))
                        .thenComparing(Student::getAge, Comparator.nullsFirst(Comparator.naturalOrder()))
                        .thenComparing(Student::getId, Comparator.nullsLast(Integer::compare))
        );
        System.out.println(studentList);
    }

    /**
     * listIterator
     * 倒序遍历
     */
    @Test
    public void listIterator() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ListIterator<Integer> iterator = list.listIterator(list.size());
        while (iterator.hasPrevious()) {
            Integer value = iterator.previous();
            System.out.println(value);
        }
    }

}
