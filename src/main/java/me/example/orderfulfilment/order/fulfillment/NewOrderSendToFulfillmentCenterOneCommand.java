package me.example.orderfulfilment.order.fulfillment;

import liquibase.util.csv.CSVWriter;
import me.example.orderfulfilment.order.domain.Order;
import me.example.orderfulfilment.order.domain.OrderService;
import me.example.orderfulfilment.orderitem.domain.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component("newOrderSendToFulfillmentCenterOneCommand")
public class NewOrderSendToFulfillmentCenterOneCommand implements
      FulfillmentCommand {

   private static final Logger log = LoggerFactory
         .getLogger(NewOrderSendToFulfillmentCenterOneCommand.class);

   private final OrderService orderService;

   public NewOrderSendToFulfillmentCenterOneCommand(OrderService orderService) {
      this.orderService = orderService;
   }

   @Override
   public void execute(FulfillmentContext context) {
      final List<String[]> orderFulfillmentLines = new ArrayList<String[]>();

      buildOrderFulfillmentLines(orderFulfillmentLines, context.getOrderDetails());

      writeOrderFulfillmentLines(orderFulfillmentLines,
            context.getFulfillmentCenter1OutboundFolder(),
            context.getFulfillmentCenter1FileName());
   }

   private void buildOrderFulfillmentLines(
         List<String[]> orderFulfillmentLines, List<Order> orderDetails) {
      for (Order order : orderDetails) {
         List<OrderItem> orderItems = orderService.getOrderItems(order.getId());

         for (OrderItem orderItem : orderItems) {
            orderFulfillmentLines.add(buildOrderFulfillmentLine(order,
                  orderItem));
         }
      }
   }

   private String[] buildOrderFulfillmentLine(Order order, OrderItem orderItem) {
      final String[] line = new String[5];
      line[0] = order.getOrderNumber();
      line[1] = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").format(order
            .getTimeOrderPlaced());
      line[2] = orderItem.getCatalogItem().getItemNumber();
      line[3] = NumberFormat.getCurrencyInstance(Locale.US).format(
            orderItem.getPrice().doubleValue());
      line[4] = Integer.toString(orderItem.getQuantity());
      return line;
   }

   private void writeOrderFulfillmentLines(
         List<String[]> orderFulfillmentLines,
         String fulfillmentCenter1OutboundFolder,
         String fulfillmentCenter1FileName) {
      CSVWriter writer = null;
      try {
         String filePath = fulfillmentCenter1OutboundFolder
               + "/"
               + fulfillmentCenter1FileName
               + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System
                     .currentTimeMillis())) + ".csv";
         File file = new File(filePath);
         if (!file.exists()) {
            file.createNewFile();
         }
         writer = new CSVWriter(new FileWriter(file));
         writer.writeAll(orderFulfillmentLines);
      } catch (Exception e) {
         log.error(
               "An error occurred while writing out to a file for fulfillment center 1: "
                     + e.getMessage(), e);
      } finally {
         if (writer != null) {
            try {
               writer.close();
            } catch (Exception e) {
            }
         }
      }
   }
}
