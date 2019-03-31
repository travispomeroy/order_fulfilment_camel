package me.example.orderfulfilment.order.domain;

import me.example.orderfulfilment.customer.domain.Customer;

import java.util.Date;

public class Order {

    private long id;
    private Customer customer;
    private String orderNumber;
    private Date timeOrderPlaced;
    private Date lastUpdate;
    private String status;

    Order() {
    }

    Order(long id, Customer customer, String orderNumber,
          Date timeOrderPlaced, Date lastUpdate, String status) {
        super();
        this.id = id;
        this.customer = customer;
        this.orderNumber = orderNumber;
        this.timeOrderPlaced = timeOrderPlaced;
        this.lastUpdate = lastUpdate;
        this.status = status;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    Customer getCustomer() {
        return customer;
    }

    void setCustomer(Customer customer) {
        this.customer = customer;
    }

    String getOrderNumber() {
        return orderNumber;
    }

    void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    Date getTimeOrderPlaced() {
        return timeOrderPlaced;
    }

    void setTimeOrderPlaced(Date timeOrderPlaced) {
        this.timeOrderPlaced = timeOrderPlaced;
    }

    Date getLastUpdate() {
        return lastUpdate;
    }

    void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Order [id=");
        builder.append(id);
        builder.append(", ");
        if (customer != null) {
            builder.append("customer=");
            builder.append(customer);
            builder.append(", ");
        }
        if (orderNumber != null) {
            builder.append("orderNumber=");
            builder.append(orderNumber);
            builder.append(", ");
        }
        if (timeOrderPlaced != null) {
            builder.append("timeOrderPlaced=");
            builder.append(timeOrderPlaced);
            builder.append(", ");
        }
        if (lastUpdate != null) {
            builder.append("lastUpdate=");
            builder.append(lastUpdate);
            builder.append(", ");
        }
        if (status != null) {
            builder.append("status=");
            builder.append(status);
            builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }
}
