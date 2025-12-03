import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Solution {
    
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("input.txt")).trim();
        
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }
    
    private static long part1(String input) {
        List<String> arr = Arrays.asList(input.split(","));
        List<Long> res = new ArrayList<>();
        long sum = 0;
        for(String s : arr) {
            long range1 = Long.parseLong(s.split("-")[0]);
            long range2 = Long.parseLong(s.split("-")[1]);
            for(long i = range1; i <= range2; i++) {
                String str = String.valueOf(i);
                int patternLength = str.length();
                String first = str.substring(0, patternLength/2);
                String second = str.substring(patternLength/2, str.length());

                if(first.equals(second)) {
                    res.add(i);
                }
            }
        }
        for(long i : res) {
            sum += i;
        }
        return sum;
    }
    
    private static BigInteger part2(String input) {
        List<String> arr = Arrays.asList(input.split(","));
        List<BigInteger> doubleList = new ArrayList<>();
        BigInteger sum = BigInteger.ZERO;
        for(String s : arr) {
            BigInteger range1 = new BigInteger(s.split("-")[0]);
            BigInteger range2 = new BigInteger(s.split("-")[1]);
            for(BigInteger i = range1; i.compareTo(range2) <= 0; i = i.add(BigInteger.ONE)) {
                String str = i.toString();
                for(int j = 1; j < str.length(); j++) {
                    if (str.length() % j == 0) {
                        String pattern = "(" + str.substring(0, j) + ")\\1{" + (str.length()/j - 1) + "}";
                        if (Pattern.matches(pattern, str)) {
                            if (!doubleList.contains(new BigInteger(str))) {
                                doubleList.add(new BigInteger(str));
                            }
                            break;
                        }
                    }
                }
            }
        }
        for(BigInteger i : doubleList) {
            sum = sum.add(i);
        }
        return sum;
    }
}
