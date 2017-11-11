package pl.com.bottega.hrs.model.repositories;

import pl.com.bottega.hrs.model.Department;

/**
 * Created by user on 07.11.2017.
 */
public interface DepartmentRepository {

    Department get(String number);

    void save(Department department);
}
