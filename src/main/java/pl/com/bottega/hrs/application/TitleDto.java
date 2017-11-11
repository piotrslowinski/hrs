package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.model.Title;

import java.time.LocalDate;

/**
 * Created by user on 07.11.2017.
 */
public class TitleDto {

    private LocalDate fromDate, toDate;

    private String title;

    public TitleDto(Title title) {
        this.fromDate = title.getFromDate();
        this.toDate = title.getToDate();
        this.title = title.getName();
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
