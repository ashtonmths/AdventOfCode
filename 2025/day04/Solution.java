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

    static int neighborhood[][] = {
            { -1, -1 }, { -1, 0 }, { -1, 1 },
            { 0, -1 }, { 0, 1 },
            { 1, -1 }, { 1, 0 }, { 1, 1 }
    };

    private static int part1(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);
            for (int c = 0; c < cols; c++) {
                grid[r][c] = line.charAt(c);
            }
        }
        int accessible = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '@') continue;
                int neighborCount = 0;
                for (int[] n : neighborhood) {
                    int nr = r + n[0];
                    int nc = c + n[1];

                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                    char neighbor = grid[nr][nc];
                    if(neighbor == '@') {
                        neighborCount++;
                    }
                }
                if (neighborCount < 4) {
                   accessible++;
                }
            }
        }

        return accessible;
    }

    private static int part2(String input) {
        List<String> lines = Arrays.asList(input.split("\n"));
        return 0;
    }
}
