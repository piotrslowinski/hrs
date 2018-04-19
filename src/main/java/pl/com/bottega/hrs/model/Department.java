package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.stream.Collector;

/**
 * Created by user on 14.10.2017.
 */
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @Column(name = "dept_no", columnDefinition = "char(4)")
    private String deptNo;

    @Column(name = "dept_name")
    private String deptName;


    public Department() {
    }

    public Department(String deptNo, String deptName) {
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        return getDeptNo().equals(that.getDeptNo());
    }

    @Override
    public int hashCode() {
        return getDeptNo().hashCode();
    }

    public String getDeptNo() {
        return deptNo;
    }

    public String getDeptName() {
        return deptName;
    }


    public String getNumber() {
        return deptNo;
    }
}
