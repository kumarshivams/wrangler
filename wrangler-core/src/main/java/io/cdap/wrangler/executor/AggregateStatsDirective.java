public class AggregateStatsDirective implements Directive {
    private String sizeColumn;
    private String timeColumn;
    private String sizeOutput;
    private String timeOutput;

    @Override
    public void initialize(Arguments args) {
        // Parse arguments
    }

    @Override
    public List<Row> execute(List<Row> rows, ExecutorContext context) {
        // Accumulate values in context.getStore()
        return rows;
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}