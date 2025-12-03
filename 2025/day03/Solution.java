import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Solution {
    
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("2025/day03/input.txt")).trim();
        
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }
    
    private static String part1(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        // TODO: Implement part 1
        return "";
    }
    
    private static String part2(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        // TODO: Implement part 2
        return "";
    }
}
