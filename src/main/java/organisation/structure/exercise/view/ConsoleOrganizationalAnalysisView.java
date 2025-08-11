package organisation.structure.exercise.view;

import organisation.structure.exercise.model.AnalysisResult;
import org.springframework.stereotype.Component;

/**
 * Console-based view implementation for organizational analysis.
 * Displays analysis results to the console output.
 */
@Component
public class ConsoleOrganizationalAnalysisView implements IOrganizationalAnalysisView {
    
    @Override
    public void displayAnalysisResult(AnalysisResult result) {
        if (result.isSuccess()) {
            System.out.println("=== ANALYSIS COMPLETED SUCCESSFULLY ===");
            
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
    public void displayUsageInstructions(String instructions) {
        System.out.println(instructions);
    }
    
    @Override
    public void displayError(String errorMessage) {
        System.err.println("ERROR: " + errorMessage);
    }
    
    @Override
    public void displaySuccess(String message) {
        System.out.println("SUCCESS: " + message);
    }
    
    private void displayOrganizationalSummary(organisation.structure.exercise.model.OrganizationalSummary summary) {
        System.out.println("\n=== ORGANIZATIONAL SUMMARY ===");
        System.out.printf("CEO: %s (ID: %s)%n", 
            summary.getCeo().getFullName(), summary.getCeo().getId());
        System.out.printf("Total Employees: %d%n", summary.getTotalEmployees());
        System.out.printf("Managers: %d%n", summary.getManagers());
        System.out.printf("Individual Contributors: %d%n", summary.getIndividualContributors());
        System.out.printf("Total Salary Budget: $%.2f%n", summary.getTotalSalaryBudget());
        System.out.printf("Average Salary: $%.2f%n", summary.getAverageSalary());
    }
    
    private void displayUnderpaidManagers(java.util.List<organisation.structure.exercise.model.Employee> managers) {
        System.out.println("\n=== MANAGER SALARY ANALYSIS ===");
        System.out.println("⚠ UNDERPAID MANAGERS:");
        for (organisation.structure.exercise.model.Employee manager : managers) {
            double underpayment = manager.getUnderpaymentAmount();
            System.out.printf("  - %s (ID: %s): Underpaid by $%.2f%n", 
                manager.getFullName(), manager.getId(), underpayment);
        }
    }
    
    private void displayOverpaidManagers(java.util.List<organisation.structure.exercise.model.Employee> managers) {
        System.out.println("⚠ OVERPAID MANAGERS:");
        for (organisation.structure.exercise.model.Employee manager : managers) {
            double overpayment = manager.getOverpaymentAmount();
            System.out.printf("  - %s (ID: %s): Overpaid by $%.2f%n", 
                manager.getFullName(), manager.getId(), overpayment);
        }
    }
    
    private void displayLongReportingLines(java.util.List<organisation.structure.exercise.model.Employee> employees) {
        System.out.println("\n=== REPORTING LINE ANALYSIS ===");
        System.out.println("⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:");
        for (organisation.structure.exercise.model.Employee employee : employees) {
            int excessLevels = employee.getExcessReportingLevels();
            System.out.printf("  - %s (ID: %s): %d levels too deep (Level %d)%n", 
                employee.getFullName(), employee.getId(), 
                excessLevels, employee.getReportingLevel());
        }
    }
}
