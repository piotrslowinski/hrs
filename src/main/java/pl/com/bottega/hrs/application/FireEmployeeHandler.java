package pl.com.bottega.hrs.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.FireEmployeeCommand;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

/**
 * Created by user on 10.11.2017.
 */
@Component
public class FireEmployeeHandler implements Handler<FireEmployeeCommand> {


    EmployeeRepository employeeRepository;

    public FireEmployeeHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void handle(FireEmployeeCommand cmd) {
        Employee employee = employeeRepository.get(cmd.getEmpNo());
        employee.fire();
        employeeRepository.save(employee);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return FireEmployeeCommand.class;
    }
}
