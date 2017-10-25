package pl.com.bottega.hrs.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by user on 19.10.2017.
 */
@Entity
@Table(name = "titles")
public class Title {





    @Embeddable
    public static class TitleId implements Serializable {

        @Column(name = "emp_no",nullable = false, insertable = false, updatable = false)
        private Integer empNo;


        public TitleId() {
        }

        public TitleId(Integer empNo) {
            this.empNo = empNo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TitleId)) return false;

            TitleId titleId = (TitleId) o;

            return empNo.equals(titleId.empNo);
        }

        @Override
        public int hashCode() {
            return empNo.hashCode();
        }

        @Override
        public String toString() {
            return "TitleId{" +
                    "empNo=" + empNo +
                    '}';
        }
    }

    @EmbeddedId
    private TitleId titleId;

    @Column(name = "title")
    private String title;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public Title(TitleId titleId, String title, LocalDate fromDate, LocalDate toDate){
        this.titleId = titleId;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public Title() {
    }

    public void setToDateTitle(LocalDate newDate) {
        this.toDate = newDate;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Title{" +
                "titleId=" + titleId +
                ", title='" + title + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
