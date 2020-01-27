import edu.princeton.cs.algs4.Stopwatch;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StopwatchManager {

    private Map<String, Stopwatch> stopwatches = new HashMap<>();
    private Map<String, Double> measuredTimes = new LinkedHashMap<>();

    public void start(String name) {
        stopwatches.put(name, new Stopwatch());
    }

    public void stop(String name) {
        if (!stopwatches.containsKey(name)) throw new IllegalArgumentException("No stopwatch associated with " + name);
        double time = stopwatches.remove(name).elapsedTime();
        measuredTimes.merge(name, time, Double::sum);
    }

    public void printStats() {
        measuredTimes.forEach((name, time) -> System.out.printf("Time for %s: %f\r\n", name, time));
    }

}

