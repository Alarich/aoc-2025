package org.advent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.LongStream;

public class SecondDayOfChristmasPart2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Instant startTime = Instant.now();
        long sum = 0;

        Path p = Paths.get(
                SecondDayOfChristmasPart2.class.getClassLoader().getResource("SecondDayOfChristmasSource.txt").toURI()
        );

        String[] ranges = Files.readString(p).split(",");
        for(String range : ranges) {
            int dash = range.indexOf('-');
            long start = Long.parseLong(range.substring(0, dash));
            long end   = Long.parseLong(range.substring(dash + 1));
            LongStream stream = LongStream.rangeClosed(start, end);
            sum += stream.filter(SecondDayOfChristmasPart2::hasPattern).sum();
        }

        System.out.println("Sum: " + sum);

        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }

    /**
     * This surprisingly works. Don't ask me why I thought splitting by subPattern would work.
     * The reason this still works is that string.split(substring) without a limit returns an empty array
     * when all string matches are replaced.
     * And allMatch returns true for any predicate for an empty stream.
     * If the pattern doesn't fully replace all values, the stream is not empty and match fails.
     * This also takes 10x more time to compute.
     */
    private static boolean hasPatternWrong(long input) {
        String pattern = Long.toString(input);
        int maxPatternLength = pattern.length()/2;
        for (int patternLength = 1; patternLength <= maxPatternLength; patternLength++) {
            String subPattern = pattern.substring(0, patternLength);
            if (Arrays.stream(pattern.split(subPattern)).allMatch(chunk -> false)) {

                return true;
            }
        }
        return false;
    }

    private static boolean hasPattern(long input) {
        String pattern = Long.toString(input);
        int maxPatternLength = pattern.length() / 2;
        for (int patternLength = 1; patternLength <= maxPatternLength; patternLength++) {
            String subPattern = pattern.substring(0, patternLength);
            if (pattern.length() % patternLength == 0) {
                int repeatCount = pattern.length() / patternLength;
                String repeatedString = subPattern.repeat(repeatCount);
                if (pattern.equals(repeatedString)) {
                    return true;
                }
            }
        }
        return false;
    }
}