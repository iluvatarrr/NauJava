package com.naumen;

import java.util.List;
import java.util.Scanner;

public class AverageSalaryTask implements TaskRunnable {
    private static final Scanner scanner = new Scanner(System.in);
    private final List<Employee> EMPLOYEES;

    public AverageSalaryTask(List<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            EMPLOYEES =  List.of(
                    new Employee("Иван Иванов", 30, "it", 1000.0),
                    new Employee("Петр Петров", 25, "hr", 1000.0),
                    new Employee("Мария Сидорова", 35, "finance", 1000.0),
                    new Employee("Анна Козлова", 28, "marketing", 1000.0),
                    new Employee("Сергей Смирнов", 40, "it", 1000.0));
        } else {
            EMPLOYEES = employees;
        }
    }

    private void getAverageSalary() {
        System.out.println("[Average] Print department (it,hr,finance,marketing):");
        String department = scanner.nextLine().toLowerCase();
        if (isValidDepartment(department)) {
            System.out.println(findAverageSalary(department));
        } else {
            System.out.println("Department is not valid");
        }
    }

    private boolean isValidDepartment(String department) {
        return department != null &&
                !department.isEmpty() &&
                EMPLOYEES.stream()
                        .anyMatch(el -> el
                                .getDepartment()
                                .equalsIgnoreCase(department));
    }

    private double findAverageSalary(String department) {
       return EMPLOYEES.stream()
                .filter(employee -> employee
                        .getDepartment()
                        .equalsIgnoreCase(department))
               .mapToDouble(Employee::getSalary)
               .average()
               .orElse(0.0);
    }

    @Override
    public void run() {
        getAverageSalary();
    }
}