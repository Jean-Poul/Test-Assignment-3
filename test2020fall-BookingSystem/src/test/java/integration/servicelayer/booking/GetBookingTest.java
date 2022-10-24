package integration.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
import dto.Booking;
import dto.BookingCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class GetBookingTest extends ContainerizedDbIntegrationTest {

    // SUT (Systems Under Test)
    private BookingService bookingService;
    private BookingServiceImpl bookingServiceImpl;
    // DOC (Depended-on Component)
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorageStorage;
    private static final String user = "root";

    @BeforeAll
    public void beforeAll() throws SQLException {
        runMigration(3);
        bookingStorage = new BookingStorageImpl(getConnectionString(), user, getDbPassword()) {
        };
        bookingService = new BookingServiceImpl(bookingStorage, customerStorage, employeeStorageStorage);
        bookingServiceImpl = new BookingServiceImpl(bookingStorage, customerStorage, employeeStorageStorage);
    }

    @Test
    @DisplayName("Booking: test of database storing bookings and returning booking with customer ID through BookingService interface")
    void verifyBookingIdFromCustomerReturnsLatestIdAndStoresBookingInDatabase() throws SQLException, BookingServiceException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        int bookingID = bookingService.createBooking(customerID, employeeID, date, start, end);
        // Act
        Collection<Booking> bookingList = bookingStorage.getBookingsForCustomer(customerID);
        // Assert
        assertTrue(
                bookingList.stream().anyMatch(x ->
                        x.getCustomerID() == customerID &&
                                x.getEmployeeID() == employeeID &&
                                x.getId() == bookingID &&
                                x.getDate().equals(date)));
    }

    @Test
    @DisplayName("Booking: test of storing bookings and getting customer booking by ID through BookingServiceImpl")
    void verifyDatabaseStoringBookingAndReturningBookingIdThroughBookingServiceImpl() throws SQLException, BookingServiceException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        int bookingID = bookingServiceImpl.createBooking(customerID, employeeID, date, start, end);
        // Act
        Collection<Booking> bookingList = bookingStorage.getBookingsForCustomer(customerID);
        // Assert
        assertTrue(
                bookingList.stream().anyMatch(x ->
                        x.getCustomerID() == customerID &&
                                x.getEmployeeID() == employeeID &&
                                x.getId() == bookingID &&
                                x.getDate().equals(date)));
    }

    @Test
    @DisplayName("Booking: test of database storing bookings and returning booking with employee ID through BookingService interface")
    void verifyBookingIdFromEmployeeReturnsLatestIdAndStoresBookingInDatabase() throws SQLException, BookingServiceException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        int bookingID = bookingService.createBooking(customerID, employeeID, date, start, end);
        // Act
        Collection<Booking> bookingList = bookingStorage.getBookingsForEmployee(employeeID);
        // Assert
        assertTrue(
                bookingList.stream().anyMatch(x ->
                        x.getCustomerID() == customerID &&
                                x.getEmployeeID() == employeeID &&
                                x.getId() == bookingID &&
                                x.getDate().equals(date)));
    }

    @Test
    @DisplayName("Booking: test of storing bookings and getting employee by ID through BookingServiceImpl")
    void verifyDatabaseStoringBookingAndReturningEmployeeIdThroughBookingServiceImpl() throws SQLException, BookingServiceException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        int bookingID = bookingServiceImpl.createBooking(customerID, employeeID, date, start, end);
        // Act
        Collection<Booking> bookingList = bookingStorage.getBookingsForEmployee(employeeID);
        // Assert
        assertTrue(
                bookingList.stream().anyMatch(x ->
                        x.getCustomerID() == customerID &&
                                x.getEmployeeID() == employeeID &&
                                x.getId() == bookingID &&
                                x.getDate().equals(date)));
    }
}

