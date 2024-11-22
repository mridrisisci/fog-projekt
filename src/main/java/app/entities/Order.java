package app.entities;


import java.sql.Timestamp;

public class Order
{
    private final int ORDER_ID;
    private final int CUSTOMER_ID;
    private final int CARPORT_ID;
    private final int SALESPERSON_ID;

    private enum status {
        NOT_PAID,
        PAID,
        COMPLETED
    }

    private final int PRICE;
    private final Timestamp TIME_PLACED;
    private final boolean ORDER_PAID;
    private final boolean IS_ASSIGNED;


    public Order(int orderId, int customerId, int carportId, int salespersonId, int price, Timestamp timePlaced, boolean orderPaid, boolean isAssigned) {
        ORDER_ID = orderId;
        CUSTOMER_ID = customerId;
        CARPORT_ID = carportId;
        SALESPERSON_ID = salespersonId;
        PRICE = price;
        TIME_PLACED = timePlaced;
        ORDER_PAID = orderPaid;
        IS_ASSIGNED = isAssigned;
    }

    public boolean isIS_ASSIGNED()
    {
        return IS_ASSIGNED;
    }

    public boolean getOrderPaid()
    {
        return ORDER_PAID;
    }

    public int getORDER_ID()
    {
        return ORDER_ID;
    }

    public int getCUSTOMER_ID()
    {
        return CUSTOMER_ID;
    }
    public int getCARPORT_ID()
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

}
