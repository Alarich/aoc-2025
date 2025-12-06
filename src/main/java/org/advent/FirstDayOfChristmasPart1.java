package org.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class FirstDayOfChristmasPart1 {
    public static void main(String[] args) throws Exception {
        Instant startTime = Instant.now();

        int currentPosition = 50;
        char left = 'L';
        int zeroedCounter = 0;

        Path p = Paths.get(
                FirstDayOfChristmasPart1.class.getClassLoader().getResource("FirstDayOfChristmasSource.txt").toURI()
        );
        List<String> paths = Files.readAllLines(p)
                .stream().toList();

        for (String path : paths) {
            char direction = path.charAt(0);
            int count = Integer.parseInt(path.substring(1)) % 100;
            if (direction == left) {
                currentPosition -= count;
            } else {
                currentPosition += count;
            }

            if (currentPosition > 99) {
                currentPosition = currentPosition - 100;
            } else if (currentPosition < 0) {
                currentPosition = 100 + currentPosition;
            }

            if (currentPosition == 0) {
                zeroedCounter++;
            }
        }

        System.out.println("Zeroed Counter: " + zeroedCounter);

        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }
}