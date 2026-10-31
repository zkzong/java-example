package org.example.sb3.ss.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 8306802022239174861L;

    private long orderId;

    private int orderType;

    private int userId;

    private long addressId;

    private String status;

    @Override
    public String toString() {
        return String.format("order_id: %s, order_type: %s, user_id: %s, address_id: %s, status: %s", orderId, orderType, userId, addressId, status);
    }
}
