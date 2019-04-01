package me.example.orderfulfilment.order;

import me.example.orderfulfilment.order.domain.Order;
import me.example.orderfulfilment.order.domain.OrderService;
import me.example.orderfulfilment.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class OrderController {

   private static final Logger log = LoggerFactory.getLogger(OrderController.class);

   private final OrderService orderService;

   public OrderController(OrderService orderService) {
      this.orderService = orderService;
   }

   @RequestMapping(value = "/orderHome")
   public String orderHome() {
      return "orderHome";
   }

   @RequestMapping(value = "/processOrders", method = RequestMethod.GET)
   public ResponseEntity<String> processOrdersRender() {
      return ResponseEntity.ok("Got the Order!!!");
   }

   @RequestMapping(value = "/processOrders", method = RequestMethod.POST)
   public ResponseEntity<List<Order>> processOrders() {
      orderService.processOrderFulfillment();
      List<Order> orderDetails = orderService.getOrderDetails();
      return ResponseEntity.ok(orderDetails);
   }

   @RequestMapping(value = "/resetOrders", method = RequestMethod.GET)
   public String resetOrdersRender() {
      return "resetOrders";
   }

   @RequestMapping(value = "/resetOrders", method = RequestMethod.POST)
   public ResponseEntity<List<Order>> resetOrders() {
      List<Order> orders = orderService.getOrderDetails(OrderStatus.PROCESSING,
            100);
      try {
         orderService.processOrderStatusUpdate(orders, OrderStatus.NEW);
      } catch (Exception e) {
         log.error(
               "An error occurred while resetting orders: " + e.getMessage(), e);
      }

      List<Order> orderDetails = orderService.getOrderDetails();
      return ResponseEntity.ok(orderDetails);
   }

   @RequestMapping(value = "/viewOrders", method = RequestMethod.GET)
   public ResponseEntity<List<Order>> viewOrders() {
      List<Order> orderDetails = orderService.getOrderDetails();
      return ResponseEntity.ok(orderDetails);
   }
}
