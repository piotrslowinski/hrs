package pl.com.bottega.hrs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public Department(){}

    public Department(String deptNo, String deptName){
        this.deptNo = deptNo;
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }
}
