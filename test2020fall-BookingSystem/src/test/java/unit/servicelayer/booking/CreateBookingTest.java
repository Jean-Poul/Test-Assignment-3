package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
import dto.BookingCreation;
import org.junit.jupiter.api.*;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/*
The @TestInstance annotation lets us configure the lifecycle of JUnit 5 tests.
@TestInstance has two modes. One is LifeCycle.PER_METHOD (the default). The other is Lifecycle.PER_CLASS.
The latter enables us to ask JUnit to create only one instance of the test class and reuse it between tests.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class CreateBookingTest {
    // SUT (Systems Under Test)
    private BookingService bookingService;
    private BookingServiceImpl bookingServiceImpl;
    // DOC (Depended-on Component)
    private BookingStorage bookingStorageMock;


    @BeforeAll
    public void beforeAll() {
        bookingStorageMock = mock(BookingStorage.class);
        CustomerStorage customerStorageMock = mock(CustomerStorage.class);
        EmployeeStorage employeeStorageMock = mock(EmployeeStorage.class);
        bookingService = new BookingServiceImpl(bookingStorageMock, customerStorageMock, employeeStorageMock);
        bookingServiceImpl = new BookingServiceImpl(bookingStorageMock, customerStorageMock, employeeStorageMock);
    }

    @Test
    @DisplayName("Booking: Create booking through Service interface")
    void verifyBookingCreationThroughServiceInterface() throws SQLException, BookingServiceException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        // Act
        int actual = bookingService.createBooking(customerID, employeeID, date, start, end);
        // Assert
        // Functionality
        Assertions.assertEquals(bookingStorageMock.createBooking(new BookingCreation(customerID, employeeID, date, start, end)), actual);
        // Behavior
        verify(bookingStorageMock, times(2))
                .createBooking(
                        argThat(x -> x.getCustomerID() == (customerID) &&
                                x.getEmployeeID() == (employeeID)));
    }

    @Test
    @DisplayName("Booking: Create booking through ServiceImpl")
    void verifyBookingCreationThroughServiceImpl() throws SQLException, BookingServiceException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        // Act
        int actual = bookingServiceImpl.createBooking(customerID, employeeID, date, start, end);
        // Assert
        // Functionality
        Assertions.assertEquals(bookingStorageMock.createBooking(new BookingCreation(customerID, employeeID, date, start, end)), actual);
        // Behavior
        verify(bookingStorageMock, times(4))
                .createBooking(
                        argThat(x -> x.getCustomerID() == (customerID) &&
                                x.getEmployeeID() == (employeeID)));
    }
}

