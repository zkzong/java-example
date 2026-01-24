package io.code.java.collection.map;

import io.code.java.collection.comparator.MapKeyComparator;
import io.code.java.collection.entity.Country;
import io.code.java.collection.entity.ObjectMap;
import io.code.java.collection.entity.Student;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by Zong on 2016/7/14.
 */
public class MapTest {
    public static void main(String[] args) {
        //key相同时，后put的值会覆盖之前put的值
        Map map = new HashMap();
        map.put(1, 10);
        map.put(1, 11);
        System.out.println(map);

        Map m = new HashMap(16);
        m.put(null, 100);
        m.put("", 100);
        System.out.println(m);
    }

    @Test
    public void replace() {
        String s = "<html><body>${name}-${age}-${name}</body></html>";

        Map<String, String> m = new HashMap<>();
        m.put("name", "zong");
        m.put("age", "30");

        for (Map.Entry<String, String> entry : m.entrySet()) {
            s = s.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        System.out.println(s);
    }

    @Test
    public void sortKey() {
        Map<String, String> map = new TreeMap<>(
                // Comparator可以对集合对象或者数组进行排序的比较器接口，实现该接口的public compare(T o1,To2)方法即可实现排序，
                // 该方法主要是根据第一个参数o1,小于、等于或者大于o2分别返回负整数、0或者正整数。
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o2.compareTo(o1);
                    }
                }
        );
        map.put("a", "a");
        map.put("c", "c");
        map.put("d", "d");
        map.put("b", "b");

        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            System.out.println(key + " : " + map.get(key));
        }
    }

    // http://www.importnew.com/20703.html
    @Test
    public void sortValue() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("d", "d");
        map.put("b", "b");
        map.put("a", "a");
        map.put("c", "c");

        // 对value排序我们就需要借助于Collections的sort(List<T> list, Comparator<? super T> c)方法，该方法根据指定比较器产生的顺序对指定列表进行排序。
        // 但是有一个前提条件，那就是所有的元素都必须能够根据所提供的比较器来进行比较。
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (Map.Entry<String, String> mapping : list) {
            System.out.println(mapping.getKey() + " : " + mapping.getValue());
        }
    }

    @Test
    public void sort() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("KFC", "kfc");
        map1.put("WNBA", "wnba");
        map1.put("NBA", "nba");
        map1.put("CBA", "cba");

        // 按Key进行排序
        Map<String, String> resultMap = sortMapByKey(map1);
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }

        // HashMap的值是没有顺序的，他是按照key的HashCode来实现的。
        Map<String, String> map2 = new HashMap<String, String>();
        //map.put("d", "d");
        //map.put("b", "b");
        //map.put("a", "a");
        //map.put("c", "c");
        for (int i = 0; i < 10; i++) {
            String random = UUID.randomUUID().toString();
            System.out.println(random + "   " + random.hashCode());
            map2.put(random, random);
        }
        System.out.println(map2);

        List<Map.Entry<String, String>> list = new ArrayList<>(map2.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            //升序排序
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (Map.Entry<String, String> mapping : list) {
            System.out.println(mapping.getKey() + " : " + mapping.getValue());
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    @Test
    public void objectMap() {
        ObjectMap objectMap = new ObjectMap();
        objectMap.setName("zong");
        objectMap.setAge(30);
        Map<String, String> map = new HashMap<>(4);
        map.put("a", "a");
        map.put("b", "b");
        objectMap.setMap(map);
        System.out.println(objectMap);
    }

    @Test
    public void keyObject() {
        Student s1 = new Student(1, "zong", 30);
        Student s2 = new Student(1, "zong", 30);
        Map m = new HashMap();
        // 不会覆盖
        m.put(s1, 1);
        m.put(s2, 2);

        m.remove(s2);
        System.out.println(m);

        HashMap<Country, String> countryCapitalMap = new HashMap<>();
        Country india = new Country("India", 1000);
        countryCapitalMap.put(india, "Delhi");
        Country japan = new Country("Japan", 10000);
        countryCapitalMap.put(japan, "Tokyo");
        Country france = new Country("France", 2000);
        countryCapitalMap.put(france, "Paris");
        Country russia = new Country("Russia", 20000);
        countryCapitalMap.put(russia, "Moscow");
        Iterator<Country> countryCapitalIter = countryCapitalMap.keySet().iterator();
        while (countryCapitalIter.hasNext()) {
            Country countryObj = countryCapitalIter.next();
            String capital = countryCapitalMap.get(countryObj);
            System.out.println(countryObj.getName() + "----" + capital);
        }
    }

}
