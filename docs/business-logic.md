# Business Logic Documentation

This document provides a comprehensive explanation of the business rules, calculations, and logic implemented in the Organizational Structure Analysis application.

## 🎯 Business Rules Overview

The application implements three main categories of business rules:

1. **Manager Salary Analysis Rules**
2. **Reporting Line Analysis Rules**
3. **Organizational Structure Rules**

## 💰 Manager Salary Analysis

### Core Principle
Managers should be compensated fairly relative to their direct subordinates, with a target range that ensures appropriate compensation for increased responsibility while maintaining internal equity.

### Salary Ratio Calculation

#### Formula
```
Salary Ratio = Manager's Salary / Average Subordinate Salary
```

#### Example Calculation
```
Manager Salary: $60,000
Subordinate 1: $45,000
Subordinate 2: $47,000
Subordinate 3: $50,000

Average Subordinate Salary = (45,000 + 47,000 + 50,000) / 3 = $47,333.33
Salary Ratio = 60,000 / 47,333.33 = 1.27
```

### Compensation Thresholds

#### Underpaid Managers
- **Condition**: Salary Ratio < 1.2 (120%)
- **Business Rule**: Managers should earn at least 20% more than their subordinates' average salary
- **Rationale**: Ensures managers are adequately compensated for increased responsibility

#### Fairly Paid Managers
- **Condition**: 1.2 ≤ Salary Ratio ≤ 1.5 (120% - 150%)
- **Business Rule**: Managers should earn between 20% and 50% more than their subordinates' average salary
- **Rationale**: Provides appropriate compensation range for management responsibilities

#### Overpaid Managers
- **Condition**: Salary Ratio > 1.5 (150%)
- **Business Rule**: Managers should not earn more than 50% more than their subordinates' average salary
- **Rationale**: Prevents excessive compensation disparities and maintains internal equity

### Amount Calculations

#### Underpayment Amount
```
Underpayment Amount = (Average Subordinate Salary × 1.2) - Manager's Salary
```

**Example:**
```
Manager Salary: $50,000
Average Subordinate Salary: $45,000
Target Salary: $45,000 × 1.2 = $54,000
Underpayment Amount: $54,000 - $50,000 = $4,000
```

#### Overpayment Amount
```
Overpayment Amount = Manager's Salary - (Average Subordinate Salary × 1.5)
```

**Example:**
```
Manager Salary: $80,000
Average Subordinate Salary: $45,000
Target Salary: $45,000 × 1.5 = $67,500
Overpayment Amount: $80,000 - $67,500 = $12,500
```

### Edge Cases and Special Considerations

#### Managers with No Subordinates
- **Rule**: Not included in salary analysis
- **Rationale**: Cannot calculate meaningful salary ratio without subordinates
- **Implementation**: Filtered out before analysis

#### Single Subordinate
- **Rule**: Treated the same as multiple subordinates
- **Calculation**: Uses the single subordinate's salary as the average
- **Consideration**: May need special handling for very small teams

#### Zero or Negative Salaries
- **Rule**: Invalid data - excluded from analysis
- **Rationale**: Negative salaries are not meaningful in this context
- **Implementation**: Validation during CSV parsing

## 📊 Reporting Line Analysis

### Core Principle
Organizations should maintain reasonable reporting line depths to ensure effective communication, decision-making, and organizational agility.

### Reporting Level Calculation

#### Level Assignment Rules
- **Level 0**: CEO (Chief Executive Officer)
- **Level 1**: Direct reports to CEO
- **Level 2**: Reports to Level 1 managers
- **Level 3**: Reports to Level 2 managers
- **Level 4**: Reports to Level 3 managers
- **Level 5+**: Too deep (violation)

#### Algorithm Implementation
```java
private void calculateReportingLevels(Employee employee, int level) {
    employee.setReportingLevel(level);
    for (Employee subordinate : employee.getDirectSubordinates()) {
        calculateReportingLevels(subordinate, level + 1);
    }
}
```

### Maximum Depth Rule

#### Business Rule
- **Maximum Allowed Levels**: 4 levels between employee and CEO
- **Violation Threshold**: Level 5 or higher
- **Rationale**: Maintains organizational efficiency and communication effectiveness

#### Excess Level Calculation
```
Excess Levels = Employee's Level - 4
```

**Examples:**
- Level 5 employee: 5 - 4 = 1 excess level
- Level 6 employee: 6 - 4 = 2 excess levels
- Level 4 employee: 4 - 4 = 0 excess levels (acceptable)

### Organizational Structure Validation

#### CEO Identification
- **Rule**: Exactly one employee must have no manager (empty managerId)
- **Validation**: Application verifies CEO exists and is unique
- **Error Handling**: Clear error message if no CEO or multiple CEOs found

#### Manager Reference Validation
- **Rule**: All managerId values must reference existing employee IDs
- **Validation**: Checked during hierarchy building
- **Error Handling**: Warning messages for missing manager references

#### Circular Reference Prevention
- **Rule**: Employee cannot manage themselves
- **Validation**: Implicitly prevented by hierarchy building algorithm
- **Implementation**: Employee cannot be added to their own subordinates list

## 🏢 Organizational Structure Rules

### Hierarchy Building

#### Parent-Child Relationship
- **Rule**: Each employee (except CEO) has exactly one direct manager
- **Implementation**: managerId field establishes parent-child relationships
- **Data Structure**: Tree structure with CEO as root

#### Subordinate Collection
- **Rule**: Managers maintain list of direct subordinates
- **Implementation**: `List<Employee> directSubordinates` field
- **Purpose**: Enables hierarchical traversal and analysis

