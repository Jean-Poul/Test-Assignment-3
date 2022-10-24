package unit.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.EmployeeCreation;
import org.junit.jupiter.api.*;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

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
    @DisplayName("Employee: test of create employee through Service interface and storage mock call")
    void verifyCreationOfEmployeeThroughService() throws EmployeeServiceException, SQLException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date birthdate = cal.getTime();
        // Act
        int actual = employeeService.createEmployee(firstName, lastName, birthdate);
        // Assert
        // Functionality
        Assertions.assertEquals(employeeStorageMock.createEmployee(new EmployeeCreation(firstName, lastName)), actual);
        // Behavior
        verify(employeeStorageMock, times(2))
                .createEmployee(
                        argThat(x -> x.getFirstName().equals(firstName) &&
                                x.getLastName().equals(lastName)));
    }

    @Test
    @DisplayName("Employee: test of create employee through ServiceImpl and storage mock call")
    void verifyCreationOfEmployeeThroughServiceImpl() throws EmployeeServiceException, SQLException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date birthdate = cal.getTime();
        // Act
        int actual = employeeServiceImpl.createEmployee(firstName, lastName, birthdate);
        // Assert
        // Functionality
        Assertions.assertEquals(employeeStorageMock.createEmployee(new EmployeeCreation(firstName, lastName)), actual);
        // Behavior
        verify(employeeStorageMock, times(4))
                .createEmployee(
                        argThat(x -> x.getFirstName().equals(firstName) &&
                                x.getLastName().equals(lastName)));
    }
}

