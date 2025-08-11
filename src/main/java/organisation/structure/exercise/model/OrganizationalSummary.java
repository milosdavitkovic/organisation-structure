package organisation.structure.exercise.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a summary of the organizational structure.
 * Contains key metrics and statistics about the organization.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationalSummary {
    
    private Employee ceo;
    private int totalEmployees;
    private int managers;
    private int individualContributors;
    private double totalSalaryBudget;
    private double averageSalary;
    private int maxReportingLevel;
    
    /**
     * Creates an organizational summary with calculated values.
     */
    public OrganizationalSummary(Employee ceo, int totalEmployees, int managers, 
                               double totalSalaryBudget, double averageSalary, int maxReportingLevel) {
        this.ceo = ceo;
        this.totalEmployees = totalEmployees;
        this.managers = managers;
        this.individualContributors = totalEmployees - managers;
        this.totalSalaryBudget = totalSalaryBudget;
        this.averageSalary = averageSalary;
        this.maxReportingLevel = maxReportingLevel;
    }
    
    /**
     * Gets the CEO of the organization.
     */
    public Employee getCeo() {
        return ceo;
    }
    
    /**
     * Gets the total number of employees.
     */
    public int getTotalEmployees() {
        return totalEmployees;
    }
    
    /**
     * Gets the number of managers.
     */
    public int getManagers() {
        return managers;
    }
    
    /**
     * Gets the number of individual contributors.
     */
    public int getIndividualContributors() {
        return individualContributors;
    }
    
    /**
     * Gets the total salary budget.
     */
    public double getTotalSalaryBudget() {
        return totalSalaryBudget;
    }
    
    /**
     * Gets the average salary.
     */
    public double getAverageSalary() {
        return averageSalary;
    }
    
    /**
     * Gets the maximum reporting level in the organization.
     */
    public int getMaxReportingLevel() {
        return maxReportingLevel;
    }
}
