package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.TimeDuration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TimeDurationTest {
    @Test
    public void testTimeDurationParsing() {
        // Test basic conversions
        assertEquals(1_000_000L, new TimeDuration("1ms").getNanoseconds());
        assertEquals(1_000_000_000L, new TimeDuration("1s").getNanoseconds());
        assertEquals(1.5 * 1_000_000_000L, new TimeDuration("1.5s").getNanoseconds(), 0.001);

        // Test all supported units
        assertEquals(100L, new TimeDuration("100ns").getNanoseconds());
        assertEquals(2_000L, new TimeDuration("2us").getNanoseconds());
        assertEquals(30 * 60 * 1_000_000_000L, new TimeDuration("30m").getNanoseconds());
    }

    @Test
    public void testInvalidTimeDuration() {
        assertThrows("Should reject missing unit",
                IllegalArgumentException.class,
                () -> new TimeDuration("100")
        );

        assertThrows("Should reject unknown unit",
                IllegalArgumentException.class,
                () -> new TimeDuration("1ys")
        );
    }
}