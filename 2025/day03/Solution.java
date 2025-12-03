import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("input.txt")).trim();

        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }

    private static int part1(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        int totalSum = 0;
        
        for (String line : lines) {
            int maxJoltage = 0;
            for (int i = 0; i < line.length(); i++) {
                for (int j = i + 1; j < line.length(); j++) {
                    int digit1 = Integer.parseInt(String.valueOf(line.charAt(i)));
                    int digit2 = Integer.parseInt(String.valueOf(line.charAt(j)));
                    int joltage = digit1 * 10 + digit2;
                    if (joltage > maxJoltage) {
                        maxJoltage = joltage;
                    }
                }
            }
            totalSum += maxJoltage;
        }
        return totalSum;
    }

    private static int part2(String input) {
        // List<String> lines = Arrays.asList(input.split("\n"));

        return 0;
    }
}
