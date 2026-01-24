package io.code.java.collection.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: zkzong
 * @Date: 2018.10.9
 */
@Getter
@Setter
public class ObjectMap implements Serializable {

    private String name;
    private int age;
    private Map<String, String> map;

}
