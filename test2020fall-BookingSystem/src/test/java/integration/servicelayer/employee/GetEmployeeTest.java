package integration.servicelayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class GetEmployeeTest extends ContainerizedDbIntegrationTest {
    // SUT (Systems Under Test)
    private EmployeeService employeeService;
    private EmployeeServiceImpl employeeServiceImpl;
    // DOC (Depended-on Component)
    private EmployeeStorage employeeStorage;
    private static final String user = "root";

    @BeforeAll
    public void beforeAll() throws SQLException {
        runMigration(3);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword());
        employeeService = new EmployeeServiceImpl(employeeStorage);
        employeeServiceImpl = new EmployeeServiceImpl(employeeStorage);

        var newEmployees = employeeStorage.getEmployees().size();
        if (newEmployees < 100) {
            addFakeEmployees(100 - newEmployees);
        }
    }

    /*
    @BeforeEach
    public void beforeEach() {
        runMigration(3);
    }*/

    private void addFakeEmployees(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName());
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    @DisplayName("Employee: test of get employee with ID through EmployeeService interface")
    void verifyEmployeeIdAndReturnLatestId() throws SQLException, EmployeeServiceException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 10);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date birthdate = cal.getTime();
        int actual = employeeService.createEmployee(firstName, lastName, birthdate);
        // Act
        Collection<Employee> employeeList = employeeStorage.getEmployeeWithId(actual);
        // Assert
        assertTrue(
                employeeList.stream().anyMatch(x ->
                        x.getFirstName().equals("J-P") &&
                                x.getLastName().equals("L-M") &&
                                x.getId() == actual));
    }

    @Test
    @DisplayName("Employee: test of get employee with ID through EmployeeStorageImpl")
    void verifyGetEmployeeByIdThroughStorageImpl() throws SQLException, EmployeeServiceException {
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
