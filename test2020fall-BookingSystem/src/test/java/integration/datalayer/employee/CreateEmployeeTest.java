package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateEmployeeTest extends ContainerizedDbIntegrationTest {
    // SUT (Systems Under Test)
    private EmployeeStorage employeeStorage;
    private EmployeeStorageImpl employeeStorageImpl;
    // DOC (Depended-on Component)
    private static final String user = "root";

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(2);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword());
        employeeStorageImpl = new EmployeeStorageImpl(getConnectionString(), user, getDbPassword());

        var newEmployees = employeeStorage.getEmployees().size();
        if (newEmployees < 100) {
            addFakeEmployees(100 - newEmployees);
        }
    }

    private void addFakeEmployees(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName());
            employeeStorage.createEmployee(e);
        }
    }

    @Test
    @DisplayName("Employee: test of employee creation through EmployeeStorage interface")
    void mustSaveCustomerInDatabaseWhenCallingCreateEmployee() throws SQLException {
        // Arrange
        var firstName = "J-P";
        var lastName = "L-M";
        // Act
        employeeStorage.createEmployee(new EmployeeCreation(firstName, lastName));
        // Assert
        var employees = employeeStorage.getEmployees();
        assertTrue(
                employees.stream().anyMatch(x ->
                        x.getFirstName().equals("J-P") &&
                                x.getLastName().equals("L-M")));
    }

    @Test
    @DisplayName("Employee: test of employee creation through EmployeeStorageImpl")
    void mustSaveCustomerInDatabaseWhenCallingCreateEmployeeImpl() throws SQLException {
        // Arrange
        var firstName = "MCJackie";
        var lastName = "Potato";
        // Act
        employeeStorageImpl.createEmployee(new EmployeeCreation(firstName, lastName));
        // Assert
        var employees = employeeStorageImpl.getEmployees();
        assertTrue(
                employees.stream().anyMatch(x ->
                        x.getFirstName().equals("J-P") &&
                                x.getLastName().equals("L-M")));
    }
}
