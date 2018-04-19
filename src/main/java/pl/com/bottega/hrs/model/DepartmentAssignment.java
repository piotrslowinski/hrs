package pl.com.bottega.hrs.model;

import org.springframework.beans.factory.annotation.Autowired;
import pl.com.bottega.hrs.infrastructure.StandardTimeProvider;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by user on 31.10.2017.
 */
@Entity
@Table(name = "dept_emp")
public class DepartmentAssignment {


    @Embeddable
    public static class DepartmentAssignmentId implements Serializable {

        @Column(name = "emp_no")
        private Integer empNo;

        @ManyToOne
        @JoinColumn(name = "dept_no")
        private Department department;

        public DepartmentAssignmentId() {
        }

        public DepartmentAssignmentId(Integer empNo, Department department) {
            this.empNo = empNo;
            this.department = department;
        }
    }

    @EmbeddedId
    private DepartmentAssignmentId id;

    @Transient
    @Autowired
    private TimeProvider timeProvider;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DepartmentAssignment() {
    }

    public DepartmentAssignment(Integer empNo, Department department, TimeProvider timeProvider) {
        this.id = new DepartmentAssignmentId(empNo, department);
        this.timeProvider = timeProvider;
        this.fromDate = timeProvider.today();
        this.toDate = timeProvider.MAX_DATE;
    }

    public Department getDepartment() {
        return id.department;
    }

    public boolean isAssigned(Department department) {
        return isCurrent() && department.equals(id.department);
    }

    public void unassign() {
        toDate = timeProvider.today();
    }


    public LocalDate getFromDate() {
        return fromDate;
    }


    public LocalDate getToDate() {
        return toDate;
    }

    public boolean isCurrent() {
        return toDate.isAfter(timeProvider.today());
    }
}

