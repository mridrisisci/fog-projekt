package app.entities;


import java.sql.Timestamp;

public class Order
{
    private final int ORDER_ID;
    private final String CARPORT_ID;
    private int SALESPERSON_ID;
    private  int PRICE;
    private final Timestamp ORDER_PLACED;
    private  boolean IS_ASSIGNED;
    private String status;
    private  Account account;

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

    public Order(int orderID, String carportID, String status, Timestamp orderPlaced, Account account)
    {
        this.ORDER_ID = orderID;
        this.CARPORT_ID = carportID;
        this.status = status;
        this.ORDER_PLACED = orderPlaced;
        this.account = account;
    }


    public boolean isIS_ASSIGNED()
    {
        return IS_ASSIGNED;
    }

    public int getORDER_ID()
    {
        return ORDER_ID;
    }

    public String getStatus() { return status;}

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

    @Override
    public String toString() {
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
