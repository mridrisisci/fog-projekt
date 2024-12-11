package app.entities;


import java.sql.Timestamp;
import java.util.List;

public class Order
{
    private int orderID;
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
    private String shed;
    private boolean hasShed;
    private List<String> orderDetails;
    private String paymentStatus;

    public String getShed()
    {
        return shed;
    }
    public String getPaymentStatus()
    {
        return paymentStatus;
    }


    public Order(int orderId, String carportId, int salespersonId, int price, int salesPrice, int coverageRatioPercentage, String status, Timestamp orderPlaced, RoofType roofType, int accountID)
    {
        orderID = orderId;
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
        orderID = orderId;
        carportID = carportId;
        this.price = price;
        this.salesPrice = salesPrice;
        this.coverageRatioPercentage = coverageRatioPercentage;
        this.orderPlaced = orderPlaced;
        this.status = status;
        this.roofType = roofType;
        this.accountID = accountID;
    }


    public Order(int orderId, String status, String carportID, Timestamp orderPlaced, String paymentStatus, int width, int length, Account account)
    { // used for 'orderhistory.html'
        this.orderID = orderId;
        this.status = status;
        this.carportID = carportID;
        this.orderPlaced = orderPlaced;
        this.paymentStatus = paymentStatus;
        this.width = width;
        this.length = length;
        this.account = account;
    }
    public Order(int orderID, Timestamp orderPlaced, String status, String carportID, int length, int width, String shed, int price, RoofType roofType)
    { // this is used for 'receipt.html' && sendOffer
        this.orderID = orderID;
        this.orderPlaced = orderPlaced;
        this.status = status;
        this.carportID = carportID;
        this.length = length;
        this.width = width;
        this.shed = shed;
        this.price = price;
        this.roofType = roofType;

    }

    public Order(int orderID, int width, int length, String shed, RoofType roofType, int salesPrice, int price, Account account)
    { // used for orderdetails
        this.orderID = orderID;
        this.width = width;
        this.length = length;
        this.shed = shed;
        this.roofType = roofType;
        this.salesPrice = salesPrice;
        this.price = price;
        this.account = account;

    }


    public List<String> getOrderDetails()
    {
        return orderDetails;
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

    public int getOrderID()
    {
        return orderID;
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
            "orderId=" + orderID +
            ", carportId=" + carportID +
            ", salespersonId=" + salesPersonID +
            ", price=" + price +
            ", timePlaced=" + orderPlaced +
            ", status=" + status +
            ", isAssigned=" + IS_ASSIGNED +
            "}";
    }


}
