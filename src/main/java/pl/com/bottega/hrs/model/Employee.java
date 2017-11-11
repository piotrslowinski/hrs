package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.nio.channels.AsynchronousFileChannel;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by user on 14.10.2017.
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Transient
    private final LocalDate TO_DATE_MAX = LocalDate.of(9999,1,1);

    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Transient
    private TimeProvider timeProvider;

    @Column(name = "hire_date")
    private LocalDate hireDate;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('M','F')")
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Collection<Salary> salaries = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Collection<Title> titles = new LinkedList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "emp_no")
    private Collection<DepartmentAssignment> departmentAssignments = new LinkedList<>();



    public Employee(){}

    public Employee (Integer empNo, String firstName, String lastName, LocalDate birthDate, Address address, TimeProvider timeProvider){
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.timeProvider = timeProvider;
        this.hireDate = timeProvider.today();
        this.address = address;

    }


    public void updateProfile(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public void updateProfile(String firstName, String lastName, LocalDate birthDate, Address address, Gender gender) {
        updateProfile(firstName, lastName, birthDate);
        this.address = address;
        this.gender = gender;
    }

    public void changeSalary(Integer newSalary) {
        getCurrentSalary().ifPresent((currentSalary) -> {
            removeOrTerminateSalary(currentSalary);
        });
        addNewSalary(newSalary);

    }

    private void addNewSalary(Integer newSalary) {
        salaries.add(new Salary(empNo, newSalary, timeProvider));
    }

    private void removeOrTerminateSalary(Salary currentSalary) {
        if(currentSalary.startsToday()){
            salaries.remove(currentSalary);
        } else {
            currentSalary.terminate();
        }
    }


    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public Collection<Salary> getSalaries() {
        return salaries;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empNo=" + empNo +
                ", birthDate=" + birthDate +
                ", hireDate=" + hireDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", address=" + address +
                ", salaries=" + salaries +
                '}';
    }




    public Optional<Salary> getCurrentSalary() {
        return salaries.stream().filter(Salary::isCurrent).findFirst();
    }

    public void changeTitle(String titleName) {
        getCurrentTitle().ifPresent((t) -> {
            if (t.startsToday())
                titles.remove(t);
            else
                t.terminate();
        });
        titles.add(new Title(empNo, titleName, timeProvider));
    }

    public Optional<Title> getCurrentTitle() {
        return titles.stream().filter(Title::isCurrent).findFirst();
    }

    public Collection<Title> getTitleHistory() {
        return titles;
    }

    public Collection<Department> getCurrentDepartments() {
        return departmentAssignments.stream().filter(DepartmentAssignment::isCurrent).
                map(DepartmentAssignment::getDepartment).
                collect(Collectors.toList());
    }

    public void assignDepartment(Department department) {
        if (!isCurrentlyAssignedTo(department))
            departmentAssignments.add(new DepartmentAssignment(empNo, department, timeProvider));
    }

    private boolean isCurrentlyAssignedTo(Department department) {
        return getCurrentDepartments().contains(department);
    }

    public void unassignDepartment(Department department) {
        departmentAssignments.stream().filter((assignment) -> assignment.isAssigned(department))
                .findFirst()
                .ifPresent(DepartmentAssignment::unassign);
    }

    public void fire(Employee employee){
//        employee.getDepartmentsHistory().stream().filter((department) -> department.isCurrent())
//                .findAny().ifPresent(DepartmentAssignment::unassign);

        Collection<Department> current = employee.getCurrentDepartments();
        for (Department dept: current){
            unassignDepartment(dept);
        }
        Salary salary = employee.getCurrentSalary().get();
        removeOrTerminateSalary(salary);
        Title title = employee.getCurrentTitle().get();
        title.terminate();


    }

    public Collection<DepartmentAssignment> getDepartmentsHistory() {
        return departmentAssignments;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Gender getGender() {
        return gender;
    }


}
