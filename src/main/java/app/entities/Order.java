package app.entities;


import java.sql.Timestamp;

public class Order
{
    private final int ORDER_ID;
    private final String CARPORT_ID;
    private int SALESPERSON_ID;
    private final int PRICE;
    private final Timestamp ORDER_PLACED;
    private  boolean IS_ASSIGNED;
    private String status;
    private int salesPrice;
    private String roofType;
    private int coverageRatioPercentage;
    private int accountID;

    public Order(int orderId, String carportId, int salespersonId, int price, int salesPrice, int coverageRatioPercentage, String status, Timestamp orderPlaced, String roofType, int accountID)
    {
        ORDER_ID = orderId;
        CARPORT_ID = carportId;
        SALESPERSON_ID = salespersonId;
        PRICE = price;
        this.salesPrice = salesPrice;
        this.coverageRatioPercentage = coverageRatioPercentage;
        ORDER_PLACED = orderPlaced;
        this.status = status;
        this.roofType = roofType;
        this.accountID = accountID;
    }

    public Order(int orderId, String carportId, int price, int salesPrice, int coverageRatioPercentage, String status, Timestamp orderPlaced, String roofType, int accountID)
    {
        ORDER_ID = orderId;
        CARPORT_ID = carportId;
        PRICE = price;
        this.salesPrice = salesPrice;
        this.coverageRatioPercentage = coverageRatioPercentage;
        ORDER_PLACED = orderPlaced;
        this.status = status;
        this.roofType = roofType;
        this.accountID = accountID;
    }


    public Order(int orderId, String carportId, int salespersonId, int price, String status, Timestamp orderPlaced, boolean isAssigned)
    {
        ORDER_ID = orderId;
        CARPORT_ID = carportId;
        SALESPERSON_ID = salespersonId;
        PRICE = price;
        ORDER_PLACED = orderPlaced;
        IS_ASSIGNED = isAssigned;
        this.status = status;
    }

    public Order(int orderID, Timestamp orderPlaced, String status, String carportID)
    {
        this.ORDER_ID = orderID;
        ORDER_PLACED = orderPlaced;
        this.status = status;
        this.CARPORT_ID = carportID;

    }



    public boolean isIS_ASSIGNED()
    {
        return IS_ASSIGNED;
    }

    public int getORDER_ID()
    {
        return ORDER_ID;
    }

    public String getStatus()
    {
        return status;
    }

    public Timestamp getORDER_PLACED()
    {
        return ORDER_PLACED;
    }


    //public void setStatus(OrderStatus newStatus) { this.status = newStatus;}


    public String getCARPORT_ID()
    {
        return CARPORT_ID;
    }

    public int getSALESPERSON_ID()
    {
        return SALESPERSON_ID;
    }

    public int getPRICE()
    {
        return PRICE;
    }

    public int getSalesPrice()
    {
        return salesPrice;
    }

    public String getRoofType()
    {
        return roofType;
    }

    public int getCoverageRatioPercentage()
    {
        return coverageRatioPercentage;
    }

    public int getAccountID()
    {
        return accountID;
    }

    @Override
    public String toString()
    {
        return "Order {" +
            "orderId=" + ORDER_ID +
            ", carportId=" + CARPORT_ID +
            ", salespersonId=" + SALESPERSON_ID +
            ", price=" + PRICE +
            ", timePlaced=" + ORDER_PLACED +
            ", status=" + status +
            ", isAssigned=" + IS_ASSIGNED +
            "}";
    }


}
