package app.entities;


import java.sql.Timestamp;

public class Order
{
    private final int ORDER_ID;
    private final String CARPORT_ID;
    private int SALESPERSON_ID;
    private int price;
    private final Timestamp ORDER_PLACED;
    private  boolean IS_ASSIGNED;
    private String status;
    private Account account;
    private int HEIGHT;
    private int LENGTH;
    private boolean orderPaid;
    private int salesPrice;
    private String roofType;
    private int coverageRatioPercentage;
    private int accountID;

    public Order(int orderId, String carportId, int salespersonId, int price, int salesPrice, int coverageRatioPercentage, String status, Timestamp orderPlaced, String roofType, int accountID)
    {
        ORDER_ID = orderId;
        CARPORT_ID = carportId;
        SALESPERSON_ID = salespersonId;
        this.price = price;
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
        this.price = price;
        this.salesPrice = salesPrice;
        this.coverageRatioPercentage = coverageRatioPercentage;
        ORDER_PLACED = orderPlaced;
        this.status = status;
        this.roofType = roofType;
        this.accountID = accountID;
    }


    public Order(int orderId, String carportId, String status, Timestamp orderPlaced, boolean orderPaid, int HEIGHT, int LENGTH, Account account)
    { // used for 'orderhistory.html'
        this.ORDER_ID = orderId;
        this.CARPORT_ID = carportId;
        this.status = status;
        this.ORDER_PLACED = orderPlaced;
        this.orderPaid = orderPaid;
        this.HEIGHT = HEIGHT;
        this.LENGTH = LENGTH;
        this.account = account;
    }
    public Order(int orderID, Timestamp orderPlaced, String status, String carportID)
    { // this is used for 'kvittering.html'
        this.ORDER_ID = orderID;
        ORDER_PLACED = orderPlaced;
        this.status = status;
        this.CARPORT_ID = carportID;

    }

    public Order(int ORDER_ID, String CARPORT_ID, int SALESPERSON_ID, int price, Timestamp ORDER_PLACED, String status, Account account, int HEIGHT, int LENGTH, int salesPrice, String roofType)
    { // used for getorderdetails() || order/details/{id}
        this.ORDER_ID = ORDER_ID;
        this.CARPORT_ID = CARPORT_ID;
        this.SALESPERSON_ID = SALESPERSON_ID;
        this.price = price;
        this.ORDER_PLACED = ORDER_PLACED;
        this.status = status;
        this.account = account;
        this.HEIGHT = HEIGHT;
        this.LENGTH = LENGTH;
        this.salesPrice = salesPrice;
        this.roofType = roofType;
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
    public boolean getOrderPaid()
    {
        return orderPaid;
    }

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


    public String getCARPORT_ID()
    {
        return CARPORT_ID;
    }
    public int getSALESPERSON_ID()
    {
        return SALESPERSON_ID;
    }

    public int getPrice()
    {
        return price;
    }

    public Account getAccount()
    {
        return account;
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
            ", price=" + price +
            ", timePlaced=" + ORDER_PLACED +
            ", status=" + status +
            ", isAssigned=" + IS_ASSIGNED +
            "}";
    }


}
