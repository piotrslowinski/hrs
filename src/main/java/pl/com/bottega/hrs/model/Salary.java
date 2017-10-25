package pl.com.bottega.hrs.model;



import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by user on 15.10.2017.
 */
@Entity
@Table(name = "salaries")
public class Salary {



    @Embeddable
    public static class SalaryId implements Serializable{

        @Column(name = "emp_no", nullable = false, insertable = false, updatable = false)
        private Integer empNo ;


        @Column(name = "from_date")
        private LocalDate fromDate;

        public SalaryId() {
        }

        public SalaryId(Integer empNo, LocalDate fromDate){
            this.empNo = empNo;
            this.fromDate = fromDate;
        }

        @Override
        public String toString() {
            return "SalaryId{" +
                    "empNo=" + empNo +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SalaryId)) return false;

            SalaryId salaryId = (SalaryId) o;

            return empNo.equals(salaryId.empNo);
        }

        @Override
        public int hashCode() {
            return empNo.hashCode();
        }
    }

    @EmbeddedId
    private SalaryId salaryId;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "to_date")
    private LocalDate toDate;


    public Salary(SalaryId salaryId, Integer salary, LocalDate toDate){
        this.salaryId = salaryId;
        this.salary = salary;
        this.toDate = toDate;
    }

    public Salary(){
    }

    public Integer getSalary() {
        return salary;
    }

    public void setToDateSalary(LocalDate newDate){
        this.toDate = newDate;
    }



    @Override
    public String toString() {
        return "Salary{" +
                "salaryId=" + salaryId +
                ", salary=" + salary +
                ", toDate=" + toDate +
                '}';
    }


}
