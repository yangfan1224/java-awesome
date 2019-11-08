package github.com.yangfan1224.awesome.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

public class MetricReport {
    static final MetricRegistry metrics = new MetricRegistry();
    static Timer responses = metrics.timer(name(MetricReport.class, "responses"));
    public static void main(String [] args) {
        startReport();
        Meter requests = metrics.meter("requests");
        requests.mark();
        workJob();
        wait５Seconds();
    }

    static void workJob() {
        final Timer.Context context = responses.time();
        try {
            int sum = 0;
            for (int i = 0; i < 10000; i++) {
                sum += i;
            }
        } finally {
            context.stop();
        }
    }
    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    static void wait５Seconds() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {

        }
    }
}
