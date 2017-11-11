package pl.com.bottega.hrs.model.repositories;

import pl.com.bottega.hrs.model.Employee;

/**
 * Created by user on 07.11.2017.
 */
public interface EmployeeRepository {

    Integer generateNumber();

    void save(Employee employee);

    Employee get(Integer empNo);
}
