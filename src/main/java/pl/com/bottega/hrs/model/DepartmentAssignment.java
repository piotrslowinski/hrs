package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by user on 25.10.2017.
 */
@Entity
@Table(name = "dept_no")
public class DepartmentAssignment {





    @Embeddable
    public static class DepartmentAssignmentId implements Serializable{

        @Column(name = "emp_no", nullable = false, insertable = false, updatable = false)
        private Integer empNo ;

        @Column(name = "dept_no", columnDefinition = "char(4)", insertable = false, updatable = false)
        private String deptNo;

        @OneToOne
        @PrimaryKeyJoinColumn(name = "dept_no", referencedColumnName = "dept_no")
        Department department;

        public DepartmentAssignmentId(Integer empNo, String deptNo) {
            this.empNo = empNo;
            this.deptNo = deptNo;
        }

        public DepartmentAssignmentId() {
        }
    }

    @EmbeddedId
    private DepartmentAssignmentId departmentAssignmentId;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;




    public DepartmentAssignment(Integer empNo, String deptNo, LocalDate fromDate, LocalDate toDate) {
        this.departmentAssignmentId = new DepartmentAssignmentId(empNo, deptNo);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DepartmentAssignment() {
    }

    public void setToDateDepartment(LocalDate newDate) {
        this.toDate = newDate;
    }

    public DepartmentAssignmentId getDepartmentAssignmentId() {
        return departmentAssignmentId;
    }
    public Department getDept(){
        return departmentAssignmentId.department;
    }

    public String getDeptName(){
        return getDept().getDeptName();
    }


    public void setDepartmentAssignmentId(DepartmentAssignmentId departmentAssignmentId) {
        this.departmentAssignmentId = departmentAssignmentId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getEmpNo(){
        return departmentAssignmentId.empNo;
    }

    public String getDeptNo(){
        return departmentAssignmentId.deptNo;
    }



}
