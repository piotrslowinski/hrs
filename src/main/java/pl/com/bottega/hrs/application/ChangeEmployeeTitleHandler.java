package pl.com.bottega.hrs.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.ChangeEmployeeProfileCommand;
import pl.com.bottega.hrs.model.commands.ChangeEmployeeTitleCommand;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

/**
 * Created by user on 07.11.2017.
 */
@Component
public class ChangeEmployeeTitleHandler implements Handler<ChangeEmployeeTitleCommand> {

    private EmployeeRepository repository;

    public ChangeEmployeeTitleHandler(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void handle(ChangeEmployeeTitleCommand cmd) {
        Employee employee = repository.get(cmd.getEmpNo());
        employee.changeTitle(cmd.getTitle());
        repository.save(employee);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return ChangeEmployeeProfileCommand.class;
    }
}
