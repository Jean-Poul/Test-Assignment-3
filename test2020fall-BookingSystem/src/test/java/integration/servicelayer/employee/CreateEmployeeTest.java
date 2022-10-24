package integration.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
class CreateEmployeeTest extends ContainerizedDbIntegrationTest {
    // SUT (Systems Under Test)
    private EmployeeService employeeService;
    private EmployeeServiceImpl employeeServiceImpl;
    // DOC (Depended-on Component)
    private EmployeeStorage employeeStorage;
    private static final String user = "root";

    @BeforeAll
    public void beforeAll() {
        runMigration(2);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword());
        employeeService = new EmployeeServiceImpl(employeeStorage);
        employeeServiceImpl = new EmployeeServiceImpl(employeeStorage);
    }

    @Test
    @DisplayName("Employee: test of create employee through Service interface and storage")
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
        var createdEmployee = employeeStorage.getEmployeeWithId(actual);
        // Assert
        assertTrue(
                createdEmployee.stream().anyMatch(x ->
                        x.getFirstName().equals("J-P") &&
                                x.getLastName().equals("L-M") &&
                                x.getId() == actual));
    }

    @Test
    @DisplayName("Employee: test of create employee through ServiceImpl and storage call")
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
        var createdEmployee = employeeStorage.getEmployeeWithId(actual);
        // Assert
        assertTrue(
                createdEmployee.stream().anyMatch(x ->
                        x.getFirstName().equals("J-P") &&
                                x.getLastName().equals("L-M") &&
                                x.getId() == actual));
    }
}
