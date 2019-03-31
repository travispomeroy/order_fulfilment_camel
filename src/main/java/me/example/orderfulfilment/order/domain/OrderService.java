package me.example.orderfulfilment.order.domain;

import me.example.orderfulfilment.orderitem.domain.OrderItem;

import java.util.List;

public interface OrderService {

   List<Order> getOrderDetails();

   List<Order> getOrderDetails(OrderStatus orderStatus, int fetchSize);

   void processOrderFulfillment();

   void processOrderStatusUpdate(List<Order> orders, OrderStatus orderStatus)
         throws Exception;

   List<OrderItem> getOrderItems(long id);
}
