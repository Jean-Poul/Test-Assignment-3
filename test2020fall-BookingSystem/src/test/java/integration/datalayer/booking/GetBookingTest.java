package integration.datalayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import dto.Booking;
import dto.BookingCreation;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Collection;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class GetBookingTest extends ContainerizedDbIntegrationTest {

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
    @DisplayName("Booking: test of database storing bookings and returning booking with customer ID through BookingStorage interface")
    void verifyBookingIdFromCustomerReturnsLatestIdAndStoresBookingInDatabase() throws SQLException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);

        int customerIDTwo = 3;
        int employeeIDTwo = 6;
        Date dateTwo = new Date(2000, 2, 2);
        Time startTwo = new Time(LocalTime.now().getHour());
        Time endTwo = new Time(LocalTime.now().getHour() + 1);

        int bookingID = bookingStorage.createBooking(new BookingCreation(customerID, employeeID, date, start, end));
        int bookingIDTwo = bookingStorage.createBooking(new BookingCreation(customerIDTwo, employeeIDTwo, dateTwo, startTwo, endTwo));
        // Act
        Collection<Booking> bookingList = bookingStorage.getBookingsForCustomer(customerID);
        var expected = 1;
        var expectedSize = bookingList.size();

        Collection<Booking> getAllBookings = bookingStorage.getBookings();
        var actualSize = getAllBookings.size();
        // Assert
        Assertions.assertEquals(expected, bookingIDTwo - bookingID);
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("Booking: test of storing bookings and getting customer booking by ID through BookingStorageImpl")
    void verifyDatabaseStoringBookingAndReturningBookingIdThroughStorageImpl() throws SQLException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        int bookingId = bookingStorageImpl.createBooking(new BookingCreation(customerID, employeeID, date, start, end));
        int actual = bookingId - (bookingId - 1);
        int expected = 1;
        // Act
        Collection<Booking> bookingList = bookingStorageImpl.getBookingsForCustomer(customerID);
        java.util.Date actualDate = bookingList.iterator().next().getDate();
        // Assert
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(date, actualDate);
    }
}
