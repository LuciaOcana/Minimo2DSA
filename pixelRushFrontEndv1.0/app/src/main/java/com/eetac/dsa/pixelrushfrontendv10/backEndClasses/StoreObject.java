package com.eetac.dsa.pixelrushfrontendv10.backEndClasses;

public class StoreObject {
    public String objectID;
    public String articleName;
    public int price;
    public String description;
    public String articlePhoto; //same as attribute photo from User class

    //empty constructor
    public StoreObject() {
    }

    //fully constructor
    public StoreObject(String objectID, String articleName, int price, String description) {
        this.objectID = objectID;
        this.articleName = articleName;
        this.price = price;
        this.description = description;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }
}