### Data Integrity Rules

#### Unique Employee IDs
- **Rule**: Each employee must have a unique ID
- **Validation**: Checked during CSV parsing
- **Error Handling**: Duplicate IDs cause parsing errors

#### Valid Salary Values
- **Rule**: Salaries must be positive numbers
- **Validation**: Numeric parsing with range checking
- **Error Handling**: Invalid salaries cause parsing errors

#### Required Fields
- **Rule**: id, firstName, lastName, salary are required
- **Validation**: CSV parsing validates field presence
- **Error Handling**: Missing required fields cause parsing errors

## 📈 Analysis Workflow

### Step-by-Step Process

#### 1. Data Loading and Validation
```
CSV File → Parse Lines → Validate Format → Create Employee Objects
```

**Validation Steps:**
- Header row validation
- Field count validation
- Data type validation
- Business rule validation

#### 2. Hierarchy Construction
```
Employee List → Build Employee Map → Establish Relationships → Find CEO
```

**Construction Steps:**
- Create lookup map for O(1) employee access
- Build parent-child relationships
- Identify and validate CEO
- Handle missing manager references

#### 3. Reporting Level Calculation
```
CEO → Recursive Level Assignment → Complete Level Mapping
```

**Calculation Steps:**
- Start from CEO (level 0)
- Recursively assign levels to all subordinates
- Track maximum depth for validation

#### 4. Manager Salary Analysis
```
Manager Identification → Subordinate Analysis → Ratio Calculation → Classification
```

**Analysis Steps:**
- Identify employees with subordinates
- Calculate average subordinate salary for each manager
- Compute salary ratios
- Classify as underpaid, fairly paid, or overpaid
- Calculate exact amounts

#### 5. Reporting Line Analysis
```
Level Identification → Depth Validation → Violation Reporting
```

**Analysis Steps:**
- Identify employees at level 5 or higher
- Calculate excess levels
- Generate violation reports

#### 6. Results Compilation
```
Analysis Results → Format Output → Present Findings
```

**Compilation Steps:**
- Organize findings by category
- Format monetary amounts
- Generate summary statistics
- Present actionable insights

## 🔍 Business Logic Implementation

### Employee Model Methods

#### Salary Analysis Methods
```java
public double getAverageSubordinateSalary() {
    if (directSubordinates.isEmpty()) {
        return 0.0;
    }
    return directSubordinates.stream()
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0.0);
}

public double getSalaryRatioToAverage() {
    double avgSubordinateSalary = getAverageSubordinateSalary();
    if (avgSubordinateSalary == 0.0) {
        return 0.0;
    }
    return salary / avgSubordinateSalary;
}

public boolean isUnderpaid() {
    double ratio = getSalaryRatioToAverage();
    return ratio < 1.2;
}

public boolean isOverpaid() {
    double ratio = getSalaryRatioToAverage();
    return ratio > 1.5;
}
```

#### Reporting Line Methods
```java
public boolean hasTooLongReportingLine() {
    return reportingLevel > 4;
}

public int getExcessReportingLevels() {
    return Math.max(0, reportingLevel - 4);
}
```

### Service Layer Implementation

#### Analysis Orchestration
```java
public void analyzeOrganizationalStructure(@NonNull final List<Employee> employees) {
    // 1. Build organizational hierarchy
    Map<String, Employee> employeeMap = buildEmployeeMap(employees);
    Employee ceo = findCEO(employees);
    
    // 2. Build reporting relationships
    buildReportingRelationships(employeeMap);
    
    // 3. Calculate reporting levels
    calculateReportingLevels(ceo, 0);
    
    // 4. Perform analyses
    analyzeManagerSalaries(employees);
    analyzeReportingLines(employees);
}
```

## 🎯 Business Impact and Recommendations

### Salary Analysis Insights

#### Underpaid Managers
- **Impact**: Potential retention risk, decreased motivation
- **Recommendations**: 
  - Review compensation policies
  - Consider market benchmarking
  - Implement salary adjustments

#### Overpaid Managers
- **Impact**: Internal equity issues, budget inefficiency
- **Recommendations**:
  - Review compensation justification
  - Consider performance-based adjustments
  - Implement salary freezes if appropriate

### Reporting Line Insights

#### Long Reporting Lines
- **Impact**: Communication delays, decision-making bottlenecks
- **Recommendations**:
  - Restructure organizational hierarchy
  - Implement matrix organizations
  - Delegate decision-making authority

#### Optimal Structure
- **Target**: 3-4 levels maximum
- **Benefits**: Improved communication, faster decisions, better employee engagement

## 🔧 Configuration and Customization

### Configurable Parameters

#### Salary Thresholds
- **Current**: 120% (underpaid) and 150% (overpaid)
- **Customization**: Can be made configurable via properties
- **Implementation**: Constants in Employee model

#### Reporting Depth Limits
- **Current**: Maximum 4 levels
- **Customization**: Can be adjusted for different organizational models
- **Implementation**: Constant in reporting line analysis

### Future Enhancements

#### Industry-Specific Rules
- **Technology**: May allow deeper hierarchies due to specialized roles
- **Manufacturing**: May require flatter structures for efficiency
- **Consulting**: May have different compensation models

#### Size-Based Adjustments
- **Small Companies**: May have different salary ratio expectations
- **Large Companies**: May need more sophisticated analysis
- **Multi-National**: May require regional considerations

---

*For technical implementation details, see the [Architecture](architecture.md) and [API Reference](api-reference.md) documentation.*
