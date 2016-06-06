package amazonwebservices.cloudwatch;

import java.util.Map;


public interface CloudwatchMetricService
{
    void pushMetric( String metricName, double value );

    void pushMetricAsync( String metricName, double value );

    void pushMetricWithDimensions( String metricName, Map<String, String> dimensionNames, double value );

    void pushMetricWithDimensionsAsync( String metricName, Map<String, String> dimensionNames, double value );
}
