package me.example.orderfulfilment.orderitem;

import me.example.orderfulfilment.order.domain.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;

@Component
public class OrderItemMessageTranslator {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderItemMessageTranslator.class);

    @Inject
    private OrderService orderService;

    public String transformToOrderItemMessage(Map<String, Object> orderIds) {
        try {
            validateOrderIds(orderIds);
            return orderService.processCreateOrderMessage((Long)orderIds.get("id"));
        } catch (Exception e) {
            logger.error("Order processing failed.", e);
        }

        return "";
    }

    private void validateOrderIds(Map<String, Object> orderIds) throws Exception {
        isNonNull(orderIds);
        hasIdAsAKey(orderIds);
        idKeyHasNonNullValueAndValueIsALong(orderIds);
    }

    private void isNonNull(Map<String, Object> orderIds) throws Exception {
        if (orderIds == null) {
            throw new Exception("Order is was not bound to the method via integration " +
                    "framework");
        }
    }

    private void hasIdAsAKey(Map<String, Object> orderIds) throws Exception {
        if (!orderIds.containsKey("id")) {
            throw new Exception("Could not find a valid key of 'id' for the order id");
        }
    }

    private void idKeyHasNonNullValueAndValueIsALong(Map<String, Object> orderIds) throws Exception {
        if (orderIds.get("id") == null || !(orderIds.get("id") instanceof Long)) {
            throw new Exception("The order id was not correctly provided or formatted.");
        }
    }
}
