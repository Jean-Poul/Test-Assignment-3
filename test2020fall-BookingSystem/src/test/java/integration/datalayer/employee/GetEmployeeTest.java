package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Collection;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class GetEmployeeTest extends ContainerizedDbIntegrationTest {
    // SUT (Systems Under Test)
    private EmployeeStorage employeeStorage;
    private EmployeeStorageImpl employeeStorageImpl;
    // DOC (Depended-on Component)
    private static final String user = "root";

    @BeforeAll
    public void beforeAll() throws SQLException {
        runMigration(3);
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword()) {
        };
        employeeStorageImpl = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword());

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
    @DisplayName("Employee: test of get employee with ID through EmployeeStorage interface")
    void verifyEmployeeIdAndReturnLatestId() throws SQLException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        var expectedAssertion = 1;
        int employeeOne = employeeStorage.createEmployee(new EmployeeCreation(firstName, lastName));
        int employeeTwo = employeeStorage.createEmployee(new EmployeeCreation("Ping?", "Pong!"));
        // Act
        Collection<Employee> employeeList = employeeStorage.getEmployeeWithId(employeeOne);
        int actual = employeeList.iterator().next().getId();
        // Assert
        Assertions.assertEquals(employeeOne, actual);
        Assertions.assertEquals(expectedAssertion, employeeTwo - employeeOne);
    }

    @Test
    @DisplayName("Employee: test of get employee with ID through EmployeeStorageImpl")
    void verifyGetEmployeeByIdThroughStorageImpl() throws SQLException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        int employeeOne = employeeStorageImpl.createEmployee(new EmployeeCreation(firstName, lastName));
        // Act
        Collection<Employee> employeeList = employeeStorageImpl.getEmployeeWithId(employeeOne);
        int actual = employeeList.iterator().next().getId();
        String actualFirstName = employeeList.iterator().next().getFirstName();
        // Assert
        Assertions.assertEquals(employeeOne, actual);
        Assertions.assertEquals(firstName, actualFirstName);
    }
}
