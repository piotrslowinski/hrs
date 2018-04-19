package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Gender;
import pl.com.bottega.hrs.model.TimeMachine;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;
import pl.com.bottega.hrs.model.commands.ChangeSalaryCommand;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChangeSalaryTest extends AcceptanceTest {

    @Autowired
    private ChangeSalaryHandler changeSalaryHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    private final TimeMachine timeMachine = new TimeMachine();

    @Test
    public void shouldChangeEmployeeSalary() {
        // given
        timeMachine.travel(Duration.ofDays(-365));
        LocalDate t0 = timeMachine.today();
        AddDepartmentCommand addDepartmentCommand = new AddDepartmentCommand();
        addDepartmentCommand.setName("Marketing");
        addDepartmentCommand.setNumber("d1");
        addDepartmentHandler.handle(addDepartmentCommand);
        AddEmployeeCommand addEmployeeCommand = new AddEmployeeCommand();
        addEmployeeCommand.setFirstName("Janek");
        addEmployeeCommand.setLastName("Nowak");
        addEmployeeCommand.setAddress(new Address("test", "test"));
        addEmployeeCommand.setBirthDate(LocalDate.parse("1990-01-01"));
        addEmployeeCommand.setDeptNo("d1");
        addEmployeeCommand.setGender(Gender.M);
        addEmployeeCommand.setSalary(50000);
        addEmployeeCommand.setTitle("Junior Developer");
        addEmployeeHandler.handle(addEmployeeCommand);

        // when
        timeMachine.reset();
        LocalDate t1 = timeMachine.today();
        ChangeSalaryCommand changeSalaryCommand = new ChangeSalaryCommand();
        changeSalaryCommand.setEmpNo(1);
        changeSalaryCommand.setAmount(1000);
        changeSalaryHandler.handle(changeSalaryCommand);

        // then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        assertEquals(Integer.valueOf(1000), employeeDto.getSalary().get());
    }
}