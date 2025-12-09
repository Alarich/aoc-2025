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

        List<Range> ranges = parseRanges(foodData);
        ranges.sort(Comparator.comparingLong(r -> r.min));
        List<Range> mergedRanges = mergeRanges(ranges);

        System.out.println("FreshIDCount:" + getCount(mergedRanges));
        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }

    private static long getCount(List<Range> mergedRanges) {
        return mergedRanges
                .stream()
                .mapToLong(r -> r.max - r.min + 1)
                .sum();
    }

    static List<Range> parseRanges(List<String> foodData) {
        List<Range> ranges = new ArrayList<>();
        for (String foodDatum: foodData) {
            if (foodDatum.isEmpty()) {
                break;
            }

            String[] range = foodDatum.split("-");
            ranges.add(new Range(Long.parseLong(range[0]), Long.parseLong(range[1])));
        }
        return ranges;
    }

    static List<Range> mergeRanges(List<Range> ranges) {
        List<Range> mergedRanges = new ArrayList<>();
        for (Range range : ranges) {
            if (mergedRanges.isEmpty()) {
                mergedRanges.add(range);
                continue;
            }

            Range last = mergedRanges.get(mergedRanges.size() - 1);

            if (last.max < range.min) {
                mergedRanges.add(range);
            } else {
                last.max = Math.max(last.max, range.max);
            }
        }
        return mergedRanges;
    }
}