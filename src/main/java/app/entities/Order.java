package app.entities;


import java.sql.Timestamp;

public class Order
{
    private final int ORDER_ID;
    private final int CUSTOMER_ID;
    private final int CARPORT_ID;
    private final int SALESPERSON_ID;
    private final int PRICE;
    private final Timestamp TIME_PLACED;
    private final boolean IS_ASSIGNED;
    private OrderStatus status;

    public Order(int orderId, int customerId, int carportId, int salespersonId, int price, OrderStatus status, Timestamp orderPlaced, boolean isAssigned)
    {
        ORDER_ID = orderId;
        CUSTOMER_ID = customerId;
        CARPORT_ID = carportId;
        SALESPERSON_ID = salespersonId;
        PRICE = price;
        TIME_PLACED = orderPlaced;
        IS_ASSIGNED = isAssigned;
        this.status = status;
    }

    public Order(Timestamp orderPlaced, OrderStatus status)
    {
        TIME_PLACED = orderPlaced;
        this.status = status;
    }



    public boolean isIS_ASSIGNED()
    {
        return IS_ASSIGNED;
    }

    public int getORDER_ID()
    {
        return ORDER_ID;
    }

    public OrderStatus getStatus() { return status;}

    public String getFullStatus() { return status.toString();}

    //public void setStatus(OrderStatus newStatus) { this.status = newStatus;}

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

    @Override
    public String toString() {
        return "Order {" +
            "orderId=" + ORDER_ID +
            ", customerId=" + CUSTOMER_ID +
            ", carportId=" + CARPORT_ID +
            ", salespersonId=" + SALESPERSON_ID +
            ", price=" + PRICE +
            ", timePlaced=" + TIME_PLACED +
            ", status=" + status +
            ", isAssigned=" + IS_ASSIGNED +
            "}";
    }


}
