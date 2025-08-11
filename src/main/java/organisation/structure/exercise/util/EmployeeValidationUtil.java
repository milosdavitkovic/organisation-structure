package organisation.structure.exercise.util;

import organisation.structure.exercise.model.Employee;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for employee data validation operations.
 * Provides methods to validate employee data integrity and organizational structure.
 */
@Slf4j
public class EmployeeValidationUtil {
    
    private static final double MIN_SALARY = 0.0;
    private static final double MAX_SALARY = 1000000.0; // $1M max salary
    
    /**
     * Validates a list of employees for data integrity.
     * 
     * @param employees List of employees to validate
     * @return true if all employees are valid, false otherwise
     */
    public static boolean validateEmployees(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            log.warn("Employee list is null or empty");
            return false;
        }
        
        // Check for duplicate IDs
        if (hasDuplicateIds(employees)) {
            log.warn("Duplicate employee IDs found");
            return false;
        }
        
        // Check for valid employee data
        for (Employee employee : employees) {
            if (!isValidEmployee(employee)) {
                log.warn("Invalid employee data found: {}", employee.getId());
                return false;
            }
        }
        
        // Check for valid organizational structure
        if (!isValidOrganizationalStructure(employees)) {
            log.warn("Invalid organizational structure found");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates a single employee for data integrity.
     * 
     * @param employee The employee to validate
     * @return true if the employee is valid, false otherwise
     */
    public static boolean isValidEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        // Validate ID
        if (employee.getId() == null || employee.getId().trim().isEmpty()) {
            log.debug("Employee ID is null or empty");
            return false;
        }
        
        // Validate first name
        if (employee.getFirstName() == null || employee.getFirstName().trim().isEmpty()) {
            log.debug("Employee first name is null or empty for ID: {}", employee.getId());
            return false;
        }
        
        // Validate last name
        if (employee.getLastName() == null || employee.getLastName().trim().isEmpty()) {
            log.debug("Employee last name is null or empty for ID: {}", employee.getId());
            return false;
        }
        
        // Validate salary
        if (employee.getSalary() < MIN_SALARY || employee.getSalary() > MAX_SALARY) {
            log.debug("Invalid salary: {} for employee ID: {}", employee.getSalary(), employee.getId());
            return false;
        }
        
        return true;
    }
    
    /**
     * Checks for duplicate employee IDs.
     * 
     * @param employees List of employees to check
     * @return true if duplicate IDs are found, false otherwise
     */
    public static boolean hasDuplicateIds(List<Employee> employees) {
        Map<String, Long> idCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getId, Collectors.counting()));
        
        return idCounts.values().stream().anyMatch(count -> count > 1);
    }
    
    /**
     * Validates the organizational structure for consistency.
     * 
     * @param employees List of employees to validate
     * @return true if the structure is valid, false otherwise
     */
    public static boolean isValidOrganizationalStructure(List<Employee> employees) {
        // Check for exactly one CEO
        long ceoCount = employees.stream()
                .filter(Employee::isCEO)
                .count();
        
        if (ceoCount != 1) {
            log.warn("Invalid CEO count: {} (expected 1)", ceoCount);
            return false;
        }
        
        // Check for valid manager references
        for (Employee employee : employees) {
            if (!employee.isCEO() && !isValidManagerReference(employee, employees)) {
                log.warn("Invalid manager reference for employee ID: {}", employee.getId());
                return false;
            }
        }
        
        // Check for circular references
        if (hasCircularReferences(employees)) {
            log.warn("Circular references found in organizational structure");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validates if an employee's manager reference is valid.
     * 
     * @param employee The employee to check
     * @param allEmployees List of all employees
     * @return true if the manager reference is valid, false otherwise
     */
    public static boolean isValidManagerReference(Employee employee, List<Employee> allEmployees) {
        if (employee.isCEO()) {
            return true;
        }
        
        String managerId = employee.getManagerId();
        if (managerId == null || managerId.trim().isEmpty()) {
            log.debug("Non-CEO employee has no manager ID: {}", employee.getId());
            return false;
        }
        
        // Check if manager exists
        boolean managerExists = allEmployees.stream()
                .anyMatch(emp -> emp.getId().equals(managerId));
        
        if (!managerExists) {
            log.debug("Manager not found for employee ID: {} (manager ID: {})", 
                     employee.getId(), managerId);
            return false;
        }
        
        return true;
    }
    
    /**
     * Checks for circular references in the organizational structure.
     * 
     * @param employees List of employees to check
     * @return true if circular references are found, false otherwise
     */
    public static boolean hasCircularReferences(List<Employee> employees) {
        for (Employee employee : employees) {
            if (hasCircularReference(employee, employees)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if a specific employee has a circular reference.
     * 
     * @param employee The employee to check
     * @param allEmployees List of all employees
     * @return true if a circular reference is found, false otherwise
     */
    private static boolean hasCircularReference(Employee employee, List<Employee> allEmployees) {
        if (employee.isCEO()) {
            return false;
        }
        
        String currentManagerId = employee.getManagerId();
        int maxDepth = allEmployees.size(); // Prevent infinite loops
        
        for (int depth = 0; depth < maxDepth; depth++) {
            if (currentManagerId == null || currentManagerId.trim().isEmpty()) {
                return false; // Reached CEO
            }
            
            if (currentManagerId.equals(employee.getId())) {
                return true; // Circular reference found
            }
            
            // Find the manager
            final String managerIdToFind = currentManagerId;
            Employee manager = allEmployees.stream()
                    .filter(emp -> emp.getId().equals(managerIdToFind))
                    .findFirst()
                    .orElse(null);
            
            if (manager == null) {
                return false; // Invalid manager reference
            }
            
            currentManagerId = manager.getManagerId();
        }
        
        return false;
    }
    
    /**
     * Validates salary distribution for reasonableness.
     * 
     * @param employees List of employees to validate
     * @return true if salary distribution is reasonable, false otherwise
     */
    public static boolean isValidSalaryDistribution(List<Employee> employees) {
        if (employees.isEmpty()) {
            return true;
        }
        
        double minSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .min()
                .orElse(0.0);
        
        double maxSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .max()
                .orElse(0.0);
        
        double avgSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
        
        // Check for reasonable salary ranges
        if (minSalary < 10000) { // Minimum $10K salary
            log.warn("Very low minimum salary: {}", minSalary);
            return false;
        }
        
        if (maxSalary > 500000) { // Maximum $500K salary
            log.warn("Very high maximum salary: {}", maxSalary);
            return false;
        }
        
        // Check for reasonable salary spread
        double salarySpread = maxSalary / minSalary;
        if (salarySpread > 100) { // Max 100x difference
            log.warn("Very large salary spread: {}", salarySpread);
            return false;
        }
        
        return true;
    }
    
    /**
     * Finds orphaned employees (employees with invalid manager references).
     * 
     * @param employees List of employees to check
     * @return List of orphaned employees
     */
    public static List<Employee> findOrphanedEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> !employee.isCEO() && !isValidManagerReference(employee, employees))
                .collect(Collectors.toList());
    }
    
    /**
     * Finds employees with missing data.
     * 
     * @param employees List of employees to check
     * @return List of employees with missing data
     */
    public static List<Employee> findEmployeesWithMissingData(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> !isValidEmployee(employee))
                .collect(Collectors.toList());
    }
}
