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

        @Column(name = "title")
        private String title;

        @Column(name = "from_date")
        private LocalDate fromDate;

        public TitleId() {
        }

        public TitleId(Integer empNo, String title, LocalDate fromDate) {
            this.empNo = empNo;
            this.title = title;
            this.fromDate = fromDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TitleId)) return false;

            TitleId titleId = (TitleId) o;

            if (!empNo.equals(titleId.empNo)) return false;
            if (!title.equals(titleId.title)) return false;
            return fromDate.equals(titleId.fromDate);
        }

        @Override
        public int hashCode() {
            int result = empNo.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + fromDate.hashCode();
            return result;
        }
    }

    @EmbeddedId
    private TitleId titleId;

    @Column(name = "to_date")
    private LocalDate toDate;

    public Title() {
    }


    public Title(Integer empNo, String title, LocalDate fromDate, LocalDate toDate){
        this.titleId = new TitleId(empNo, title, fromDate);
        this.toDate = toDate;
    }



    public String getTitle() {
        return titleId.title;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public TitleId getTitleId() {
        return titleId;
    }

    public void setTitleId(TitleId titleId) {
        this.titleId = titleId;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

}

