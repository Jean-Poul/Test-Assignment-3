package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BookingStorageImpl implements BookingStorage {
    private final String connectionString;
    private final String username;
    private final String password;

    public BookingStorageImpl(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createBooking(BookingCreation bookingCreation) throws SQLException {
        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bookingCreation.getCustomerID());
            stmt.setInt(2, bookingCreation.getEmployeeID());
            stmt.setDate(3, bookingCreation.getDate());
            stmt.setTime(4, bookingCreation.getStart());
            stmt.setTime(5, bookingCreation.getEnd());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int bookings) throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerId = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking booking = new Booking(id, customerId, employeeId, date, start, end);
                    results.add(booking);
                }
            }
            return results;
        }
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int bookings) throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerId = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking booking = new Booking(id, customerId, employeeId, date, start, end);
                    results.add(booking);
                }
            }
            return results;
        }
    }

    public List<Booking> getBookings() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Booking>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, customerId, employeeId, date, start, end from Bookings")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    int customerId = resultSet.getInt("customerId");
                    int employeeId = resultSet.getInt("employeeId");
                    Date date = resultSet.getDate("date");
                    Time start = resultSet.getTime("start");
                    Time end = resultSet.getTime("end");

                    Booking booking = new Booking(id, customerId, employeeId, date, start, end);
                    results.add(booking);
                }
            }
            return results;
        }
    }
}
