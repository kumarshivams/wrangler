package io.cdap.wrangler.executor;

import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.executor.TestingRig;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AggregateStatsDirectiveTest {
    @Test
    public void testAggregateStats() throws Exception {
        List<Row> rows = Arrays.asList(
                Row.with("size_col", "1KB").add("time_col", "100ms").build(),
                Row.with("size_col", "2MB").add("time_col", "500ms").build()
        );

        String[] recipe = {
                "aggregate-stats :size_col :time_col total_size total_time"
        };

        List<Row> results = TestingRig.execute(recipe, rows);

        // Verify single output row
        assertEquals(1, results.size());

        // Verify calculations (all values converted to bytes/ns first)
        assertEquals(2 * 1024 * 1024L + 1024L, results.get(0).getValue("total_size"));
        assertEquals(600_000_000L, results.get(0).getValue("total_time"));
    }

    @Test
    public void testUnitConversion() throws Exception {
        List<Row> rows = Arrays.asList(
                Row.with("size", "1GB").add("time", "1h").build()
        );

        String[] recipe = {
                "aggregate-stats :size :time size_mb time_min MB min"
        };

        List<Row> results = TestingRig.execute(recipe, rows);

        // Verify converted units
        assertEquals(1024.0, results.get(0).getValue("size_mb")); // 1GB → 1024MB
        assertEquals(60.0, results.get(0).getValue("time_min"));  // 1h → 60min
    }
}