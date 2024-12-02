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
    private Account account;
    private int HEIGHT;
    private int LENGTH;
    private int ACCOUNT_ID;
    private boolean orderPaid;

    public Order(int orderId, String carportId, String status, Timestamp orderPlaced, boolean orderPaid, int height, int length, Account account)
    { // this constructor is used to "see all queries // requestedqueries.html"
        this.ORDER_ID = orderId;
        this.CARPORT_ID = carportId;
        this.status = status;
        this.ORDER_PLACED = orderPlaced;
        this.orderPaid = orderPaid;
        this.HEIGHT = height;
        this.LENGTH = length;
        this.account = account;
    }



    public Order(int orderID, Timestamp orderPlaced, String status, String carportID)
    {
        this.ORDER_ID = orderID;
        ORDER_PLACED = orderPlaced;
        this.status = status;
        this.CARPORT_ID = carportID;

    }

    public boolean getOrderPaid()
    {
        return orderPaid;
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

    public int getHEIGHT()
    {
        return HEIGHT;
    }

    public int getLENGTH()
    {
        return LENGTH;
    }

    public int getACCOUNT_ID()
    {
        return ACCOUNT_ID;
    }


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

    public Account getAccount()
    {
        return account;
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
