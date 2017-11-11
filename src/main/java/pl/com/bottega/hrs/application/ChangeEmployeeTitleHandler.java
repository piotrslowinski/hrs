package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.commands.ChangeEmployeeTitleCommand;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

/**
 * Created by user on 07.11.2017.
 */
@Component
public class ChangeEmployeeTitleHandler {

    private EmployeeRepository repository;

    public ChangeEmployeeTitleHandler(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void handle(ChangeEmployeeTitleCommand cmd){
        Employee employee = repository.get(cmd.getEmpNo());
        employee.changeTitle(cmd.getTitle());
        repository.save(employee);
    }
}
