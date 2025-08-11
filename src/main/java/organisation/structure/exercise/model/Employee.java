package organisation.structure.exercise.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the organizational structure.
 * Contains employee information and organizational hierarchy data.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    private String id;
    private String firstName;
    private String lastName;
    private double salary;
    private String managerId;
    private int reportingLevel;
    private List<Employee> directSubordinates = new ArrayList<>();
    
    public Employee(String id, String firstName, String lastName, double salary, String managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
        this.reportingLevel = 0;
        this.directSubordinates = new ArrayList<>();
    }
    
    /**
     * Returns the full name of the employee.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * Checks if this employee is the CEO (has no manager).
     */
    public boolean isCEO() {
        return managerId == null || managerId.isEmpty();
    }
    
    /**
     * Checks if this employee has direct subordinates.
     */
    public boolean hasSubordinates() {
        return !directSubordinates.isEmpty();
    }
    
    /**
     * Calculates the average salary of direct subordinates.
     */
    public double getAverageSubordinateSalary() {
        if (directSubordinates.isEmpty()) {
            return 0.0;
        }
        
        double totalSalary = directSubordinates.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        
        return totalSalary / directSubordinates.size();
    }
    
    /**
     * Calculates the salary ratio compared to average subordinate salary.
     */
    public double getSalaryRatioToAverage() {
        double avgSubordinateSalary = getAverageSubordinateSalary();
        if (avgSubordinateSalary == 0.0) {
            return 0.0;
        }
        return salary / avgSubordinateSalary;
    }
    
    /**
     * Checks if the employee is underpaid (earns less than 20% more than average subordinates).
     */
    public boolean isUnderpaid() {
        if (!hasSubordinates()) {
            return false;
        }
        return getSalaryRatioToAverage() < 1.2;
    }
    
    /**
     * Checks if the employee is overpaid (earns more than 50% more than average subordinates).
     */
    public boolean isOverpaid() {
        if (!hasSubordinates()) {
            return false;
        }
        return getSalaryRatioToAverage() > 1.5;
    }
    
    /**
     * Calculates the amount by which the employee is underpaid.
     */
    public double getUnderpaymentAmount() {
        if (!isUnderpaid()) {
            return 0.0;
        }
        double avgSubordinateSalary = getAverageSubordinateSalary();
        return (avgSubordinateSalary * 1.2) - salary;
    }
    
    /**
     * Calculates the amount by which the employee is overpaid.
     */
    public double getOverpaymentAmount() {
        if (!isOverpaid()) {
            return 0.0;
        }
        double avgSubordinateSalary = getAverageSubordinateSalary();
        return salary - (avgSubordinateSalary * 1.5);
    }
    
    /**
     * Checks if the employee has too long reporting line (more than 4 levels from CEO).
     */
    public boolean hasTooLongReportingLine() {
        return reportingLevel > 4;
    }
    
    /**
     * Calculates the number of excess reporting levels.
     */
    public int getExcessReportingLevels() {
        return Math.max(0, reportingLevel - 4);
    }
}
