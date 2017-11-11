package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.*;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Gender;
import pl.com.bottega.hrs.model.commands.AddDepartmentCommand;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;
import pl.com.bottega.hrs.model.commands.FireEmployeeCommand;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 10.11.2017.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FireEmployeeTest {

    @Autowired
    private AddEmployeeHandler addEmployeeHandler;

    @Autowired
    private EmployeeFinder employeeFinder;

    @Autowired
    private AddDepartmentHandler addDepartmentHandler;

    @Autowired
    private FireEmployeeHandler fireEmployeeHandler;

    @Test
    public void shouldFireCreatedEmployee(){
        // given
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

        //when
        FireEmployeeCommand fireEmployeeCommand = new FireEmployeeCommand();
        fireEmployeeCommand.getEmpNo();
        fireEmployeeHandler.handle(fireEmployeeCommand);

        //then
        DetailedEmployeeDto employeeDto = employeeFinder.getEmployeeDetails(1);
        SalaryDto salaryDto = employeeFinder.getSalaryInfo(1);
        assertEquals(LocalDate.now(), salaryDto.getToDate());

    }
}
