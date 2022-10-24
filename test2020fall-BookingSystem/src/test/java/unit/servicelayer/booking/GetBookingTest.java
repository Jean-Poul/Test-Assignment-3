package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
import dto.Booking;
import org.junit.jupiter.api.*;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;

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
    @DisplayName("Booking: Verify list of bookings for Customer by id (Service interface)")
    @Order(1)
    void getBookingsListFromCustomerService() throws SQLException {
        // Arrange
        int customerID = 12;
        // Act
        bookingService.getBookingsForCustomer(customerID);
        // Assert
        // Functionality
        Assertions.assertEquals(bookingStorageMock.getBookingsForCustomer(customerID), new ArrayList<Booking>());
        // Behavior
        verify(bookingStorageMock, times(2))
                .getBookingsForCustomer(customerID);
    }

    @Test
    @DisplayName("Booking: Verify list of bookings for Customer by id (ServiceImpl)")
    @Order(2)
    void getBookingsListFromCustomerServiceImpl() throws SQLException {
        // Arrange
        int customerID = 12;
        // Act
        bookingServiceImpl.getBookingsForCustomer(customerID);
        // Assert
        // Functionality
        Assertions.assertEquals(bookingStorageMock.getBookingsForCustomer(customerID), new ArrayList<Booking>());
        // Behavior
        verify(bookingStorageMock, times(4))
                .getBookingsForCustomer(customerID);
    }

    @Test
    @DisplayName("Booking: Verify list of bookings for Employee by id (Service interface)")
    @Order(3)
    void getBookingsListFromEmployeeService() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        bookingService.getBookingsForEmployee(employeeID);
        // Assert
        // Functionality
        Assertions.assertEquals(bookingStorageMock.getBookingsForEmployee(employeeID), new ArrayList<Booking>());
        // Behavior
        verify(bookingStorageMock, times(4))
                .getBookingsForEmployee(employeeID);
    }

    @Test
    @DisplayName("Booking: Verify list of bookings for Employee by id (ServiceImpl)")
    @Order(4)
    void getBookingsListForEmployeeImpl() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        bookingServiceImpl.getBookingsForEmployee(employeeID);
        // Assert
        // Functionality
        Assertions.assertEquals(bookingStorageMock.getBookingsForEmployee(employeeID), new ArrayList<Booking>());
        // Behavior
        verify(bookingStorageMock, times(2))
                .getBookingsForEmployee(employeeID);
    }
}
