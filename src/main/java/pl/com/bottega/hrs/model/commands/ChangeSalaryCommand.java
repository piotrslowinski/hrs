package pl.com.bottega.hrs.model.commands;

/**
 * Created by user on 07.11.2017.
 */
public class ChangeSalaryCommand implements Command {

    private Integer empNo;
    private Integer amount;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void validate(ValidationErrors errors) {
        validatePresence(errors, "empNo", empNo);
        validatePresence(errors, "amount", amount);
    }
}
