package pl.com.bottega.hrs.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Created by user on 30.10.2017.
 */

public interface TimeProvider {

    LocalDate MAX_DATE = LocalDate.parse("9999-01-01");

    Clock clock();

    default LocalDate today() {
        return LocalDate.now(clock());
    }
}
