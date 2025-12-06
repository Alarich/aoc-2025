package org.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class ThirdDayOfChristmasPart1 {
    public static void main(String[] args) throws Exception {
        Instant startTime = Instant.now();

        Path p = Paths.get(
                ThirdDayOfChristmasPart1.class.getClassLoader().getResource("ThirdDayOfChristmasSource.txt").toURI()
        );
        List<String> batteryBanks = Files.readAllLines(p)
                .stream().toList();

        long totalJoltage = 0;
        for (String batteryBank : batteryBanks) {
            int batteryOneAddress = 0;
            int batteryOneJoltage = 0;
            int batteryTwoAddress = 0;
            int batteryTwoJoltage = 0;
            // Search through the bank for the highest power battery, always ignore the last one.
            for (int i = 0; i < batteryBank.length() - 1; i++) {
                int iJoltage = Character.getNumericValue(batteryBank.charAt(i));
                if (iJoltage > batteryOneJoltage) {
                    batteryOneJoltage = iJoltage;
                    batteryOneAddress = i;
                }
            }
            // Search through the rest of the bank for the highest second battery.
            for (int i = batteryOneAddress + 1; i < batteryBank.length(); i++) {
                int iJoltage = Character.getNumericValue(batteryBank.charAt(i));
                if (iJoltage > batteryTwoJoltage) {
                    batteryTwoJoltage = iJoltage;
                    batteryTwoAddress = i;
                }
            }
            
            String batteryJoltage = "" + batteryOneJoltage + batteryTwoJoltage;
            System.out.println("BatteryBank:" + batteryBank);
            System.out.println("BatteryBankJoltage:" + batteryJoltage);
            totalJoltage += Long.parseLong(batteryJoltage);
        }


        System.out.println("Result:" + totalJoltage);
        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }
}