package me.example.orderfulfilment.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class CopyFileToTestDirRoute extends RouteBuilder {

    @Inject
    private Environment environment;

    @Override
    public void configure() throws Exception {
        String dir = environment.getProperty("order.fulfillment.center.1.outbound.folder");

        from("file://" + dir + "?noop=true").to("file://" + dir + "/test");
    }
}
