package me.example.orderfulfilment.order.fulfillment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class FulfillmentProcessor {

   private static final Logger log = LoggerFactory.getLogger(FulfillmentProcessor.class);

   @Inject
   @Qualifier("newOrderRetrievalCommand")
   private FulfillmentCommand newOrderRetrievalCommand;

   @Inject
   @Qualifier("newOrderSendToFulfillmentCenterOneCommand")
   private FulfillmentCommand newOrderSendToFulfillmentCenterOneCommand;

   /**
    * Orchestrates order fulfillment.
    */
   public void run() {
      FulfillmentContext context = new FulfillmentContext();

      newOrderRetrievalCommand.execute(context);

      newOrderSendToFulfillmentCenterOneCommand.execute(context);
   }

   /**
    * @param newOrderRetrievalCommand
    *           the newOrderRetrievalCommand to set
    */
   public void setNewOrderRetrievalCommand(
         FulfillmentCommand newOrderRetrievalCommand) {
      this.newOrderRetrievalCommand = newOrderRetrievalCommand;
   }

   /**
    * @param newOrderSendToFulfillmentCenterOneCommand
    *           the newOrderSendToFulfillmentCenterOneCommand to set
    */
   public void setNewOrderSendToFulfillmentCenterOneCommand(
         FulfillmentCommand newOrderSendToFulfillmentCenterOneCommand) {
      this.newOrderSendToFulfillmentCenterOneCommand = newOrderSendToFulfillmentCenterOneCommand;
   }

}
