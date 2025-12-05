import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Paths.get("input.txt")).trim();

        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }

    private static long part1(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        List<String> range = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        boolean beforeBlank = true;
        for (String line : lines) {
            if (beforeBlank) {
                if (line.isEmpty()) {
                    beforeBlank = false;
                } else {
                    range.add(line);
                }
            } else {
                ingredients.add(line);
            }
        }

        int freshCount = 0;

        for (String ingredient : ingredients) {
            long value = Long.parseLong(ingredient);

            boolean isFresh = false;

            for (String r : range) {
                long range1 = Long.parseLong(r.split("-")[0]);
                long range2 = Long.parseLong(r.split("-")[1]);
                if (value >= range1 && value <= range2) {
                    isFresh = true;
                    break;
                }
            }
            if (isFresh) {
                freshCount++;
            }
        }
        return freshCount;
    }

    private static long part2(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        // TODO: Implement part 2
        return 0;
    }
}
