package unit.datalayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.EmployeeCreation;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/*
The @TestInstance annotation lets us configure the lifecycle of JUnit 5 tests.
@TestInstance has two modes. One is LifeCycle.PER_METHOD (the default). The other is Lifecycle.PER_CLASS.
The latter enables us to ask JUnit to create only one instance of the test class and reuse it between tests.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class CreateEmployeeTest {
    // SUT (Systems Under Test)
    private EmployeeStorage employeeStorage;
    private EmployeeStorageImpl employeeStorageImpl;
    // DOC (Depended-on Component)
    private EmployeeStorage employeeStorageMock;
    private EmployeeStorageImpl employeeStorageImplMock;

    //private static final String conStr = "jdbc:mysql://localhost:3307/DemoApplicationTest";
    //private static final String user = "root";
    //private static final String pass = "testuser123";

    @BeforeAll
    public void beforeAll() {
        employeeStorageMock = mock(EmployeeStorage.class);
        employeeStorageImplMock = mock(EmployeeStorageImpl.class);
/*
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword()) {
        };
        employeeStorageImpl = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword()); */
    }

    @Test
    @DisplayName("Employee: test of create employee through EmployeeStorage interface")
    void verifyCreationOfEmployeeThroughStorage() throws SQLException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        // Act
        int expected = employeeStorageMock.createEmployee(new EmployeeCreation(firstName, lastName));
        int actual = 0;
        //employeeStorageMock.createEmployee(new EmployeeCreation(firstName, lastName));
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(employeeStorageMock, times(1))
                .createEmployee(
                        argThat(x -> x.getFirstName().equals(firstName) &&
                                x.getLastName().equals(lastName)));
    }

    @Test
    @DisplayName("Employee: test of create employee through EmployeeStorageImpl")
    void verifyCreationOfEmployeeThroughStorageImpl() throws SQLException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        // Act
        int expected = employeeStorageImplMock.createEmployee(new EmployeeCreation(firstName, lastName));
        int actual = 0;
        //employeeStorageMock.createEmployee(new EmployeeCreation(firstName, lastName));
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(employeeStorageImplMock, times(1))
                .createEmployee(
                        argThat(x -> x.getFirstName().equals(firstName) &&
                                x.getLastName().equals(lastName)));
    }
}
