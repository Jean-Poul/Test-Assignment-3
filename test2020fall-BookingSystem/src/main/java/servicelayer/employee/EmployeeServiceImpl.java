package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeStorage employeeStorage;

    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }

    @Override
    public int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException {
        try {
            return employeeStorage.createEmployee(new EmployeeCreation(firstName, lastName));
        } catch (SQLException e) {
            throw new EmployeeServiceException(e.getMessage());
        }
    }

    @Override
    public Collection<Employee> getEmployeeById(int employeeId) throws SQLException {
        return employeeStorage.getEmployeeWithId(employeeId);
    }
}
