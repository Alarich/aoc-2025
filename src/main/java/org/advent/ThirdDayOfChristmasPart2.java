package org.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class ThirdDayOfChristmasPart2 {
    public static void main(String[] args) throws Exception {
        Instant startTime = Instant.now();

        Path p = Paths.get(
                ThirdDayOfChristmasPart2.class.getClassLoader().getResource("ThirdDayOfChristmasSource.txt").toURI()
        );
        List<String> batteryBanks = Files.readAllLines(p)
                .stream().toList();

        long totalJoltage = 0;
        for (String batteryBank : batteryBanks) {
            int bankSizePool = 12;
            int[] bankSizeJoltages = new int[12];

            // Start the seekPosition at negative one to offset the start
            int seekPosition = -1;
            while (bankSizePool > 0) {
                // Reducing pool to 0, we start looking at the minimum seek position for the next largest number
                // until we can't seek further due to running out of available batteries.
                for (int i = seekPosition+1; i < batteryBank.length(); i++) {
                    int iJoltage = Character.getNumericValue(batteryBank.charAt(i));
                    if (iJoltage > bankSizeJoltages[12-bankSizePool]) {
                        seekPosition = i;
                        bankSizeJoltages[12-bankSizePool] = iJoltage;
                    }
                    if (batteryBank.length() - i - bankSizePool <= 0) {
                        bankSizePool--;
                        break;
                    }
                }
            }

            String batteryJoltage = flattenBatteryCells(bankSizeJoltages);
            //System.out.println("BatteryBank:" + batteryBank);
            //System.out.println("BatteryBankJoltage:" + batteryJoltage);
            totalJoltage += Long.parseLong(batteryJoltage);
        }

        System.out.println("Result:" + totalJoltage);
        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }

    private static String flattenBatteryCells(int[] bankSizeJoltages) {
        StringBuilder stringBuilder = new StringBuilder(bankSizeJoltages.length);
        for (int joltage : bankSizeJoltages) stringBuilder.append(joltage);
        return stringBuilder.toString();
    }
}