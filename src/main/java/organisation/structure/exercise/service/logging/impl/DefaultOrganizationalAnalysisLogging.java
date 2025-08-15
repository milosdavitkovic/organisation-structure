package organisation.structure.exercise.service.logging.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.core.model.OrganizationalSummary;
import organisation.structure.exercise.service.logging.OrganizationalAnalysisLogging;

/**
 * Console-based view implementation for organizational analysis.
 * Displays analysis results to the console output.
 */
@Slf4j
@Service
public class DefaultOrganizationalAnalysisLogging implements OrganizationalAnalysisLogging {
    
    @Override
    public void displayAnalysisResults(AnalysisResult result) {
        if (result.isSuccess()) {
            log.info("=== ANALYSIS COMPLETED SUCCESSFULLY ===");
            
            if (result.getOrganizationalSummary() != null) {
                displayOrganizationalSummary(result.getOrganizationalSummary());
            }
            
            if (result.getUnderpaidManagers() != null && !result.getUnderpaidManagers().isEmpty()) {
                displayUnderpaidManagers(result.getUnderpaidManagers());
            }
            
            if (result.getOverpaidManagers() != null && !result.getOverpaidManagers().isEmpty()) {
                displayOverpaidManagers(result.getOverpaidManagers());
            }
            
            if (result.getEmployeesWithLongReportingLines() != null && !result.getEmployeesWithLongReportingLines().isEmpty()) {
                displayLongReportingLines(result.getEmployeesWithLongReportingLines());
            }
            
        } else {
            displayError(result.getErrorMessage());
        }
    }
    
    @Override
    public void displayOrganizationalSummary(OrganizationalSummary summary) {
        log.info("\n=== ORGANIZATIONAL SUMMARY ===");
        System.out.printf("CEO: %s (ID: %s)%n", 
            summary.getCeo().getFullName(), summary.getCeo().getId());
        System.out.printf("Total Employees: %d%n", summary.getTotalEmployees());
        System.out.printf("Managers: %d%n", summary.getManagers());
        System.out.printf("Individual Contributors: %d%n", summary.getIndividualContributors());
        System.out.printf("Total Salary Budget: $%.2f%n", summary.getTotalSalaryBudget());
        System.out.printf("Average Salary: $%.2f%n", summary.getAverageSalary());
    }
    
    @Override
    public void displayError(String errorMessage) {
        System.err.println("ERROR: " + errorMessage);
    }
    
    @Override
    public void displaySuccess(String message) {
        log.info("SUCCESS: " + message);
    }
    
    @Override
    public void displayInfo(String message) {
        log.info("INFO: " + message);
    }
    

    
    private void displayUnderpaidManagers(java.util.List<organisation.structure.exercise.core.model.Employee> managers) {
        log.info("\n=== MANAGER SALARY ANALYSIS ===");
        log.info("⚠ UNDERPAID MANAGERS:");
        for (organisation.structure.exercise.core.model.Employee manager : managers) {
            double underpayment = manager.getUnderpaymentAmount();
            System.out.printf("  - %s (ID: %s): Underpaid by $%.2f%n", 
                manager.getFullName(), manager.getId(), underpayment);
        }
    }
    
    private void displayOverpaidManagers(java.util.List<organisation.structure.exercise.core.model.Employee> managers) {
        log.info("⚠ OVERPAID MANAGERS:");
        for (organisation.structure.exercise.core.model.Employee manager : managers) {
            double overpayment = manager.getOverpaymentAmount();
            System.out.printf("  - %s (ID: %s): Overpaid by $%.2f%n", 
                manager.getFullName(), manager.getId(), overpayment);
        }
    }
    
    private void displayLongReportingLines(java.util.List<organisation.structure.exercise.core.model.Employee> employees) {
        log.info("\n=== REPORTING LINE ANALYSIS ===");
        log.info("⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:");
        for (organisation.structure.exercise.core.model.Employee employee : employees) {
            int excessLevels = employee.getExcessReportingLevels();
            System.out.printf("  - %s (ID: %s): %d levels too deep (Level %d)%n", 
                employee.getFullName(), employee.getId(), 
                excessLevels, employee.getReportingLevel());
        }
    }
}
