package me.example.orderfulfilment.routes;

import me.example.orderfulfillment.generated.FulfillmentClient;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.MediaSize;

@Component
public class QueueRouter extends RouteBuilder {

    private static final String
            NAME_SPACE = "http://www.pluralsight.com/orderfulfillment/Order";

    private static final Namespaces NAMESPACES = new Namespaces("o", NAME_SPACE);
    private static final String ORDER_ITEM_PROCESSING_QUEUE = "activemq:queue" +
            ":ORDER_ITEM_PROCESSING";
    private static final String ABC_FULFILLMENT_REQUEST_QUEUE = "activemq:queue:" +
            "ABC_FULFILLMENT_REQUEST";
    private static final String FC1_FULFILLMENT_REQUEST_QUEUE = "activemq:queue:" +
            "FC1_FULFILLMENT_REQUEST";
    private static final String ERROR_FULFILLMENT_REQUEST = "activemq:queue:" +
            "ERROR_FULFILLMENT_REQUEST";

    private static final String ORDER_TYPE_XPATH = "/o:Order/o:OrderType/o:FulfillmentCenter";

    @Override
    public void configure() throws Exception {
        from(ORDER_ITEM_PROCESSING_QUEUE)
                .choice()
                .when()
                    .xpath(ORDER_TYPE_XPATH + " = '" + FulfillmentClient.ABC_FULFILLMENT_CENTER.value() + "'", NAMESPACES)
                    .to(ABC_FULFILLMENT_REQUEST_QUEUE)
                .when()
                    .xpath(ORDER_TYPE_XPATH + " = '" + FulfillmentClient.FULFILLMENT_CENTER_ONE.value() + "'", NAMESPACES)
                    .to(FC1_FULFILLMENT_REQUEST_QUEUE)
                .otherwise()
                    .to(ERROR_FULFILLMENT_REQUEST);

    }
}
