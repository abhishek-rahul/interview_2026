// BFS Code Template

import java.util.*;
public class BFSGraph {

    static void bfs(int start, List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);       // 1️⃣ start node
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();   // 2️⃣ process node
            System.out.print(node + " ");

            for (int neigh : graph.get(node)) { // 3️⃣ neighbors
                if (!visited[neigh]) {
                    visited[neigh] = true;
                    queue.offer(neigh);
                }
            }
        }
    }

    public static void main(String[] args) {

        int n = 4; // number of nodes
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        // edges
        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);

        bfs(0, graph); // Output: 0 1 2 3
    }
}

// DFS Code Template 
class Solution {

    int[] drow = {-1,1,0,0};
    int[] dcol = {0,0,-1,1};

    private static int sum = 0;
    
    private void dfs(int i,int j,int rowLen , int colLen, int[][] grid) {
        int trow,tcol;
        if(i<0 || i>rowLen || j<0 || j>colLen || grid[i][j]==0)
            return ;
        else {
            sum++;
        }
        grid[i][j] = 0;
        
        for(int k=0;k<4;k++){
            trow = i + drow[k];
            tcol = j + dcol[k];
            if( (trow>=0 && trow<rowLen && tcol>=0 && tcol<colLen ) && grid[trow][tcol]==1){
                dfs(trow,tcol,rowLen,colLen,grid);            }
        }
    }

    public int maxAreaOfIsland(int[][] grid) {
        int maxi = 0;
        int rowLen = grid.length;
        int colLen = grid[0].length;
        for(int i = 0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++) {
                if( grid[i][j]==1) {
                    sum = 0;
                    dfs(i,j,rowLen,colLen,grid);
                    maxi = Math.max(sum,maxi);
                }
            }
        }
        return maxi;
    }
}

// Topological Sort using BFS  indegree

import java.util.*;

public class TopoSort {

    static void topoSort(int n, List<List<Integer>> graph) {

        int[] indegree = new int[n];

        // 1️⃣ indegree calculate karo
        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                indegree[v]++;
            }
        }

        // 2️⃣ queue me indegree = 0 wale nodes
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0)
                q.offer(i);
        }

        // 3️⃣ BFS
        while (!q.isEmpty()) {
            int node = q.poll();
            System.out.print(node + " ");

            for (int neigh : graph.get(node)) {
                indegree[neigh]--;
                if (indegree[neigh] == 0) {
                    q.offer(neigh);
                }
            }
        }
    }

    public static void main(String[] args) {

        int n = 4;
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);

        topoSort(n, graph); // Output: 0 1 2 3
    }
}

// Topological Sort using DFS 

import java.util.*;

public class DFSTopoSort {

    static void dfs(int node,
                    List<List<Integer>> graph,
                    boolean[] visited,
                    Stack<Integer> stack) {

        visited[node] = true;

        for (int neigh : graph.get(node)) {
            if (!visited[neigh]) {
                dfs(neigh, graph, visited, stack);
            }
        }

        // 1️⃣ saare neighbors ho jaane ke baad push
        stack.push(node);
    }

    static void topoSort(int n, List<List<Integer>> graph) {

        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, graph, visited, stack);
            }
        }

        // 2️⃣ stack pop = topological order
        while (!stack.isEmpty()) {
            System.out.print(stack.pop() + " ");
        }
    }

    public static void main(String[] args) {

        int n = 4;
        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());

        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);

        topoSort(n, graph); // Output: 0 2 1 3 (one valid order)
    }
}

// Detect Cycle in a Undirected Graph using DFS 
public class CycleDetectionDFS {

