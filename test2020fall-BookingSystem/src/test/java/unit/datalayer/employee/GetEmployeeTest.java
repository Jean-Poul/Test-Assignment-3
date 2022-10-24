package unit.datalayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.*;

import static org.mockito.Mockito.*;

/*
The @TestInstance annotation lets us configure the lifecycle of JUnit 5 tests.
@TestInstance has two modes. One is LifeCycle.PER_METHOD (the default). The other is Lifecycle.PER_CLASS.
The latter enables us to ask JUnit to create only one instance of the test class and reuse it between tests.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class GetEmployeeTest {
    // SUT (Systems Under Test)
    private EmployeeStorage employeeStorage;
    private EmployeeStorageImpl employeeStorageImpl;
    // DOC (Depended-on Component)
    private EmployeeStorage employeeStorageMock;
    private EmployeeStorage employeeStorageImplMock;
/*
    private static final String conStr = "jdbc:mysql://localhost:3307/DemoApplicationTest";
    private static final String user = "root";
    private static final String pass = "testuser123";*/

    @BeforeAll
    public void beforeAll() {
        employeeStorageMock = mock(EmployeeStorage.class);
        employeeStorageImplMock = mock(EmployeeStorageImpl.class);
    /*    employeeStorage = new EmployeeStorageImpl(conStr, user, pass) {
        };
        employeeStorageImpl = new EmployeeStorageImpl(conStr, user, pass);*/
    }

    @Test
    @DisplayName("Employee: test of get employee through EmployeeStorage interface")
    void verifyCallOfEmployeeStorage() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        Collection<Employee> expected = employeeStorageMock.getEmployeeWithId(employeeID);
        List<Employee> actual = new ArrayList<>();
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(employeeStorageMock, times(1))
                .getEmployeeWithId(employeeID);
    }

    @Test
    @DisplayName("Employee: test of get employee through EmployeeStorageImpl")
    void verifyCallOfEmployeeStorageImpl() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        Collection<Employee> expected = employeeStorageImplMock.getEmployeeWithId(employeeID);
        List<Employee> actual = new ArrayList<>();
        // Assert
        // Functionality
        Assertions.assertEquals(expected, actual);
        // Behavior
        verify(employeeStorageImplMock, times(1))
                .getEmployeeWithId(employeeID);
    }
}
