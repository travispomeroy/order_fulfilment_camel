package me.example.orderfulfilment.order;

import me.example.orderfulfilment.catalogitem.domain.CatalogItem;
import me.example.orderfulfilment.catalogitem.persistence.CatalogItemEntity;
import me.example.orderfulfilment.customer.domain.Customer;
import me.example.orderfulfilment.customer.persistence.CustomerEntity;
import me.example.orderfulfilment.order.domain.Order;
import me.example.orderfulfilment.order.domain.OrderService;
import me.example.orderfulfilment.order.domain.OrderStatus;
import me.example.orderfulfilment.order.fulfillment.FulfillmentProcessor;
import me.example.orderfulfilment.order.persistence.OrderEntity;
import me.example.orderfulfilment.order.persistence.OrderRepository;
import me.example.orderfulfilment.orderitem.domain.OrderItem;
import me.example.orderfulfilment.orderitem.persistence.OrderItemEntity;
import me.example.orderfulfilment.orderitem.persistence.OrderItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class DefaultOrderService implements OrderService {

   private static final Logger log = LoggerFactory.getLogger(DefaultOrderService.class);

   @Inject
   private OrderRepository orderRepository;

   @Inject
   private OrderItemRepository orderItemRepository;

   @Inject
   private FulfillmentProcessor fulfillmentProcessor;

   @Override
   public List<Order> getOrderDetails() {
      List<Order> orders = new ArrayList<>();

      try {
         populateOrderDetails(orders, orderRepository.findAll());
      } catch (Exception e) {
         log.error(
               "An error occurred while retrieving all orders: "
                     + e.getMessage(), e);
      }

      return orders;
   }

   @Override
   public void processOrderFulfillment() {
      try {
         fulfillmentProcessor.run();
      } catch (Exception e) {
         log.error(
               "An error occurred during the execution of order fulfillment processing: "
                     + e.getMessage(), e);
      }
   }

   @Override
   public List<Order> getOrderDetails(OrderStatus orderStatus, int fetchSize) {
      List<Order> orders = new ArrayList<>();

      try {
         populateOrderDetails(orders, orderRepository.findByStatus(
               orderStatus.getCode(), new PageRequest(0, fetchSize)));
      } catch (Exception e) {
         log.error("An error occurred while getting orders by order status: "
               + e.getMessage(), e);
      }

      return orders;
   }

   @Transactional(rollbackOn = Exception.class)
   @Override
   public void processOrderStatusUpdate(List<Order> orders, OrderStatus orderStatus) {
      List<Long> orderIds = new ArrayList<>();
      for (Order order : orders) {
         orderIds.add(order.getId());
      }
      orderRepository.updateStatus(orderStatus.getCode(),
            new Date(System.currentTimeMillis()), orderIds);
      orderItemRepository.updateStatus(orderStatus.getCode(),
            new Date(System.currentTimeMillis()), orderIds);
      for (Order order : orders) {
         order.setStatus(orderStatus.getCode());
      }
   }

   @Override
   public List<OrderItem> getOrderItems(long id) {
      List<OrderItem> orderItems = new ArrayList<>();

      try {
         List<OrderItemEntity> orderItemEntities = orderItemRepository
               .findByOrderId(id);
         populateOrderItems(orderItems, orderItemEntities);
      } catch (Exception e) {
         log.error(
               "An error occurred while retrieving order items for the order id |"
                     + id + "|: " + e.getMessage(), e);
      }
      return orderItems;
   }

   private void populateOrderDetails(List<Order> orders,
         Iterable<OrderEntity> orderEntities) {
      for (OrderEntity entity : orderEntities) {
         CustomerEntity customerEntity = entity.getCustomer();
         Customer customer = new Customer(customerEntity.getId(),
                 customerEntity.getFirstName(), customerEntity.getLastName(),
                 customerEntity.getEmail());
         orders.add(new Order(entity.getId(), customer,
                 entity.getOrderNumber(), entity.getTimeOrderPlaced(), entity
                 .getLastUpdate(), OrderStatus.getOrderStatusByCode(
                 entity.getStatus()).getDescription()));
      }
   }

   private void populateOrderItems(List<OrderItem> orderItems,
         Iterable<OrderItemEntity> orderItemEntities) {
      for (OrderItemEntity entity : orderItemEntities) {
         CatalogItemEntity catalogItemEntity = entity.getCatalogItem();
         CatalogItem catalogItem = new CatalogItem(catalogItemEntity.getId(),
                 catalogItemEntity.getItemNumber(),
                 catalogItemEntity.getItemName(), catalogItemEntity.getItemType());
         orderItems.add(new OrderItem(entity.getId(), catalogItem, entity
                 .getStatus(), entity.getPrice(), entity.getLastUpdate(), entity
                 .getQuantity()));
      }
   }
}
