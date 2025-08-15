package organisation.structure.exercise.service;

import organisation.structure.exercise.core.model.Employee;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService implements ICsvReaderService {
    
    @Override
    public List<Employee> readEmployeesFromCsv(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header line
                }
                
                Employee employee = parseEmployeeLine(line);
                if (employee != null) {
                    employees.add(employee);
                }
            }
        }
        
        return employees;
    }
    
    private Employee parseEmployeeLine(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 4) {
                System.err.println("Invalid line format: " + line);
                return null;
            }
            
            String id = parts[0].trim();
            String firstName = parts[1].trim();
            String lastName = parts[2].trim();
            double salary = Double.parseDouble(parts[3].trim());
            
            String managerId = null;
            if (parts.length > 4 && !parts[4].trim().isEmpty()) {
                managerId = parts[4].trim();
            }
            
            return new Employee(id, firstName, lastName, salary, managerId);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing salary in line: " + line);
            return null;
        }
    }
    
    @Override
    public boolean validateCsvFile(String filePath) {
        try {
            // Basic validation - check if file exists and is readable
            java.io.File file = new java.io.File(filePath);
            if (!file.exists() || !file.canRead()) {
                return false;
            }
            
            // Check if it's a CSV file
            if (!filePath.toLowerCase().endsWith(".csv")) {
                return false;
            }
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public long getCsvLineCount(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines().count();
        }
    }
}
