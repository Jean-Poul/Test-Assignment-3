package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Customer;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface BookingStorage {
    int createBooking(BookingCreation booking) throws SQLException;

    Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException;

    Collection<Booking> getBookingsForEmployee(int customerId) throws SQLException;

    public List<Booking> getBookings() throws SQLException;
}
