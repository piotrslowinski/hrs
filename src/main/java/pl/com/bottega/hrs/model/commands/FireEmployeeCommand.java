package pl.com.bottega.hrs.model.commands;

/**
 * Created by user on 10.11.2017.
 */
public class FireEmployeeCommand implements Command {

    private Integer empNo;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public void validate(ValidationErrors errors) {
        validatePresence(errors, "empNo", empNo);
    }
}
