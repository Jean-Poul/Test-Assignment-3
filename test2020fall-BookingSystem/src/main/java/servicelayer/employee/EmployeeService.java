package servicelayer.employee;

import dto.Employee;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public interface EmployeeService {
    int createEmployee(String firstName, String lastName, Date birthdate) throws EmployeeServiceException;

    Collection<Employee> getEmployeeById(int employeeId) throws SQLException;
}
