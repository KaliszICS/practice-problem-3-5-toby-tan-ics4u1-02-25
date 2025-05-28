import java.util.*;

public class PracticeProblem {

    // BFS for minimum moves from S to F
    public static int searchMazeMoves(String[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;

        int[] start = findStart(maze);
        boolean[][] visited = new boolean[rows][cols];

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start[0], start[1], 0}); // row, col, steps
        visited[start[0]][start[1]] = true;

        int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}}; // up, down, left, right

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1], steps = curr[2];

            if (maze[r][c].equals("F")) {
                return steps;
            }

            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (inBounds(nr, nc, maze) && !visited[nr][nc] && !maze[nr][nc].equals("#")) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc, steps + 1});
                }
            }
        }

        return -1; // no path found
    }

    // DFS for total number of unique paths from S to F
    public static int noOfPaths(String[][] maze) {
        int[] start = findStart(maze);
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        return dfs(maze, visited, start[0], start[1]);
    }

    private static int dfs(String[][] maze, boolean[][] visited, int r, int c) {
        if (!inBounds(r, c, maze) || visited[r][c] || maze[r][c].equals("#")) return 0;
        if (maze[r][c].equals("F")) return 1;

        visited[r][c] = true;

        int count = dfs(maze, visited, r + 1, c)
                  + dfs(maze, visited, r - 1, c)
                  + dfs(maze, visited, r, c + 1)
                  + dfs(maze, visited, r, c - 1);

        visited[r][c] = false; // backtrack
        return count;
    }

    private static boolean inBounds(int r, int c, String[][] maze) {
        return r >= 0 && r < maze.length && c >= 0 && c < maze[0].length;
    }

    private static int[] findStart(String[][] maze) {
        int row = maze.length - 1;
        for (int col = 0; col < maze[0].length; col++) {
            if (maze[row][col].equals("S")) return new int[]{row, col};
        }
        throw new RuntimeException("Start point 'S' not found.");
    }
}
