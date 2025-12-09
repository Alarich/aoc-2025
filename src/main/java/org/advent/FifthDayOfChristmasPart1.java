package org.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FifthDayOfChristmasPart1 {
    record Range(long min, long max) {}

    public static void main(String[] args) throws Exception {
        Instant startTime = Instant.now();

        Path p = Paths.get(
                FifthDayOfChristmasPart1.class.getClassLoader().getResource("FifthDayOfChristmasSource.txt").toURI()
        );
        List<String> foodData = Files.readAllLines(p)
                .stream().toList();

        List<Range> ranges = new ArrayList<>();
        List<Long> ids = new ArrayList<>();

        boolean breakFound = false;
        for (String foodDatum: foodData) {
            if (foodDatum.isEmpty()) {
                breakFound = true;
                continue;
            }

            if (breakFound) {
                ids.add(Long.parseLong(foodDatum));
            } else {
                String[] range = foodDatum.split("-");
                ranges.add(new Range(Long.parseLong(range[0]), Long.parseLong(range[1])));
            }
        }

        ranges.sort(Comparator.comparingLong(Range::min));
        int counter = 0;
        for (long id: ids) {
            for (Range range: ranges) {
                if (range.min > id) break;
                if (id <= range.max) {
                    counter++;
                    break;
                }
            }
        }

        System.out.println("Fresh foods:" + counter);
        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }
}