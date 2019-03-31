package me.example.orderfulfilment.order.fulfillment;

public interface FulfillmentCommand {

   void execute(FulfillmentContext context);
}
