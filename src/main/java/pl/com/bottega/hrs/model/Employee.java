package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Created by user on 14.10.2017.
 */
@Entity
@Table(name = "employees")
public class Employee {

    private final LocalDate TO_DATE_MAX = LocalDate.of(9999,1,1);

    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no")
    private Collection<DepartmentAssignment> departmentAssignments = new LinkedList<>();
    private String deptNo;

    public Employee(){}

    public Employee (Integer empNo, String firstName, String lastName, LocalDate birthDate, Address address){
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = LocalDate.now();
        this.address = address;

    }

    public Employee (Integer empNo, String firstName, String lastName, LocalDate birthDate, Address address, Salary salaries){
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = LocalDate.now();
        this.address = address;
        this.salaries = getSalaries();
    }

    public Employee(Integer empNo, String firstName, String lastName, LocalDate birthDate, Address address, Title titles) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = LocalDate.now();
        this.address = address;
        this.titles = getTitles();
    }

    public Employee(Integer empNo, String firstName, String lastName, LocalDate birthDate, Address address, Salary salary, Title titles) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = LocalDate.now();
        this.address = address;
        this.salaries = getSalaries();
        this.titles = getTitles();
    }

    public Employee(Integer empNo, String firstName, String lastName, LocalDate birthDate, Address address, DepartmentAssignment departmentAssignments) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hireDate = LocalDate.now();
        this.address = address;
       this.departmentAssignments = getDepartmentAssignments();
    }


    public void changeTitle(String title){
        LinkedList<Title> employeeTitles = new LinkedList<>(titles);
        if(employeeTitles.isEmpty())
            addNewTitle(title);
        else {
            employeeTitles.getLast().setToDate(LocalDate.now());
            addNewTitle(title);
        }

    }

    private void addNewTitle(String title) {
        titles.add(new Title(getEmpNo(),title, LocalDate.now(), TO_DATE_MAX));
    }


    public void updateProfile(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public void changeSalary(Integer salary){
        LinkedList<Salary> employeeSalary = new LinkedList<>(salaries);
        if(employeeSalary.isEmpty())
            addNewSalary(salary);
        else {
            employeeSalary.getLast().setToDateSalary(LocalDate.now());
            addNewSalary(salary);
        }

    }

    public void changeDepartment(Department department){
        LinkedList<DepartmentAssignment> departments = new LinkedList<>(departmentAssignments);
        if (departments.isEmpty())
            addNewDepartment(department);
        else {
            departments.getLast().setToDateDepartment(LocalDate.now());
            addNewDepartment(department);
        }
    }

    private void addNewDepartment(Department department) {

        departmentAssignments.add(new DepartmentAssignment(getEmpNo(),department.getDeptNo(), LocalDate.now(), TO_DATE_MAX));
    }

    private void addNewSalary(Integer salary) {

        salaries.add(new Salary(new Salary.SalaryId(getEmpNo(),LocalDate.now()),salary,TO_DATE_MAX));
    }

    public Optional<String> getCurrentTitle(){
        for (Title title : titles) {
            if (title.getToDate() == TO_DATE_MAX)
                return Optional.of(title.getTitle());
        }
        return Optional.empty();
    }


    public Optional<Integer> getCurrentSalary(){
        LinkedList<Salary> employeeSalary = new LinkedList<>(salaries);
        Integer currentSalary = employeeSalary.getLast().getSalary();
        return Optional.ofNullable(currentSalary);
    }

    public Department getCurrentDepartment(){
        LinkedList<DepartmentAssignment> departments = new LinkedList<>(departmentAssignments);
        Department currentDepartment = departments.getLast().getDept();
        return currentDepartment;
    }

    public Optional<String> getCurrentDeptName(){
        LinkedList<DepartmentAssignment> departments = new LinkedList<>(departmentAssignments);
        String currentDepartment = departments.getLast().getDeptName();
        return Optional.ofNullable(currentDepartment);
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

    public void addSalary(Salary salary) {
        salaries.add(salary);

    }

    public void addTitle(Title title) {
        titles.add(title);
    }


    public Collection<Title> getTitles() {
        return titles;
    }


    public Collection<DepartmentAssignment> getDepartmentAssignments() {
        return departmentAssignments;
    }

    public void addDepartmentAssignment(DepartmentAssignment deepAss, Department department) {
        departmentAssignments.add(deepAss);
    }

    public void setDepartmentAssignments(Collection<DepartmentAssignment> departmentAssignments) {
        this.departmentAssignments = departmentAssignments;
    }
}
