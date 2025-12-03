import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Solution {
    
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("2025/day02/input.txt")).trim();
        
        int[] results = solve(input);
        System.out.println("Part 1: " + results[0]);
        System.out.println("Part 2: " + results[1]);
    }
    
    private static int[] solve(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        
        int part1 = 0;
        int part2 = 0;
        
        // TODO: Implement solution for both parts
        for (String line : lines) {
            // Process each line
        }
        
        return new int[]{part1, part2};
    }
}
