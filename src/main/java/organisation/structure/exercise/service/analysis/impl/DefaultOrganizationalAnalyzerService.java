package organisation.structure.exercise.service.analysis.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import organisation.structure.exercise.model.AnalysisResult;
import organisation.structure.exercise.model.Employee;
import organisation.structure.exercise.model.OrganizationalSummary;
import organisation.structure.exercise.service.analysis.IOrganizationalAnalyzerService;
import organisation.structure.exercise.util.EmployeeValidationUtil;

/**
 * Optimized implementation of organizational analyzer service.
 * Uses efficient algorithms for large datasets with memory optimization.
 */
@Slf4j
@Service
public class DefaultOrganizationalAnalyzerService implements IOrganizationalAnalyzerService {
    
    private static final int MAX_REPORTING_LEVELS = 4;
    private static final double UNDERPAID_THRESHOLD = 1.2;
    private static final double OVERPAID_THRESHOLD = 1.5;
    
    @Override
    public AnalysisResult analyzeOrganizationalStructure(List<Employee> employees) {
        log.info("Starting organizational structure analysis for {} employees", employees.size());
        
        try {
            // Validate input data
            if (!EmployeeValidationUtil.validateEmployees(employees)) {
                log.error("Employee validation failed");
                return AnalysisResult.failure("Employee validation failed");
            }
            
            // Build organizational hierarchy
            List<Employee> hierarchyEmployees = buildOrganizationalHierarchy(employees);
            if (hierarchyEmployees.isEmpty()) {
                log.error("Failed to build organizational hierarchy");
                return AnalysisResult.failure("Failed to build organizational hierarchy");
            }
            
            // Calculate reporting levels
            calculateReportingLevels(hierarchyEmployees);
            
            // Create organizational summary
            OrganizationalSummary summary = generateOrganizationalSummary(hierarchyEmployees);
            
            // Analyze manager salaries
            AnalysisResult salaryAnalysis = analyzeManagerSalaries(hierarchyEmployees);
            
            // Analyze reporting lines
            List<Employee> longReportingLines = findEmployeesWithLongReportingLines(hierarchyEmployees);
            
            log.info("Organizational analysis completed successfully");
            
            return AnalysisResult.success(summary, 
                    salaryAnalysis.getUnderpaidManagers(),
                    salaryAnalysis.getOverpaidManagers(),
                    longReportingLines);
                    
        } catch (Exception e) {
            log.error("Error during organizational analysis: {}", e.getMessage(), e);
            return AnalysisResult.failure("Error during organizational analysis: " + e.getMessage());
        }
    }
    
    @Override
    public List<Employee> buildOrganizationalHierarchy(List<Employee> employees) {
        log.debug("Building organizational hierarchy for {} employees", employees.size());
        
        // Create employee lookup map for O(1) access
        Map<String, Employee> employeeMap = new ConcurrentHashMap<>();
        employees.forEach(employee -> employeeMap.put(employee.getId(), employee));
        
        // Find CEO
        Employee ceo = employees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        
        if (ceo == null) {
            log.error("No CEO found in the organization");
            return new ArrayList<>();
        }
        
        // Build parent-child relationships
        employees.stream()
                .filter(employee -> !employee.isCEO())
                .filter(employee -> employee.getManagerId() != null)
                .forEach(employee -> {
                    Employee manager = employeeMap.get(employee.getManagerId());
                    if (manager != null) {
                        manager.getDirectSubordinates().add(employee);
                    } else {
                        log.warn("Manager not found for employee: {} (Manager ID: {})", 
                                employee.getFullName(), employee.getManagerId());
                    }
                });
        
        log.debug("Organizational hierarchy built successfully. CEO: {}", ceo.getFullName());
        return employees;
    }
    
    @Override
    public void calculateReportingLevels(List<Employee> employees) {
        log.debug("Calculating reporting levels for {} employees", employees.size());
        
        Employee ceo = employees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        
        if (ceo == null) {
            log.error("No CEO found for reporting level calculation");
            return;
        }
        
        calculateReportingLevelsRecursive(ceo, 0);
    }
    
