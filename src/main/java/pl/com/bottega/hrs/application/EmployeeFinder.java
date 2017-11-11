package pl.com.bottega.hrs.application;

/**
 * Created by user on 02.11.2017.
 */
public interface EmployeeFinder {

    EmployeeSearchResults search(EmployeeSearchCriteria criteria);

    DetailedEmployeeDto getEmployeeDetails(Integer empNo);

    SalaryDto getSalaryInfo(Integer empNo);
}
