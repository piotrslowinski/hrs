package pl.com.bottega.hrs.infrastructure;

import pl.com.bottega.hrs.model.TimeProvider;

import java.sql.Time;
import java.time.Clock;

/**
 * Created by user on 30.10.2017.
 */
public class StandardTimeProvider implements TimeProvider {

    @Override
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
