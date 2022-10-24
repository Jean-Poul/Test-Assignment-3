package main;

import datalayer.booking.BookingStorageImpl;
import datalayer.employee.EmployeeStorageImpl;
import dto.Booking;
import dto.Customer;
import datalayer.customer.CustomerStorageImpl;
import dto.Employee;

import java.sql.SQLException;

public class Main {

    private static final String conStr = "jdbc:mysql://localhost:3307/DemoApplicationTest";
    private static final String user = "root";
    private static final String pass = "testuser123";

    public static void main(String[] args) throws SQLException {
        CustomerStorageImpl customerStorage = new CustomerStorageImpl(conStr, user, pass);
        EmployeeStorageImpl employeeStorage = new EmployeeStorageImpl(conStr, user, pass);
        BookingStorageImpl bookingStorage = new BookingStorageImpl(conStr, user, pass);

        System.out.println("Got customers: ");
        for (Customer c : customerStorage.getCustomers()) {
            System.out.println(toString(c));
        }
        System.out.println("Got employee: ");
        for (Employee e : employeeStorage.getEmployeeWithId(1)) {
            System.out.println(e.getFirstName());
        }
        System.out.println("Got Bookings: ");
        for (Booking b : bookingStorage.getBookingsForCustomer(1)) {
            System.out.println(b.getId());
        }
        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }
}
