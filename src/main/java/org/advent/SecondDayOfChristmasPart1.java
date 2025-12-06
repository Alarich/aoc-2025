package org.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class SecondDayOfChristmasPart1 {
    public static void main(String[] args) throws Exception {
        Instant startTime = Instant.now();
        long sum = 0;

        Path p = Paths.get(
                SecondDayOfChristmasPart1.class.getClassLoader().getResource("SecondDayOfChristmasSource.txt").toURI()
        );

        String[] ranges = Files.readString(p).split(",");
        for(String range : ranges) {
            System.out.println("range: " + range);
            int dash = range.indexOf('-');
            long start = Long.parseLong(range.substring(0, dash));
            long end   = Long.parseLong(range.substring(dash + 1));
            LongStream stream = LongStream.rangeClosed(start, end);
            sum += stream.filter(SecondDayOfChristmasPart1::hasPattern).sum();
        }

        System.out.println("Sum: " + sum);

        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }

    private static boolean hasPattern(long input) {
        String pattern = Long.toString(input);
        if (pattern.length() % 2 != 0) {
            return false;
        }
        int maxPatternLength = pattern.length()/2;
        String subPattern = pattern.substring(0, maxPatternLength);
        if (pattern.equals(subPattern + subPattern)) {
            System.out.println("pattern: " + pattern);
            System.out.println("subPattern: " + subPattern);
            return true;
        }
        return false;
    }
}