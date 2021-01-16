package pl.edu.wit.calculators.salary;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CalculationCommand {

    @ShellMethod("Calculate month cost for gross salary")
    public String calculate() {
        return "Test";
    }
}
