package org.example.services;
import org.example.entities.Employee;

public class PayrollService {
    public static final int BASE_HOURS_TO_GET_BONUS = 38;
    public static final int BASE_HOURS_TO_GET_EXTRA = 40;
    public static final double EXTRA_HOURS_RATE = 1.5;
    public static final double SALARY_BONUS = 500.0;
    public static final double MAX_SALARY = 20000.0;

    public double calculatePayroll(Employee employee, double hoursWorked) {
        if (employee == null) throw new NullPointerException("El empleado no puede ser nulo.");
        if (hoursWorked < 0) throw new IllegalArgumentException("Las horas trabajadas no pueden ser negativas.");
        if (employee.getHourlyRate() <= 0) throw new IllegalArgumentException("La tarifa por hora debe ser mayor que cero.");
        if (hoursWorked == 0) return 0.0;

        double salaryToPay;

        switch (employee.getEmployeeType()) {
            case FULL_TIME -> salaryToPay = calculateFullTimeSalary(employee, hoursWorked);
            case PART_TIME -> salaryToPay = calculatePartTimeSalary(employee, hoursWorked);
            default ->
                    throw new IllegalArgumentException("Tipo de empleado no soportado: " + employee.getEmployeeType());
        };

        boolean salaryExceededLimit = salaryToPay > MAX_SALARY;

        if (salaryExceededLimit && !employee.isAllowToExceedMaxSalary()) {
            throw new IllegalStateException("El salario calculado excede el máximo permitido sin autorización: " + MAX_SALARY);
        }

        return salaryToPay;
    }

    private double calculateFullTimeSalary(Employee employee, double hoursWorked) {
        double regularHours = Math.min(hoursWorked, BASE_HOURS_TO_GET_EXTRA);
        double extraHours = Math.max(0, hoursWorked - BASE_HOURS_TO_GET_EXTRA);
        double hourlyRate = employee.getHourlyRate();
        boolean canGetBonus = hoursWorked > BASE_HOURS_TO_GET_BONUS;

        double salaryToPay = hourlyRate * regularHours;
        salaryToPay += extraHours * hourlyRate * EXTRA_HOURS_RATE;
        if (canGetBonus) salaryToPay += SALARY_BONUS;

        return salaryToPay;
    }

    private double calculatePartTimeSalary(Employee employee, double hoursWorked) {
        double hourlyRate = employee.getHourlyRate();
        boolean canGetBonus = hoursWorked > BASE_HOURS_TO_GET_BONUS;

        double salaryToPay = hourlyRate * hoursWorked;
        if (canGetBonus) salaryToPay += SALARY_BONUS;

        return salaryToPay;
    }
}
