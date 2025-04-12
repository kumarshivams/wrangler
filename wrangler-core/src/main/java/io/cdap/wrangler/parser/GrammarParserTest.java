package io.cdap.wrangler.parser;

import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.RecipeParser;
import io.cdap.wrangler.api.parser.ByteSize;
import io.cdap.wrangler.registry.CompositeDirectiveRegistry;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class GrammarParserTest {
    private RecipeParser parser;

    @Before
    public void setUp() {
        CompositeDirectiveRegistry registry = new CompositeDirectiveRegistry();
        // Register test directives if needed
        parser = new GrammarBasedParser("test", registry);
    }

    @Test
    public void testByteSizeInRecipe() throws Exception {
        String recipe = "set-column :total_size 10MB";
        List<Directive> directives = parser.parse(recipe);

        // Verify the directive was parsed
        assertTrue(directives.size() > 0);

        // If testing aggregate-stats specifically:
        // assertTrue(directives.get(0) instanceof AggregateStatsDirective);
    }

    @Test
    public void testInvalidByteSizeSyntax() {
        String recipe = "set-column :total_size 10XB"; // Invalid unit
        assertThrows("Should reject invalid byte size syntax",
                RecipeException.class,
                () -> parser.parse(recipe)
        );
    }
}