package organisation.structure.exercise.service.logging.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import organisation.structure.exercise.core.model.AnalysisResult;
import organisation.structure.exercise.core.model.Employee;
import organisation.structure.exercise.core.model.OrganizationalSummary;
import organisation.structure.exercise.core.util.LoggingUtil;
import organisation.structure.exercise.service.logging.OrganizationalAnalysisLogging;

import java.util.List;

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
            displaySuccess("[Organization Analyzes] === ANALYSIS COMPLETED SUCCESSFULLY ===");

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
        log.info("------------------------------------------------------");
        log.info("[Organization Analyzes] === ORGANIZATIONAL SUMMARY ===");
        log.info("[Organization Analyzes] CEO: [{}] (ID: [{}]). ",
                summary.getCeo().getFullName(), summary.getCeo().getId());
        log.info("[Organization Analyzes] Total Employees: [{}]", summary.getTotalEmployees());
        log.info("[Organization Analyzes] Managers: [{}]", summary.getManagers());
        log.info("[Organization Analyzes] Individual Contributors: [{}]", summary.getIndividualContributors());
        double totalSalaryBudget = summary.getTotalSalaryBudget();
        log.info("[Organization Analyzes] Total Salary Budget: {}", LoggingUtil.logSwissFrankValue(totalSalaryBudget));
        double averageSalary = summary.getAverageSalary();
        log.info("[Organization Analyzes] Average Salary: {}", LoggingUtil.logSwissFrankValue(averageSalary));
    }

    @Override
    public void displayError(String errorMessage) {
        log.error("[Organization Analyzes] ERROR: {}", errorMessage);
    }

    @Override
    public void displaySuccess(String message) {
        log.info("--------------------------------------------");
        log.info("[Organization Analyzes] {}", message);
        log.info("--------------------------------------------");
    }

    @Override
    public void displayInfo(String message) {
        log.info("[Organization Analyzes] {}", message);
    }


    private void displayUnderpaidManagers(@NonNull final List<Employee> managers) {
        log.info("--------------------------------------------------------");
        log.info("[Organization Analyzes]  === MANAGER SALARY ANALYSIS ===");
        log.info("[Organization Analyzes] ⚠ UNDERPAID MANAGERS:");
        for (Employee manager : managers) {
            double underpayment = manager.getUnderpaymentAmount();
            log.info("[Organization Analyzes] [{}] (ID: [{}]): Underpaid by {}",
                    manager.getFullName(), manager.getId(), LoggingUtil.logSwissFrankValue(underpayment));
        }
    }

    private void displayOverpaidManagers(@NonNull final List<Employee> managers) {
        log.info("--------------------------------------------");
        log.info("[Organization Analyzes] ⚠ OVERPAID MANAGERS:");
        for (Employee manager : managers) {
            double overpayment = manager.getOverpaymentAmount();
            log.info("[Organization Analyzes] [{}] (ID: [{}]): Overpaid by {}",
                    manager.getFullName(), manager.getId(), LoggingUtil.logSwissFrankValue(overpayment));
        }
    }

    private void displayLongReportingLines(@NonNull final List<Employee> employees) {
        log.info("-------------------------------------------------------");
        log.info("[Organization Analyzes] === REPORTING LINE ANALYSIS ===");
        log.info("[Organization Analyzes] ⚠ EMPLOYEES WITH TOO LONG REPORTING LINES:");
        for (Employee employee : employees) {
            int excessLevels = employee.getExcessReportingLevels();
            log.info("[Organization Analyzes] Employee: [{}] (ID: [{}]) is [{}] levels too deep in organization hierarchy (Employee Level: [{}]). " +
                            "Company wants to avoid too long reporting lines, and this employee has more than 4 managers between them and the CEO, which is not allowed.",
                    employee.getFullName(), employee.getId(),
                    excessLevels, employee.getReportingLevel());
        }
    }
}