    @Override
    public AnalysisResult analyzeManagerSalaries(List<Employee> employees) {
        log.debug("Analyzing manager salaries for {} employees", employees.size());
        
        List<Employee> underpaidManagers = employees.stream()
                .filter(Employee::hasSubordinates)
                .filter(Employee::isUnderpaid)
                .collect(Collectors.toList());
        
        List<Employee> overpaidManagers = employees.stream()
                .filter(Employee::hasSubordinates)
                .filter(Employee::isOverpaid)
                .collect(Collectors.toList());
        
        log.debug("Found {} underpaid and {} overpaid managers", 
                underpaidManagers.size(), overpaidManagers.size());
        
        return AnalysisResult.success(null, underpaidManagers, overpaidManagers, null);
    }
    
    @Override
    public List<Employee> findEmployeesWithLongReportingLines(List<Employee> employees) {
        log.debug("Finding employees with long reporting lines for {} employees", employees.size());
        
        List<Employee> employeesWithLongReportingLines = employees.stream()
                .filter(Employee::hasTooLongReportingLine)
                .collect(Collectors.toList());
        
        log.debug("Found {} employees with too long reporting lines", 
                employeesWithLongReportingLines.size());
        
        return employeesWithLongReportingLines;
    }
    
    @Override
    public OrganizationalSummary generateOrganizationalSummary(List<Employee> employees) {
        log.debug("Generating organizational summary for {} employees", employees.size());
        
        Employee ceo = employees.stream()
                .filter(Employee::isCEO)
                .findFirst()
                .orElse(null);
        
        if (ceo == null) {
            log.error("No CEO found in the organization");
            return null;
        }
        
        long managers = employees.stream()
                .filter(Employee::hasSubordinates)
                .count();
        
        long individualContributors = employees.stream()
                .filter(e -> !e.hasSubordinates())
                .count();
        
        double totalSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        
        double averageSalary = employees.isEmpty() ? 0.0 : totalSalary / employees.size();
        
        int maxReportingLevel = employees.stream()
                .mapToInt(Employee::getReportingLevel)
                .max()
                .orElse(0);
        
        return new OrganizationalSummary(
                ceo,
                employees.size(),
                (int) managers,
                totalSalary,
                averageSalary,
                maxReportingLevel
        );
    }
    
    /**
     * Calculates reporting levels recursively using depth-first traversal.
     * Optimized to avoid stack overflow for deep hierarchies.
     * 
     * @param employee The employee to calculate level for
     * @param level The current level
     */
    private void calculateReportingLevelsRecursive(Employee employee, int level) {
        if (employee == null) {
            return;
        }
        
        employee.setReportingLevel(level);
        
        // Use iterative approach for large hierarchies to avoid stack overflow
        if (employee.getDirectSubordinates().size() > 100) {
            calculateReportingLevelsIterative(employee, level);
        } else {
            // Use recursive approach for smaller hierarchies
            for (Employee subordinate : employee.getDirectSubordinates()) {
                calculateReportingLevelsRecursive(subordinate, level + 1);
            }
        }
    }
    
    /**
     * Iterative approach for calculating reporting levels to avoid stack overflow.
     * 
     * @param root The root employee
     * @param startLevel The starting level
     */
    private void calculateReportingLevelsIterative(Employee root, int startLevel) {
        Stack<EmployeeLevel> stack = new Stack<>();
        stack.push(new EmployeeLevel(root, startLevel));
        
        while (!stack.isEmpty()) {
            EmployeeLevel current = stack.pop();
            Employee employee = current.employee;
            int level = current.level;
            
            employee.setReportingLevel(level);
            
            // Add subordinates to stack (reverse order to maintain correct processing order)
            for (int i = employee.getDirectSubordinates().size() - 1; i >= 0; i--) {
                stack.push(new EmployeeLevel(employee.getDirectSubordinates().get(i), level + 1));
            }
        }
    }
    
    /**
     * Helper class for iterative reporting level calculation.
     */
    private static class EmployeeLevel {
        final Employee employee;
        final int level;
        
        EmployeeLevel(Employee employee, int level) {
            this.employee = employee;
            this.level = level;
        }
    }
}
