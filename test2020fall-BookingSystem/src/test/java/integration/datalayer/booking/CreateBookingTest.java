package integration.datalayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import dto.BookingCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;


import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateBookingTest extends ContainerizedDbIntegrationTest {
    // SUT (Systems Under Test)
    private BookingStorage bookingStorage;
    private BookingStorageImpl bookingStorageImpl;
    // DOC (Depended-on Component)
    private static final String user = "root";

    @BeforeAll
    public void beforeAll() throws SQLException {
        runMigration(3);
        bookingStorage = new BookingStorageImpl(getConnectionString(), user, getDbPassword()) {
        };
        bookingStorageImpl = new BookingStorageImpl(getConnectionString(), user, getDbPassword());
    }

    @Test
    @DisplayName("Booking: test of creating booking and storing in database through BookingStorage interface")
    void verifyBookingCreationAndStoringInDatabase() throws SQLException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        // Act
        int bookingID = bookingStorage.createBooking(new BookingCreation(customerID, employeeID, date, start, end));
        // Assert
        var bookings = bookingStorage.getBookings();
        assertTrue(
                bookings.stream().anyMatch(x ->
                        x.getId() == bookingID &&
                                x.getCustomerID() == customerID &&
                                x.getEmployeeID() == employeeID &&
                                x.getDate().equals(date)));
    }

    @Test
    @DisplayName("Booking: test of creating booking and storing in database through BookingStorageImpl")
    void verifyBookingCreationAndStoringInDatabaseThroughBookingStorageImpl() throws SQLException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        // Act
        int bookingID = bookingStorageImpl.createBooking(new BookingCreation(customerID, employeeID, date, start, end));
        // Assert
        var bookings = bookingStorageImpl.getBookings();
        assertTrue(
                bookings.stream().anyMatch(x ->
                        x.getId() == bookingID &&
                                x.getCustomerID() == customerID &&
                                x.getEmployeeID() == employeeID &&
                                x.getDate().equals(date)));
    }
}
