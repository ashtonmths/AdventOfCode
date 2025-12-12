import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.math.BigInteger;

public class Solution {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("input.txt")).trim();

        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }

    private static long part1(String input) throws IOException {
        List<String> lines = Arrays.asList(input.split("\n"));
        StringBuilder output = new StringBuilder();
        Set<Integer> beams = new HashSet<>();
        int splitCount = 0;

        for (int i = 0; i < lines.size(); i++) {
            String currLine = lines.get(i);
            StringBuilder curr = new StringBuilder(currLine);
            output.append(curr).append("\n");

            for (int j = 0; j < curr.length(); j++) {
                if (curr.charAt(j) == 'S') {
                    beams.clear();
                    beams.add(j);
                }
            }

            if (beams.isEmpty())
                continue;
            if (i + 1 < lines.size()) {
                StringBuilder next = new StringBuilder(lines.get(i + 1));
                Set<Integer> nextBeams = new HashSet<>();
                for (int c : beams) {
                    if (c < 0 || c >= next.length())
                        continue;
                    char ch = next.charAt(c);
                    if (ch == '^') {
                        splitCount++;
                        nextBeams.add(c - 1);
                        nextBeams.add(c + 1);
                    } else {
                        next.setCharAt(c, '|');
                        nextBeams.add(c);
                    }
                }
                lines.set(i + 1, next.toString());
                beams = nextBeams;
            }
        }
        return splitCount;
    }

    private static BigInteger part2(String input)throws IOException{
        List<String>lines=Arrays.asList(input.split("\n"));
        Map<Integer,BigInteger>beams=new HashMap<>();
    
        for(int i=0;i<lines.size();i++){
            String row=lines.get(i);
    
            for(int j=0;j<row.length();j++){
                if(row.charAt(j)=='S'){
                    beams.clear();
                    beams.put(j,BigInteger.ONE);
                }
            }
    
            if(beams.isEmpty())continue;
            if(i+1<lines.size()){
                String next=lines.get(i+1);
                Map<Integer,BigInteger>nextBeams=new HashMap<>();
    
                for(Map.Entry<Integer,BigInteger>e:beams.entrySet()){
                    int c=e.getKey();
                    BigInteger v=e.getValue();
                    if(c<0||c>=next.length())continue;
    
                    char ch=next.charAt(c);
                    if(ch=='^'){
                        nextBeams.put(c-1,nextBeams.getOrDefault(c-1,BigInteger.ZERO).add(v));
                        nextBeams.put(c+1,nextBeams.getOrDefault(c+1,BigInteger.ZERO).add(v));
                    }else{
                        nextBeams.put(c,nextBeams.getOrDefault(c,BigInteger.ZERO).add(v));
                    }
                }
                beams=nextBeams;
            }
        }
    
        BigInteger total=BigInteger.ZERO;
        for(BigInteger v:beams.values())total=total.add(v);
        return total;
    }
}
