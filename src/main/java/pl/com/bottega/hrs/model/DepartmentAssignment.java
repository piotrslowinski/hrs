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
    public class DepartmentId implements Serializable{

        @Column(name = "emp_no", nullable = false, insertable = false, updatable = false)
        private Integer empNo ;

        @Column(name = "dept_no")
        private String deptNo;

        public DepartmentId(Integer empNo, String deptNo) {
            this.empNo = empNo;
            this.deptNo = deptNo;
        }

        public DepartmentId() {
        }
    }

    @Embedded
    private DepartmentId departmentId;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @OneToOne
    @JoinColumn(name = "dept_no")
    private Department department;

    public DepartmentAssignment(DepartmentId departmentId, LocalDate fromDate, LocalDate toDate, Department department) {
        this.departmentId = departmentId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.department = department;
    }

    public DepartmentAssignment() {
    }




}
