/*
 * Time Complexity: O(m*n)^2
 * Space Complexity: O(h) - recursive stack space
 * 
 * Did this Code run successfully on LeetCode: Yes
 * 
 * Approach: DFS - the idea is to pick any one rotten node and perform DFS on its adjacent nodes
 * using the directions array. We will keep an offset time of 2(rotten orange time) which will be subtracted at 
 * the end. The main thing to remember here is that since this is DFS, it is possible that one node might
 * get rotten sooner since it is near to one rotten orange as compared to from the other side.
 * So, each time we try to perform DFS, we need to do the time check that if the incoming time is lesser than 
 * existing time, only then perform DFS
 */

public class RottenOranges {
        
    int dirs[][] = {{0,1},{-1,0}, {1,0},{0,-1}};
    int m, n;
    public int orangesRotting(int[][] grid) {
        if(grid.length == 0 || grid == null)
            return 0;
        
         m = grid.length;
         n = grid[0].length;

        
        for(int i = 0; i<m; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(grid[i][j] == 2)
                {
                    dfs(grid,i,j,2);
                }
            }
        }
        
        int max = 0;
        for(int i = 0; i < m; i ++)
        {
            for(int j = 0; j < n; j++)
            {
                if(grid[i][j] == 1)
                {
                    return -1;
                }
                else if (grid[i][j] != 0 )
                {
                    max = Math.max(max,grid[i][j] - 2);
                }

            }
        }
        return max;
    }
    
    private void dfs(int[][] grid,int i, int j, int time)
    {
        //base
        if(i < 0 || j < 0 || i == m || j == n)
            return;
        
        if(grid[i][j] != 1 && grid[i][j] < time)
            return;
        
        
        //logic
        grid[i][j] = time;
        for(int[] dir: dirs)
        {
            int nr = i + dir[0];
            int nc = j + dir[1];
            dfs(grid,nr,nc,time + 1);
        }
    }
}


/*
 * Time Complexity: O(m*n)
 * Space Complexity: O(n)
 * Did this Code run successfully on LeetCode: Yes
 * Approach: BFS - iterate over the grid and keep a count of the fresh number of oranges and add the rotten oranges. Take each rotten
 * orange out and add iterate in all 4 directions over all the nodes adjacent to the rotten orange and if it fresh, make it rotten and reduce the count of fresh
 * and add the rotten orange to the queue. After every BFS level, increment the time variable. 
 * In the end, remember that you must reduce the time variable by 1 because even after rotting the last orange,
 * we increment the counter so we must reduce the counter by 1.
 */
class Solution {
    int m,n;
    int dirs[][] = {{0,1},{0,-1},{1,0},{-1,0}};
    Queue<int[]> q;
    public int orangesRotting(int[][] grid) {
        if(grid == null || grid.length == 0)
            return 0;
        m = grid.length;
        n = grid[0].length;
        q = new LinkedList<>();
            
        int fresh = 0;
        for(int i = 0;i < m; i++)
        {
            for(int j = 0;j < n;j++)
            {
                if(grid[i][j] == 1)
                {
                    fresh++;
                }
                else if(grid[i][j] == 2)
                {
                    q.add(new int[]{i,j});
                }
            }
        }
        
        if(fresh == 0)
        {
            return 0;
        }
        
        int time = 0;
        while(!q.isEmpty())
        {
            int size = q.size();
            for(int i = 0; i<size;i++)
            {
                int curr[] = q.poll();
                
                for(int dir[]:dirs)
                {
                    int nr = curr[0] + dir[0];
                    int nc = curr[1] + dir[1];
                    
                    if(nr >= 0 && nc >= 0 && nr < m && nc < n && grid[nr][nc] == 1)
                    {
                        grid[nr][nc] = 2;
                        fresh--;
                        q.add(new int[]{nr,nc});
                    }
                }
            }
            time++;
        }
        
        if(fresh != 0)
            return -1;
        return time - 1;
            
    }
}