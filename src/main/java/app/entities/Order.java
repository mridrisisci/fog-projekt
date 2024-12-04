package app.entities;


import java.sql.Timestamp;

public class Order
{
    private final int ORDER_ID;
    private  String carportID;
    private int salesPersonID;
    private int price;
    private Timestamp orderPlaced;
    private  boolean IS_ASSIGNED;
    private String status;
    private Account account;
    private int width;
    private int length;
    private boolean orderPaid;
    private int salesPrice;
    private RoofType roofType;
    private int coverageRatioPercentage;
    private int accountID;
    private Carport carport;
    private boolean hasShed;

    public Order(int orderId, String carportId, int salespersonId, int price, int salesPrice, int coverageRatioPercentage, String status, Timestamp orderPlaced, RoofType roofType, int accountID)
    {
        ORDER_ID = orderId;
        carportID = carportId;
        salesPersonID = salespersonId;
        this.price = price;
        this.salesPrice = salesPrice;
        this.coverageRatioPercentage = coverageRatioPercentage;
        this.orderPlaced = orderPlaced;
        this.status = status;
        this.roofType = roofType;
        this.accountID = accountID;
    }

    public Order(int orderId, String carportId, int price, int salesPrice, int coverageRatioPercentage, String status, Timestamp orderPlaced, RoofType roofType, int accountID)
    {
        ORDER_ID = orderId;
        carportID = carportId;
        this.price = price;
        this.salesPrice = salesPrice;
        this.coverageRatioPercentage = coverageRatioPercentage;
        this.orderPlaced = orderPlaced;
        this.status = status;
        this.roofType = roofType;
        this.accountID = accountID;
    }


    public Order(int orderId, String status, Timestamp orderPlaced, boolean orderPaid, int width, int length, Account account)
    { // used for 'orderhistory.html'
        this.ORDER_ID = orderId;
        this.status = status;
        this.orderPlaced = orderPlaced;
        this.orderPaid = orderPaid;
        this.width = width;
        this.length = length;
        this.account = account;
    }
    public Order(int orderID, Timestamp orderPlaced, String status, int length, int width, boolean hasShed, RoofType roofType)
    { // this is used for 'kvittering.html'
        this.ORDER_ID = orderID;
        this.orderPlaced = orderPlaced;
        this.status = status;
        this.length = length;
        this.width = width;
        this.hasShed = hasShed;
        this.roofType = roofType;

    }

    public Order(int orderID, Account account)
    { // used for orderdetails
        this.ORDER_ID = orderID;
        this.account = account;
    }




    public boolean getHasShed()
    {
        return hasShed;
    }

    public Carport getCarport()
    {
        return carport;
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

    public Timestamp getOrderPlaced()
    {
        return orderPlaced;
    }

    public int getWidth()
    {
        return width;
    }

    public int getLength()
    {
        return length;
    }


    public String getCarportID()
    {
        return carportID;
    }
    public int getSalesPersonID()
    {
        return salesPersonID;
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

    public RoofType getRoofType()
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
            ", carportId=" + carportID +
            ", salespersonId=" + salesPersonID +
            ", price=" + price +
            ", timePlaced=" + orderPlaced +
            ", status=" + status +
            ", isAssigned=" + IS_ASSIGNED +
            "}";
    }


}
