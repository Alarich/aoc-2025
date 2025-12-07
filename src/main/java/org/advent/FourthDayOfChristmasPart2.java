package org.advent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class FourthDayOfChristmasPart2 {
    final static char EMPTY = '.';
    final static char ACCESSIBLE_ROLL = 'x';
    final static char ROLL = '@';

    record Direction(int dx, int dy) {}
    final static Direction[] directions = {
            new Direction(-1, -1), new Direction(0, -1), new Direction(1, -1),
            new Direction(-1,  0),                               new Direction(1,  0),
            new Direction(-1,  1), new Direction(0,  1), new Direction(1,  1)
    };

    public static void main(String[] args) throws Exception {

        Instant startTime = Instant.now();

        Path p = Paths.get(
                FourthDayOfChristmasPart2.class.getClassLoader().getResource("FourthDayOfChristmasSource.txt").toURI()
        );
        List<String> grid = Files.readAllLines(p)
                .stream().toList();


        int gridWidth = grid.get(0).length();
        int gridHeight = grid.size();

        char[] gridAddresses = new char[gridHeight*gridWidth];

        /*
            Need to build a grid coordinate system that is able to detect nearby elements.
            To flatten the structure we can multiply the Y with the width of the grid and add the X
            Probably not really required, as multi-level array would be easier to +-1.
            Maybe during building we could get like a pattern of nearby rolls instead of doing another pass later.
         */
        for (int y = 0; y < grid.size(); y++) {
            String row = grid.get(y);
            for (int x = 0; x < row.length(); x++) {
                int index = y * gridWidth + x;
                gridAddresses[index] = row.charAt(x);
            }
        }

        int totalRolls = 0;
        int accessibleRolls = 0;
        do {
            mapAccessible(gridAddresses, gridWidth, gridHeight);
            totalRolls += accessibleRolls = replaceAccessible(gridAddresses);
        } while (accessibleRolls > 0);

        //printGrid(gridAddresses, gridWidth);
        //paintAccessibleGrid(gridAddresses, gridWidth);
        System.out.println("Total rolls:" + totalRolls);
        Instant end = Instant.now();
        System.out.println("Took: " + Duration.between(startTime, end).toMillis() + " ms");
    }

    private static void mapAccessible(char[] gridAddresses, int gridWidth, int gridHeight) {
        for (int index = 0; index < gridAddresses.length; index++) {
            if (gridAddresses[index] == EMPTY) continue;
            int nearbyRollCount = 0;
            int x = index % gridWidth;
            int y = index / gridWidth;

            for (Direction direction: directions) {
                int nx = x + direction.dx;
                int ny = y + direction.dy;
                // Check for out of bounds
                if (nx < 0 || nx >= gridWidth || ny < 0 || ny >= gridHeight) continue;
                int nIndex = ny * gridWidth + nx;

                // System.out.println("Index: " + index + " TargetIndex: " + nIndex + " Direction: (" + direction.dx + "," + direction.dy + ")");

                char target = gridAddresses[nIndex];
                if (target != EMPTY && ++nearbyRollCount == 4) break;
            }
            if (nearbyRollCount < 4) {
                gridAddresses[index] = ACCESSIBLE_ROLL;
            }
        }
    }

    private static int replaceAccessible(char[] gridAddresses) {
        int count = 0;
        for (int i = 0; i < gridAddresses.length; i++) {
            if (gridAddresses[i] == ACCESSIBLE_ROLL){
                gridAddresses[i] = EMPTY;
                count++;
            }
        }
        return count;
    }

    private static void printGrid(char[] gridAddresses, int gridWidth) {
        System.out.println("=== GridAddresses ===");
        for (int i = 0; i < gridAddresses.length; i++) {
            int y = i / gridWidth;
            int x = i % gridWidth;
            System.out.println("(" + x + "," + y + ") [" + i + "] = " + gridAddresses[i]);
        }
        System.out.println("=== GridAddresses ===");
    }

    private static void paintAccessibleGrid(char[] gridAddresses, int gridWidth) {
        System.out.println("=== AccessibleGrid ===");
        int row = 0;
        for (int i = 0; i < gridAddresses.length; i++) {
            int y = i / gridWidth;
            if (y != row) {
                row = y;
                System.out.println();
            }
            System.out.print(gridAddresses[i]);
        }
        System.out.println();
        System.out.println("=== AccessibleGrid ===");
    }

}