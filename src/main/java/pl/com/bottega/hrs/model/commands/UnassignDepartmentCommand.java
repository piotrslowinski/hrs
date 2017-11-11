package pl.com.bottega.hrs.model.commands;

/**
 * Created by user on 07.11.2017.
 */
public class UnassignDepartmentCommand {

    private Integer empNo;

    private String deptNo;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }
}
