package org.example.sb3.ss.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1332162822494069342L;

    private long orderItemId;

    private long orderId;

    private int userId;

    private String phone;

    private String status;

    @Override
    public String toString() {
        return String.format("order_item_id:%s, order_id: %s, user_id: %s, phone: %s, status: %s", orderItemId, orderId, userId, phone, status);
    }
}
