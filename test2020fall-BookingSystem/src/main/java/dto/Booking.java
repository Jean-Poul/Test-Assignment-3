package dto;

import java.sql.Date;
import java.sql.Time;


public class Booking {
    private final int id;
    private int customerID;
    private int employeeID;
    private Date date;
    private Time start;
    private Time end;

    public Booking(int id, int customerID, int employeeID, Date date, Time start, Time end) {
        this.id = id;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }
}
