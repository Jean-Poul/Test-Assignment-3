package unit.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import org.junit.jupiter.api.*;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceImpl;

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
class GetEmployeeTest {

    // SUT (Systems Under Test)
    private EmployeeService employeeService;
    private EmployeeServiceImpl employeeServiceImpl;
    // DOC (Depended-on Component)
    private EmployeeStorage employeeStorageMock;

    @BeforeAll
    public void beforeAll() {
        employeeStorageMock = mock(EmployeeStorage.class);
        employeeService = new EmployeeServiceImpl(employeeStorageMock) {
        };
        employeeServiceImpl = new EmployeeServiceImpl(employeeStorageMock) {
        };
    }

    @Test
    @DisplayName("Employee: test of get employee through Service interface and storage mock call")
    void verifyCallOfEmployeeServiceAndStorage() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        employeeService.getEmployeeById(employeeID);
        // Assert
        // Functionality
        Assertions.assertEquals(employeeStorageMock.getEmployeeWithId(employeeID), new ArrayList<Employee>());
        // Behavior
        verify(employeeStorageMock, times(4))
                .getEmployeeWithId(employeeID);
    }

    @Test
    @DisplayName("Employee: test of get employee through ServiceImpl and storage mock call")
    void verifyCallOfEmployeeServiceImplAndStorage() throws SQLException {
        // Arrange
        int employeeID = 12;
        // Act
        employeeServiceImpl.getEmployeeById(employeeID);
        // Assert
        // Functionality
        Assertions.assertEquals(employeeStorageMock.getEmployeeWithId(employeeID), new ArrayList<Employee>());
        // Behavior
        verify(employeeStorageMock, times(2))
                .getEmployeeWithId(employeeID);
    }
}

