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
        int dial = 50;
        int count = 0;
        
        for (String move : lines) {
            char direction = move.charAt(0);
            int distance = Integer.parseInt(move.substring(1));
            if (direction == 'L') {
                dial = ((dial - distance) % 100 + 100) % 100;
            } else {
                dial = (dial + distance) % 100;
            }
            if (dial == 0) {
                count++;
            }
        }
        
        return count;
    }
    
    private static int part2(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        int dialPosition = 50;
        int zeroCount = 0;
        
        for (String rotation : lines) {
            char direction = rotation.charAt(0);
            int distance = Integer.parseInt(rotation.substring(1));
            
            for (int i = 1; i <= distance; i++) {
                if (direction == 'L') {
                    dialPosition = (dialPosition - 1 + 100) % 100;
                } else if (direction == 'R') {
                    dialPosition = (dialPosition + 1) % 100;
                }
                
                if (dialPosition == 0) {
                    zeroCount++;
                }
            }
        }
        return zeroCount;
    }
}