    public static boolean hasCycle(int n, List<List<Integer>> adj) {
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (dfs(i, -1, visited, adj)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfs(int node, int parent,
                               boolean[] visited,
                               List<List<Integer>> adj) {

        visited[node] = true;

        for (int nei : adj.get(node)) {
            if (!visited[nei]) {
                if (dfs(nei, node, visited, adj)) {
                    return true;
                }
            }
            // visited but not parent → cycle
            else if (nei != parent) {
                return true;
            }
        }
        return false;
    }
}

// Detect Cycle in a Undirected Graph using BFS

import java.util.*;

public class CycleDetectionBFS {

    static class Pair {
        int node, parent;
        Pair(int n, int p) {
            node = n;
            parent = p;
        }
    }

    public static boolean hasCycle(int n, List<List<Integer>> adj) {
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                if (bfs(i, visited, adj)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean bfs(int src,
                               boolean[] visited,
                               List<List<Integer>> adj) {

        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(src, -1));
        visited[src] = true;

        while (!q.isEmpty()) {
            Pair cur = q.poll();

            for (int nei : adj.get(cur.node)) {
                if (!visited[nei]) {
                    visited[nei] = true;
                    q.offer(new Pair(nei, cur.node));
                }
                // visited but not parent → cycle
                else if (nei != cur.parent) {
                    return true;
                }
            }
        }
        return false;
    }
}


// Dijkstra's Algorithm :
import java.util.*;

// For adjacency list
class Pair {
    int node;
    int weight;

    Pair(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}

// Separate comparator class (min-heap by weight)
class PairWeightComparator implements Comparator<Pair> {
    @Override
    public int compare(Pair a, Pair b) {
        return Integer.compare(a.weight, b.weight);
    }
}

public class DijkstraTemplate {

    // n = number of nodes (0 to n-1)
    // graph = adjacency list
    // src = source node
    public static int[] dijkstra(int n, List<List<Pair>> graph, int src) {

        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Min-heap: (distance, node)
        PriorityQueue<Pair> pq = new PriorityQueue<>(new PairWeightComparator());

        pq.offer(new Pair(src, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int node = curr.node;
            int currDist = curr.weight;

            // Skip outdated entry
            if (currDist > dist[node]) continue;

            for (Pair nbr : graph.get(node)) {
                int nextNode = nbr.node;
                int edgeWeight = nbr.weight;

                // Optional safety: avoid overflow
                if (dist[node] != Integer.MAX_VALUE && dist[node] + edgeWeight < dist[nextNode]) {
                    dist[nextNode] = dist[node] + edgeWeight;
                    pq.offer(new Pair(nextNode, dist[nextNode]));
                }
            }
        }

        return dist;
    }
}

// Clone Graph 
class Solution {

    private Node getClone(Node node,Node[] visited) {
        if ( node == null) {
            return node;
        }
        if(visited[node.val]!=null)
            return visited[node.val];
        Node copy = new Node(node.val);
        visited[copy.val]=copy;

        List<Node> neighbourNodes ;
        neighbourNodes = node.neighbors;

        Node neighbourClone;

        for ( Node neighbourNode : neighbourNodes) {
            if (visited[neighbourNode.val]==null){
                neighbourClone = getClone(neighbourNode,visited);
                copy.neighbors.add(neighbourClone);
            } else {
                copy.neighbors.add(visited[neighbourNode.val]);
            }
        }
        return copy;
    }

    public Node cloneGraph(Node node) {
        if ( node == null) {
            return node;
        }
        Node [] visited= new Node[105];
        Arrays.fill(visited,null);
        Node copyGraph = getClone(node,visited);
        return copyGraph;
    }
}

// Matrix BFS Number of Islands 
// 40 minutes 

class Pair {
    int row;
    int col;
    Pair(int row,int col) {
        this.row = row;
        this.col = col;
    }
}

class Solution {

    int[] dirRow = {0,0,1,-1};
    int[] dirCol = {1,-1,0,0};

    private void bfs(char[][] grid,int rowLen, int colLen,int row,int col) {
        int newRow,newCol;
        Queue<Pair> queue = new LinkedList<>();
        Pair p;
        queue.offer(new Pair(row,col));
        while(!queue.isEmpty()) {
            p = queue.poll();
            grid[p.row][p.col] = '0';
            for(int i=0;i<4;i++) {
                newRow = p.row+dirRow[i];
                newCol = p.col+dirCol[i];
                if ( newRow>=0 && newRow<rowLen && newCol>=0 && newCol<colLen && grid[newRow][newCol] == '1'){
                    grid[newRow][newCol] = '0';
                    queue.offer(new Pair(newRow,newCol));
                }
            }
        }
    }

    public int numIslands(char[][] grid) {
        int rowLen = grid.length;
        int colLen = grid[0].length;
        //System.out.println(rowLen);
        //System.out.println(colLen);
        int cnt = 0;
        for (int i=0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++) {
                if(grid[i][j]=='1') {
                    bfs(grid,rowLen,colLen,i,j);
                    cnt++;
                }
            }
        }
        return cnt;
    }
}

// Matrix DFS Number of Islands

class Solution {

    int[] dirRow = {0,0,1,-1};
    int[] dirCol = {1,-1,0,0};

    private void dfs (char[][] grid,int rowLen,int colLen , int row, int col ) {
        if ( row<0 || row>=rowLen || col<0 || col>=colLen) {
            return ;
        }
        if ( row>=0 && row<rowLen && col>=0 && col<colLen && grid[row][col]!='1') {
            return ;
        }
        grid[row][col] = '0';
        int newRow,newCol;
        for(int i=0;i<4;i++) {
            newRow = row + dirRow[i];
            newCol = col + dirCol[i];
            if ( newRow>=0 && newRow<rowLen && newCol>=0 && newCol<colLen && grid[newRow][newCol]=='1') {
                dfs(grid,rowLen,colLen,newRow,newCol);
            }
        }   

    }

    public int numIslands(char[][] grid) {
        int rowLen = grid.length;
        int colLen = grid[0].length;
        int cnt = 0;
        for (int i=0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++) {
                if(grid[i][j]=='1') {
                    dfs(grid,rowLen,colLen,i,j);
                    cnt++;
                }
            }
        }
        return cnt;
    }
}

// Cycle in Undirected Graph

    private boolean detectCycle(int source,int parent,List<List<Integer>> graph,int[] visited) {
        visited[source] = 1;
        int dest ;
        
        for(int i=0;i<graph.get(source).size();i++){
            dest = graph.get(source).get(i);
            if(visited[dest]==0)
            {
                if(detectCycle(dest,source,graph,visited))
                    return true;
            }
            else if(visited[dest]==1 && dest!=parent)
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean isCycle(int V, int[][] edges) {
        // Code here
        List<List<Integer>> graph = new ArrayList<List<Integer>>();
        
        populateGraph(V,graph,edges);
        
        boolean isCyclePresent = false;
        int[] visited = new int[V];
        Arrays.fill(visited,0);
        for(int i=0;i<V;i++) {
            if(visited[i]==0) {
                isCyclePresent = detectCycle(i,-1,graph,visited);
                if(isCyclePresent)
                    return isCyclePresent;
            }
        }
        return isCyclePresent;
    }


// Cycle in a directed Graph 
class Solution {
    // Function to detect cycle in a directed graph.
    
    private boolean isCycle(int source,int [] visited,ArrayList<ArrayList<Integer>> adj)
    {
        visited[source] = 1;
        int dest;
        for(int i=0;i<adj.get(source).size();i++)
        {
            dest = adj.get(source).get(i);
            if(visited[dest]==0)
            {
                if(isCycle(dest,visited,adj))
                    return true;
            }
            else if(visited[dest]==1)
            {
                return true;
            }
        }
        visited[source] = 2;
        return false;
    }   
    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adj) {
         int visited[] = new int[V+5];
        Arrays.fill(visited,0);
        
        for(int i=0;i<V;i++)
        {
            if(visited[i]==0)
            {
                if(isCycle(i,visited,adj))
                    return true;
            }
        }
        return false; 
    }
}

// Course Schedule 1 
