package pl.com.bottega.hrs.model.events;

import java.time.LocalDateTime;

/**
 * Created by user on 12.12.2017.
 */
public class SalaryChangedEvent {

    private Integer empNo;
    private Integer newSalary;
    private LocalDateTime dateTime = LocalDateTime.now();

    public SalaryChangedEvent(Integer empNo, Integer newSalary) {
        this.empNo = empNo;
        this.newSalary = newSalary;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public Integer getNewSalary() {
        return newSalary;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
