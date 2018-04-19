package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.ChangeSalaryCommand;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

/**
 * Created by user on 07.11.2017.
 */
@Component
public class ChangeSalaryHandler implements Handler<ChangeSalaryCommand> {

    private EmployeeRepository repository;

    public ChangeSalaryHandler(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void handle(ChangeSalaryCommand cmd) {
        Employee employee = repository.get(cmd.getEmpNo());
        employee.changeSalary(cmd.getAmount());
        repository.save(employee);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return ChangeSalaryCommand.class;
    }
}
