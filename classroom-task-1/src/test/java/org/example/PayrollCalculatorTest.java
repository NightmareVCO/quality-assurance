package org.example;
/*
    José Taveras
    Vladimir Curiel - 10141415

    Cobertura mínima de pruebas:
        1.- Cálculo correcto del salario sin horas extra.
        2.- Cálculo correcto del salario con horas extra (solo FULL_TIME).
        3.- No aplicar horas extra a empleados PART_TIME.
        4.- Lanzar excepción para horas negativas.
        5.- Lanzar excepción si el empleado es nulo.
        6.- Validar que el salario no exceda el tope permitido sin alerta.

    Coberturas agregadas:
        1. Lanzar excepción si la tarifa por hora es negativa o nula.
        2. Validar que sea el calculo de nómina sea idempotente.
*/


import org.example.entities.Employee;
import org.example.entities.EmployeeType;
import org.example.services.PayrollService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PayrollCalculatorTest {
    private PayrollService payrollService;
    private List<Employee> employees;

    @BeforeEach
    public void init() {
        payrollService = new PayrollService();
        employees = new ArrayList<>();

        employees.add(new Employee("John"));
        employees.add(null);
    }

    static Stream<Arguments> baseSalaryArguments() {
        return Stream.of(
                Arguments.of(new Employee("John", 150, EmployeeType.FULL_TIME), 37, 5550.0),
                Arguments.of(new Employee("Doe",250, EmployeeType.PART_TIME), 38, 9500.0),
                Arguments.of(new Employee("Fizz", 350, EmployeeType.FULL_TIME), 39, 14150.0),
                Arguments.of(new Employee("Buzz", 450, EmployeeType.PART_TIME), 40, 18500.0)
        );
    }

    static Stream<Arguments> baseSalaryWithExtraArguments() {
        return Stream.of(
                Arguments.of(new Employee("John", 150, EmployeeType.FULL_TIME), 41, 6725.0),
                Arguments.of(new Employee("Doe",250, EmployeeType.PART_TIME), 42, 11000.0),
                Arguments.of(new Employee("Fizz", 350, EmployeeType.PART_TIME), 43, 15550.0),
                Arguments.of(new Employee("Buzz", 400, EmployeeType.FULL_TIME), 44, 18900.0)
        );
    }

    static Stream<Arguments> baseSalaryWithExtraPartTime() {
        return Stream.of(
                Arguments.of(new Employee("John", 150, EmployeeType.PART_TIME), 41, 6650.0),
                Arguments.of(new Employee("Doe",250, EmployeeType.PART_TIME), 42, 11000.0),
                Arguments.of(new Employee("Fizz", 350, EmployeeType.PART_TIME), 43, 15550.0),
                Arguments.of(new Employee("Buzz", 400, EmployeeType.PART_TIME), 44, 18100.0)
        );
    }

    static Stream<Arguments> salaryExceedLimitArguments() {
        return Stream.of(
                Arguments.of(new Employee("Doe",500, EmployeeType.PART_TIME, false ), 42),
                Arguments.of(new Employee("Buzz", 650, EmployeeType.FULL_TIME, false), 44)
        );
    }

    static Stream<Arguments> negativeHourlyRateArguments() {
        return Stream.of(
                Arguments.of(new Employee("John", -150, EmployeeType.FULL_TIME), 37),
                Arguments.of(new Employee("Doe", -250, EmployeeType.PART_TIME), 38)
        );
    }

    static Stream<Arguments> idempotentArguments() {
        return Stream.of(
                Arguments.of(new Employee("John", 150, EmployeeType.FULL_TIME), 37, 5550.0),
                Arguments.of(new Employee("Doe", 150, EmployeeType.FULL_TIME), 37, 5550.0),
                Arguments.of(new Employee("Fizz", 150, EmployeeType.FULL_TIME), 37, 5550.0)
        );
    }

    @ParameterizedTest(name = "{index} => Employee: {0}, Hours: {1}, Expected Salary: {2}")
    @MethodSource("baseSalaryArguments")
    @DisplayName("1. Calcular correctamente el sueldo base sin horas extras.")
    public void testCalculatePayrollBaseSalary(Employee employee, double hoursWorked, double expectedSalary) {
        double calculatedSalary = payrollService.calculatePayroll(employee, hoursWorked);

        assertEquals(expectedSalary, calculatedSalary,
                "El salario esperado era " + expectedSalary + " pero se calculó " + calculatedSalary);
    }

    @ParameterizedTest(name = "{index} => Employee: {0}, Hours: {1}, Expected Salary: {2}")
    @MethodSource("baseSalaryWithExtraArguments")
    @DisplayName("2. Calcular correctamente el sueldo con horas extras (solo FULL_TIME).")
    public void testCalculatePayrollWithExtraHours(Employee employee, double hoursWorked, double expectedSalary) {
        double calculatedSalary = payrollService.calculatePayroll(employee, hoursWorked);

        assertEquals(expectedSalary, calculatedSalary,
                "El salario esperado era " + expectedSalary + " pero se calculó " + calculatedSalary);
    }

    @ParameterizedTest(name = "{index} => Employee: {0}, Hours: {1}, Expected Salary: {2}")
    @MethodSource("baseSalaryWithExtraPartTime")
    @DisplayName("3. No aplicar horas extras a empleados PART_TIME.")
    public void testCalculatePayrollPartTimeNoExtraHours(Employee employee, double hoursWorked, double expectedSalary) {
        double calculatedSalary = payrollService.calculatePayroll(employee, hoursWorked);

        assertEquals(expectedSalary, calculatedSalary,
                "El salario esperado era " + expectedSalary + " pero se calculó " + calculatedSalary);
    }


    @Test
    @DisplayName("4. Validar si la horas trabajadas son negativas. Lanza excepción.")
    public void testCalculatePayrollNegativeHours() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    for (Employee employee : employees) {
                        payrollService.calculatePayroll(employee, -4);
                    }
                },
                "Las horas trabajadas no pueden ser negativas."
        );
    }

    @Test
    @DisplayName("5. Validar si un empleado es nulo. Lanza excepción.")
    public void testCalculatePayrollNullEmployee() {
        assertThrows(NullPointerException.class,
                () -> {
                    for (Employee employee : employees) {
                        if (employee != null) continue;
                        payrollService.calculatePayroll(employee, 0);
                    }
                },
                "El empleado no puede ser nulo."
        );
    }

    @ParameterizedTest(name = "{index} => Employee: {0}, Hours: {1}")
    @MethodSource("salaryExceedLimitArguments")
    @DisplayName("6. Validar que el salario no exceda el tope permitido sin alerta.")
    public void testCalculatePayrollSalaryExceedLimit(Employee employee, double hoursWorked) {
        assertThrows(IllegalStateException.class,
                () -> payrollService.calculatePayroll(employee, hoursWorked),
                "El salario de " + employee.getName() + " calculado excede el máximo permitido sin autorización: " + PayrollService.MAX_SALARY
        );
    }

    @ParameterizedTest(name = "{index} => Employee: {0}, Hours: {1}")
    @MethodSource("negativeHourlyRateArguments")
    @DisplayName("7. Validar que la tarifa por hora no sea negativa o nula.")
    public void testCalculatePayrollNegativeHourlyRate(Employee employee, double hoursWorked) {
        assertThrows(IllegalArgumentException.class,
                () -> payrollService.calculatePayroll(employee, hoursWorked),
                "La tarifa por hora debe ser mayor que cero."
        );
    }

    @ParameterizedTest(name = "{index} => Employee: {0}, Hours: {1}, Expected Salary: {2}")
    @MethodSource("idempotentArguments")
    @DisplayName("8. Validar que el cálculo de nómina sea idempotente.")
    public void testCalculatePayrollIdempotent(Employee employee, double hoursWorked, double expectedSalary) {
        double firstCalculation = payrollService.calculatePayroll(employee, hoursWorked);
        double secondCalculation = payrollService.calculatePayroll(employee, hoursWorked);

        assertEquals(expectedSalary, firstCalculation,
                "El primer cálculo de nómina no coincide con el esperado.");
        assertEquals(expectedSalary, secondCalculation,
                "El segundo cálculo de nómina no coincide con el esperado.");
        assertEquals(firstCalculation, secondCalculation,
                "Los cálculos de nómina deben ser idempotentes.");
    }
}
