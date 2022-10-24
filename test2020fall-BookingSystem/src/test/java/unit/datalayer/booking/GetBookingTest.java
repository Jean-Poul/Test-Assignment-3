package unit.datalayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import dto.Booking;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

/*
The @TestInstance annotation lets us configure the lifecycle of JUnit 5 tests.
@TestInstance has two modes. One is LifeCycle.PER_METHOD (the default). The other is Lifecycle.PER_CLASS.
The latter enables us to ask JUnit to create only one instance of the test class and reuse it between tests.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class GetBookingTest {
    // SUT (Systems Under Test)
    private BookingStorage bookingStorage;
    private BookingStorageImpl bookingStorageImpl;
    // DOC (Depended-on Component)
    private BookingStorage bookingStorageMock;
    private BookingStorageImpl bookingStorageImplMock;

    private static final String conStr = "jdbc:mysql://localhost:3307/DemoApplicationTest";
    private static final String user = "root";
    private static final String pass = "testuser123";

    @BeforeAll
    public void beforeAll() {
        bookingStorageMock = mock(BookingStorage.class);
        bookingStorageImplMock = mock(BookingStorageImpl.class);
        /*
        bookingStorage = new BookingStorageImpl(conStr, user, pass) {
        };
        bookingStorageImpl = new BookingStorageImpl(conStr, user, pass);*/
    }

    @Test
    @DisplayName("Booking: test of get booking through BookingStorage interface")
    void verifyCallOfBookingStorage() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        Collection<Booking> expected = bookingStorageMock.getBookingsForCustomer(employeeID);
        List<Booking> actual = new ArrayList<>();
        //bookingStorageMock.getBookingsForCustomer(employeeID);
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(bookingStorageMock, times(1))
                .getBookingsForCustomer(employeeID);
    }

    @Test
    @DisplayName("Booking: test of get booking through BookingStorageImpl")
    void verifyCallOfBookingStorageImpl() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        Collection<Booking> expected = bookingStorageImplMock.getBookingsForCustomer(employeeID);
        List<Booking> actual = new ArrayList<>();
        //bookingStorageMock.getBookingsForCustomer(employeeID);
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(bookingStorageImplMock, times(1))
                .getBookingsForCustomer(employeeID);
    }
}
