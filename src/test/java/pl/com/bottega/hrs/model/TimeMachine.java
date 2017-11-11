package pl.com.bottega.hrs.model;

import java.sql.Time;
import java.time.*;

/**
 * Created by user on 30.10.2017.
 */
public class TimeMachine implements TimeProvider {

    private Clock currentClock = Clock.systemDefaultZone();

    @Override
    public Clock clock() {
        return currentClock;
    }

    public void travel(Duration duration){
        currentClock = Clock.offset(currentClock, duration);
    }

    public void travel(LocalDate destination) {
        currentClock = Clock.fixed(LocalDateTime.of(destination, LocalTime.now()).toInstant(ZoneOffset.UTC), ZoneId.systemDefault());
    }

    public void reset() {
        currentClock = Clock.systemDefaultZone();
    }
}
