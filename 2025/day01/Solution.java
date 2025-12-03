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
        List<String> arr = Arrays.asList(input.split("\n"));
        int dial = 50;
        int count = 0;
        for(String move : arr) {
            char[] str = move.toCharArray();
            if(str[0]=='L') {
                String val = "";
                for (int i = 1; i < str.length; i++) {
                    val += str[i]; 
                }
                int number = Integer.parseInt(val);
                dial = ((dial - number)%100 + 100)%100;
            } else {
                String val = "";
                for (int i = 1; i < str.length; i++) {
                    val += str[i]; 
                }
                int number = Integer.parseInt(val);
                dial = (dial + number)%100;
            }
            if(dial==0) {
                count++;
            }
        }
        return count;
    }
    
    private static String part2(String input) {
        return "null";
    }
}
