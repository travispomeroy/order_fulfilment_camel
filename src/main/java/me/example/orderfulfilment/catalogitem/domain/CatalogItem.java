package me.example.orderfulfilment.catalogitem.domain;

public class CatalogItem {

    private long id;
    private String itemNumber;
    private String itemName;
    private String itemType;

    CatalogItem() {
    }

    CatalogItem(long id, String itemNumber, String itemName, String itemType) {
        this.id = id;
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemType = itemType;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    String getItemNumber() {
        return itemNumber;
    }

    void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    String getItemName() {
        return itemName;
    }

    void setItemName(String itemName) {
        this.itemName = itemName;
    }

    String getItemType() {
        return itemType;
    }

    void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CatalogItem [id=");
        builder.append(id);
        builder.append(", ");
        if (itemNumber != null) {
            builder.append("itemNumber=");
            builder.append(itemNumber);
            builder.append(", ");
        }
        if (itemName != null) {
            builder.append("itemName=");
            builder.append(itemName);
            builder.append(", ");
        }
        if (itemType != null) {
            builder.append("itemType=");
            builder.append(itemType);
        }
        builder.append("]");
        return builder.toString();
    }
}
