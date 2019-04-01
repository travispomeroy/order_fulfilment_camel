package me.example.orderfulfilment.routes;

import me.example.orderfulfilment.OrderFulfilmentApplication;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.EnableRouteCoverage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = OrderFulfilmentApplication.class)
@EnableRouteCoverage
public class PollDatabaseAndLogRouteTest {

    @Autowired
    private CamelContext camelContext;

    @Test
    public void shouldProduceLog() {
        NotifyBuilder notifyBuilder = new NotifyBuilder(camelContext).whenDone(1).create();

        assertThat(notifyBuilder.matches(10, TimeUnit.SECONDS)).isTrue();
    }
}