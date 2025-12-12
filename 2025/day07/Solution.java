import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

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

    private static String part2(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        // TODO: Implement part 2
        return "";
    }
}
