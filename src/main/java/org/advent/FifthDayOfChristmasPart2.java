package org.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FifthDayOfChristmasPart2 {
    static class Range {
        public long min;
        public long max;
        public Range(long min, long max) {
            this.min = min;
            this.max = max;
        }
    }

    public static void main(String[] args) throws Exception {
        Instant startTime = Instant.now();

        Path p = Paths.get(
                FifthDayOfChristmasPart2.class.getClassLoader().getResource("FifthDayOfChristmasSource.txt").toURI()
        );
        List<String> foodData = Files.readAllLines(p)
                .stream().toList();

        List<String> rawRanges = new ArrayList<>();
        List<Range> ranges = new ArrayList<>();

        for (String foodDatum: foodData) {
            if (foodDatum.isEmpty()) {
                break;
            }

            rawRanges.add(foodDatum);
        }

        rawRanges.sort(Comparator.comparingLong((String s) -> {
            int dash = s.indexOf('-');
            return Long.parseLong(s.substring(0, dash));
        }));

        for (String rawRange: rawRanges) {
            System.out.println(rawRange);

            String[] parsedRange = rawRange.split("-");
            long min = Long.parseLong(parsedRange[0]);
            long max = Long.parseLong(parsedRange[1]);

            // Min is always in order currently, meaning min is always larger than ranges[last].min.
            boolean includedInRange = false;
            for(Range range: ranges) {
                if (range.max >= min && max >= range.max) {
                    range.max = max;
                    includedInRange = true;
                } else if (range.max > max) {
                    includedInRange = true;
                }
            }
            if (!includedInRange) {
                ranges.add(new Range(min, max));
            }
        }

        long count = 0;
        for(Range range: ranges) {
            count += range.max-range.min+1;
            System.out.println("Range min:" + range.min + " max:" + range.max);
        }
        System.out.println("FreshIDCount:" + count);
        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }
}