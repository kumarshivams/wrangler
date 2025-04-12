package io.cdap.wrangler.executor;

import io.cdap.wrangler.api.Directive;
import io.cdap.wrangler.api.ExecutorContext;
import io.cdap.wrangler.api.Row;
import io.cdap.wrangler.parser.RecipeCompiler;

import java.util.List;

public class TestingRig {
    public static List<Row> execute(String[] recipe, List<Row> rows) {
        // Implementation that:
        // 1. Compiles the recipe
        // 2. Executes directives on test rows
        // 3. Returns results
    }
}