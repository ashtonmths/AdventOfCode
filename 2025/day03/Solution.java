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

    private static long part2(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        long totalSum = 0;
        
        for (String line : lines) {
            StringBuilder result = new StringBuilder();
            int pos = 0;
            
            for (int digitCount = 0; digitCount < 12; digitCount++) {
                int maxDigit = -1;
                int maxPos = pos;
                int searchLimit = line.length() - (12 - digitCount - 1);
                
                for (int i = pos; i < searchLimit; i++) {
                    int digit = Character.getNumericValue(line.charAt(i));
                    if (digit > maxDigit) {
                        maxDigit = digit;
                        maxPos = i;
                    }
                }
                
                result.append(maxDigit);
                pos = maxPos + 1;
            }
            
            totalSum += Long.parseLong(result.toString());
        }
        
        return totalSum;
    }
}
