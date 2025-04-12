package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.parser.ByteSize;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ByteSizeTest {
    @Test
    public void testByteSizeParsing() {
        // Test basic conversions
        assertEquals(1024L, new ByteSize("1KB").getBytes());
        assertEquals(1024 * 1024L, new ByteSize("1MB").getBytes());
        assertEquals(1.5 * 1024 * 1024, new ByteSize("1.5MB").getBytes(), 0.001);

        // Test case insensitivity
        assertEquals(1024L, new ByteSize("1kb").getBytes());
        assertEquals(1024L, new ByteSize("1Kb").getBytes());

        // Test without 'B' suffix
        assertEquals(1024L, new ByteSize("1K").getBytes());
    }

    @Test
    public void testInvalidByteSize() {
        // Test invalid formats
        assertThrows("Should reject missing unit",
                IllegalArgumentException.class,
                () -> new ByteSize("1024")
        );

        assertThrows("Should reject unknown unit",
                IllegalArgumentException.class,
                () -> new ByteSize("1XB")
        );

        assertThrows("Should reject negative values",
                IllegalArgumentException.class,
                () -> new ByteSize("-1MB")
        );
    }
}