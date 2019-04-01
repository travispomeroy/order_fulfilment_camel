package me.example.orderfulfilment.routes;

import me.example.orderfulfilment.order.domain.OrderStatus;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PollDatabaseAndLogRoute extends RouteBuilder {

    @Override
    public void configure() {
        String query =
                "select id from orders.\"order\" where status ='" + OrderStatus.NEW.getCode() + "'";
        String onConsume = "?consumer.onConsume=";
        String update =
                "update orders.\"order\" set status = '" + OrderStatus.PROCESSING.getCode() + "' where" +
                        " id = :#id";
        String toLog = "log:travis?level=INFO";

        String toActiveMq = "activemq:queue:ORDER_ITEM_PROCESSING";

        from("sql:" + query + onConsume + update)
                .bean("orderItemMessageTranslator", "transformToOrderItemMessage")
                .to(toActiveMq);
    }
}
