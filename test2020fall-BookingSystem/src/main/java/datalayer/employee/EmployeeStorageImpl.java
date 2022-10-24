package datalayer.employee;

import dto.Customer;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeStorageImpl implements EmployeeStorage {
    private final String connectionString;
    private final String username;
    private final String password;

    public EmployeeStorageImpl(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createEmployee(EmployeeCreation employee) throws SQLException {
        var sql = "insert into Employees(firstname, lastname) values (?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    public List<Employee> getEmployees() throws SQLException {
        try (var con = getConnection();
             var stmt = con.createStatement()) {
            var results = new ArrayList<Employee>();

            try (ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname from Employees")) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");

                    Employee e = new Employee(id, firstname, lastname);
                    results.add(e);
                }
            }
            return results;
        }
    }


    @Override
    public Collection<Employee> getEmployeeWithId(int employeeId) throws SQLException {
        var sql = "select ID, firstname, lastname from Employees where id = ?";
        try (var con = getConnection();
             // var stmt = con.createStatement()) {
             var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            var results = new ArrayList<Employee>();

            //try (ResultSet resultSet = stmt.executeQuery("select ID, firstname, lastname from Employees")) {
            try (ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");

                    Employee employee = new Employee(id, firstname, lastname);
                    results.add(employee);
                }
            }
            return results;
        }
    }

}
