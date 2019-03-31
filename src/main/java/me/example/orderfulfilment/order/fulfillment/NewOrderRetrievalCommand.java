package me.example.orderfulfilment.order.fulfillment;

import me.example.orderfulfilment.order.domain.Order;
import me.example.orderfulfilment.order.domain.OrderService;
import me.example.orderfulfilment.order.domain.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component("newOrderRetrievalCommand")
public class NewOrderRetrievalCommand implements FulfillmentCommand {

   private static final Logger log = LoggerFactory.getLogger(NewOrderRetrievalCommand.class);

   @Inject
   private Environment environment;

   @Inject
   private OrderService orderService;

   @Override
   public void execute(FulfillmentContext context) {
      try {
         loadContext(context);
      } catch (Exception e) {
         log.error(
               "An error occurred while retrieving new orders: "
                     + e.getMessage(), e);
      }
   }

   private void loadContext(FulfillmentContext context) throws Exception {
      int fetchSize = Integer.parseInt(environment
            .getProperty("neworderretrievalcommand.fetchsize"));

      List<Order> newOrders = orderService.getOrderDetails(OrderStatus.NEW, fetchSize);

      orderService.processOrderStatusUpdate(newOrders, OrderStatus.PROCESSING);

      context.setOrderDetails(newOrders);
      context.setFulfillmentCenter1OutboundFolder(environment
            .getProperty("order.fulfillment.center.1.outbound.folder"));
      context.setFulfillmentCenter1FileName(environment
            .getProperty("order.fulfillment.center.1.filename"));
   }
}
