package servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
import dto.*;
import servicelayer.notifications.SmsService;
import servicelayer.notifications.SmsServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class BookingServiceImpl implements BookingService {

    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;

    private final SmsServiceImpl smsService = new SmsServiceImpl();

    public BookingServiceImpl(BookingStorage bookingStorage, CustomerStorage customerStorage, EmployeeStorage employeeStorage) {
        this.bookingStorage = bookingStorage;
        this.customerStorage = customerStorage;
        this.employeeStorage = employeeStorage;
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException, SQLException {
        // Customer customer = customerStorage.getCustomerWithId(customerId);
        // Collection<Employee> employee = employeeStorage.getEmployeeWithId(employeeId);
        // Employee em = employee.stream().findFirst().orElse(null);
        // assert em != null;
        SmsMessage smsMessage = new SmsMessage("CUSTOMER BOOKING", "PING? PONG!");
        System.out.println(smsMessage);
        boolean isSend = smsService.sendSms(smsMessage);
        System.out.println("MESSAGE HAS BEEN SEND: " + isSend);
        return bookingStorage.createBooking(new BookingCreation(customerId, employeeId, date, start, end));
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        return bookingStorage.getBookingsForCustomer(customerId);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        return bookingStorage.getBookingsForEmployee(employeeId);
    }
}
