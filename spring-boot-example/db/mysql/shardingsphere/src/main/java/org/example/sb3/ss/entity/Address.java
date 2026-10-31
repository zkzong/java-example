package org.example.sb3.ss.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 4743102234543827855L;

    private Long addressId;

    private String addressName;

    @Override
    public String toString() {
        return String.format("address_id: %s, address_name: %s", addressId, addressName);
    }
}
