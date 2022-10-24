package unit.datalayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.employee.EmployeeStorage;
import dto.BookingCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

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
class CreateBookingTest extends ContainerizedDbIntegrationTest {
    // SUT (Systems Under Test)
    private BookingStorage bookingStorage;
    private BookingStorageImpl bookingStorageImpl;
    // DOC (Depended-on Component)
    private BookingStorage bookingStorageMock;
    private BookingStorageImpl bookingStorageImplMock;

    //private static final String conStr = "jdbc:mysql://localhost:3307/DemoApplicationTest";
    //private static final String user = "root";
    CustomerStorage customerStorage;
    EmployeeStorage employeeStorage;

    @BeforeAll
    public void beforeAll() throws SQLException {
        // runMigration(3);
        bookingStorageMock = mock(BookingStorage.class);
        bookingStorageImplMock = mock(BookingStorageImpl.class);

       /* bookingStorage = new BookingStorageImpl(getConnectionString(), user, getDbPassword()) {
        };
        bookingStorageImpl = new BookingStorageImpl(getConnectionString(), user, getDbPassword());

        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }

        //private static final String pass = "testuser123";
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        var newEmployees = employeeStorage.getEmployees().size();
        if (newEmployees < 100) {
            addFakeEmployees(100 - newEmployees);
    }*/
    }
/*
    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName());
            customerStorage.createCustomer(c);
        }

    }

    private void addFakeEmployees(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName());
            employeeStorage.createEmployee(e);
        }
    }

    @BeforeEach
    public void beforeEach() {
        runMigration(3);
    } */

    @Test
    @DisplayName("Booking: test of create booking through BookingStorage interface")
    void verifyCreationOfEmployeeThroughStorage() throws SQLException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        // Act
        int actual = bookingStorageMock.createBooking(new BookingCreation(customerID, employeeID, date, start, end));
        int expected = 0;
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(bookingStorageMock, times(1))
                .createBooking(
                        argThat(x -> x.getCustomerID() == (customerID) &&
                                x.getEmployeeID() == (employeeID)));
    }

    @Test
    @DisplayName("Booking: test of create booking through BookingStorageImpl")
    void verifyCreationOfEmployeeThroughStorageImpl() throws SQLException {
        // Arrange
        int customerID = 12;
        int employeeID = 9;
        Date date = new Date(1999, 9, 9);
        Time start = new Time(LocalTime.now().getHour());
        Time end = new Time(LocalTime.now().getHour() + 1);
        // Act
        int actual = bookingStorageImplMock.createBooking(new BookingCreation(customerID, employeeID, date, start, end));
        int expected = 0;
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(bookingStorageImplMock, times(1))
                .createBooking(
                        argThat(x -> x.getCustomerID() == (customerID) &&
                                x.getEmployeeID() == (employeeID)));
    }
}
