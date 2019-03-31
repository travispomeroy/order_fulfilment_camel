package me.example.orderfulfilment.orderitem.domain;

import me.example.orderfulfilment.catalogitem.domain.CatalogItem;

import java.math.BigDecimal;
import java.util.Date;

class OrderItem {

    private long id;
    private CatalogItem catalogItem;
    private String status;
    private BigDecimal price;
    private Date lastUpdate;
    private int quantity;

    OrderItem() {

    }

    OrderItem(long id, CatalogItem catalogItem, String status, BigDecimal price,
              Date lastUpdate, int quantity) {
        super();
        this.id = id;
        this.catalogItem = catalogItem;
        this.status = status;
        this.price = price;
        this.lastUpdate = lastUpdate;
        this.quantity = quantity;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    CatalogItem getCatalogItem() {
        return catalogItem;
    }

    void setCatalogItem(CatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    BigDecimal getPrice() {
        return price;
    }

    void setPrice(BigDecimal price) {
        this.price = price;
    }

    Date getLastUpdate() {
        return lastUpdate;
    }

    void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    int getQuantity() {
        return quantity;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderItem [id=");
        builder.append(id);
        builder.append(", ");
        if (catalogItem != null) {
            builder.append("catalogItem=");
            builder.append(catalogItem);
            builder.append(", ");
        }
        if (status != null) {
            builder.append("status=");
            builder.append(status);
            builder.append(", ");
        }
        if (price != null) {
            builder.append("price=");
            builder.append(price);
            builder.append(", ");
        }
        if (lastUpdate != null) {
            builder.append("lastUpdate=");
            builder.append(lastUpdate);
            builder.append(", ");
        }
        builder.append("quantity=");
        builder.append(quantity);
        builder.append("]");
        return builder.toString();
    }
}
