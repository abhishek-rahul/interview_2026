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

class Solution {

    private void populateGraph(ArrayList<Integer> [] graph,int[][] prerequisites){
        int len = prerequisites.length;
        int u,v;
        for(int i=0;i<len;i++){
            u = prerequisites[i][1];
            v = prerequisites[i][0];
            graph[u].add(v);
        }
    }

    private boolean canFinishDFS(ArrayList<Integer> [] graph,int[] visited,int node) {
        if(visited[node]==1){
            return false;
        }

        if(visited[node]==2) {
            return true;
        }

        visited[node]=1;

        ArrayList<Integer> adjacentNodes = graph[node];
        int adjacentNode;
        for(int i=0;i<adjacentNodes.size();i++) {
            adjacentNode = adjacentNodes.get(i);
            if (visited[adjacentNode]==0 || visited[adjacentNode]==1) {
                if(!canFinishDFS(graph,visited,adjacentNode)){
                    return false;
                }
            }
        }
        visited[node]=2;
        return true;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<Integer> [] graph = new ArrayList[numCourses];
        for(int i=0;i<numCourses;i++) {
            graph[i] = new ArrayList<Integer>();
        }
        populateGraph(graph,prerequisites);
        int[] visited = new int[numCourses];

        Arrays.fill(visited,0);

        for(int i=0;i<numCourses;i++) {
            if(visited[i]==0){
                if(!canFinishDFS(graph,visited,i)){
                    return false;
                }
            }
        }
        return true;
    }
}

// Course Schedule 2
class Solution {

    private void populateGraph(ArrayList<Integer> [] graph,int[][] prerequisites){
        int len = prerequisites.length;
        int u,v;
        for(int i=0;i<len;i++){
            u = prerequisites[i][1];
            v = prerequisites[i][0];
            graph[u].add(v);
        }
    }

    private boolean canFinishDFS(ArrayList<Integer> [] graph,int[] visited,int node,Stack<Integer> stack) {
        
        if(visited[node]==1){
            return false;
        }

        if(visited[node]==2) {
            return true;
        }

        visited[node]=1;

        ArrayList<Integer> adjacentNodes = graph[node];
        int adjacentNode;
        for(int i=0;i<adjacentNodes.size();i++) {
            adjacentNode = adjacentNodes.get(i);
            if (visited[adjacentNode]==0 || visited[adjacentNode]==1) {
                if(!canFinishDFS(graph,visited,adjacentNode,stack)){
                    return false;
                }
            }
        }
        visited[node]=2;
        stack.push(node);
        return true;
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        ArrayList<Integer> [] graph = new ArrayList[numCourses];
        Stack<Integer> stack = new Stack<>();
        for(int i=0;i<numCourses;i++) {
            graph[i] = new ArrayList<Integer>();
        }
        populateGraph(graph,prerequisites);
        int[] visited = new int[numCourses];

        Arrays.fill(visited,0);
        int [] order={};

        for(int i=0;i<numCourses;i++) {
            if(visited[i]==0){
                if(!canFinishDFS(graph,visited,i,stack)){
                    return order;
                }
            }
        }
        order = new int[numCourses];
        int cnt = 0;
        while(!stack.empty()){
            order[cnt]=stack.pop();
            cnt++;
        }
        return order;
    }
}


// Alien Dictionary [ Not the correct code  ]

class Solution {
    // populate kro lettematrix ko jahan bhi connection dikhe
    private boolean populateGraph(String[] words,ArrayList<Character>[] graph,int[] visited) {
        char u,v;
        String firstWord,secondWord;
        for (int i=0;i<words.length-1;i++) {
            firstWord = words[i];
            secondWord = words[i+1];

            int len = Math.min(firstWord.length(),secondWord.length());
            boolean canBePrefix=true;
            for(int j=0;j<len;j++) {
                if(firstWord.charAt(j)!=secondWord.charAt(j)){
                    canBePrefix = false;
                    u = firstWord.charAt(j);
                    v = secondWord.charAt(j);
                    graph[u-'a'].add(v);
                    visited[u-'a']=0;
                    visited[v-'a']=0;
                    break;
                }
            }
            if(canBePrefix && firstWord.length()>secondWord.length()){
                return false;
            }
        }
        return true;
    }

    // 26 ka array list init karo
    private void initGraph(ArrayList<Character>[] graph) {
        for(int i=0;i<26;i++) {
            graph[i] = new ArrayList<Character>();
        }
    }

    private void fillAllVisited(String[] words,int[] allVisited) {
        String word;
        for(int i=0;i<words.length;i++) {
            word = words[i];
            for(int j=0;j<word.length();j++) {
                allVisited[word.charAt(j)-'a']=0;
            }
        }
    }

    private boolean getTopoSortOrder(ArrayList<Character>[] graph,int[] visited,char source,Stack<Character> stack) {
        if (visited[source-'a']==1){
            return false;
        }
        if (visited[source-'a']==2){
            return true;
        }
        visited[source-'a']=1;
        ArrayList<Character> adjacentCharacters = graph[source-'a'];
        char adjacentCharacter;
        for(int i=0;i<adjacentCharacters.size();i++) {
            adjacentCharacter = adjacentCharacters.get(i);
            if (visited[adjacentCharacter-'a']==0) {
                if (!getTopoSortOrder(graph,visited,adjacentCharacter,stack)){
                    return false;
                }
            }
        }
        visited[source-'a']=2;
        stack.push(source);
        return true;
    }

    public String foreignDictionary(String[] words) {
        int[] visited = new int[26];
        int[] allVisited = new int[26];
        Arrays.fill(visited,-1);
        Arrays.fill(allVisited,-1);
        ArrayList<Character>[] graph = new ArrayList[26];
        initGraph(graph);
        boolean isValid = true;
        System.out.println("0.1");
        /*
        if(words.length==1) {
            return "";
        }
        */
        System.out.println("0.2");
        isValid = populateGraph(words,graph,visited);
        if ( !isValid ){
            return "";
        }
        System.out.println("1");
        Stack<Character> stack = new Stack<>();
        // print graph  
        for(int i=0;i<26;i++) {
            if(visited[i]==0) {
                System.out.println("");
                System.out.println((char)('a'+i));
                for(int j=0;j<graph[i].size();j++) {
                    System.out.print(graph[i].get(j));  
                    System.out.print(",");  
                }
                System.out.println("");
            }
        }
        // Stack ka order decide hoga
        for(int i=0;i<26;i++) {
            if (visited[i]==0) {
                char ch = (char)('a'+i);
                isValid = getTopoSortOrder(graph,visited,ch,stack);
                if ( !isValid ){
                    return "";
                }
            }
        }
        System.out.println("2");
        

        // Stack ka order print karwao
        StringBuilder sb = new StringBuilder();
        char c;
        boolean addOtherLetters = true;
        fillAllVisited(words,allVisited);
        if(!stack.empty()) {
            fillAllVisited(words,allVisited);
            addOtherLetters = true;
        }
        while(!stack.empty()) {
            c = stack.pop();
            sb.append(c);
            allVisited[c-'a']=1;

        }

        if(addOtherLetters) {
            System.out.println("adding leftover letters");
            for(int i=0;i<26;i++) {
                if(allVisited[i]==0){
                    sb.append((char)(i+'a'));
                    allVisited[i]=1;
                }
            }
        }

        String ans = sb.toString();
        return ans;

    }
}

// Rotten Oranges :
class Pair {
    int row;
    int col;
    Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
class Solution {
    int[] dirRow = {-1,1,0,0};
    int[] dirCol = {0,0,-1,1};

    private void bfsOrangesRotten(int[][] grid,int[][] minutesTaken,int rowLen,int colLen) {
        Queue<Pair> queue = new LinkedList<>();
        for(int i=0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++) {
                if ( grid[i][j]==2) {
                    queue.offer(new Pair(i,j));
                    minutesTaken[i][j]=0;
                }
            }
        }        
        Pair pair;
        int newRow,newCol;
        int lastMinutesTaken;
        while(!queue.isEmpty()){
            pair = queue.poll();
            lastMinutesTaken = minutesTaken[pair.row][pair.col];
            for(int i=0;i<4;i++) {

                newRow = pair.row+dirRow[i];
                newCol = pair.col+dirCol[i];
                
                if(newRow>=0 && newRow<rowLen && newCol>=0 && newCol<colLen 
                            && grid[newRow][newCol]==1 && minutesTaken[newRow][newCol]>lastMinutesTaken+1){
                    grid[newRow][newCol]=2;
                    minutesTaken[newRow][newCol]=lastMinutesTaken+1 ;
                    queue.offer(new Pair(newRow,newCol));
                }
            }
        }
    }

    private int findMinimumTime(int[][] grid,int[][] minutesTaken,int rowLen,int colLen) {
        int minTime = 0;
        for(int i=0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++){
                if(grid[i][j]==1)
                    return -1;
                if(grid[i][j]==2) {
                    minTime = Math.max(minTime,minutesTaken[i][j]);
                }
            }
        }
        return minTime;
    }


    public int orangesRotting(int[][] grid) {
        int rowLen = grid.length;
        int colLen = grid[0].length;

        int[][] minutesTaken = new int[rowLen][colLen];
        for(int i=0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++) {
                minutesTaken[i][j]=Integer.MAX_VALUE;
            }
        }
        bfsOrangesRotten(grid,minutesTaken,rowLen,colLen);

        int ans = findMinimumTime(grid,minutesTaken,rowLen,colLen);
        return ans;
    }
}

// Sorrounded Region 
class Pair {
    int row;
    int col;
    Pair(int row,int col) {
        this.row = row;
        this.col = col;
    }
}

class Solution {
    int[] dirRow = {-1,1,0,0};
    int[] dirCol = {0,0,-1,1};
    private void solveBFS(int[][] visited,char[][] board,int rowLen,int colLen,Queue<Pair> queue) {
        Pair p;
        int newRow,newCol;
        while(!queue.isEmpty()){
            p = queue.poll();
            visited[p.row][p.col]=1;
            for(int i=0;i<4;i++) {
                newRow = p.row+dirRow[i];
                newCol = p.col+dirCol[i];
                if(newRow>=0 && newRow<rowLen && newCol>=0 && newCol<colLen && visited[newRow][newCol]==0 && board[newRow][newCol]=='O'){
                    visited[newRow][newCol]=1;
                    queue.offer(new Pair(newRow,newCol));
                }
            }
        }
        
    }

    private void updateBoard(int[][] visited,char[][] board,int rowLen,int colLen) {
        for(int i=0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++) {
                if(visited[i][j]==0 && board[i][j]=='O'){
                    board[i][j]='X';
                }
            }
        }
    }

    public void solve(char[][] board) {
        int rowLen = board.length;
        int colLen = board[0].length;
        int[][] visited = new int[rowLen][colLen];
        Queue<Pair> queue = new LinkedList<>();
        for(int i=0;i<rowLen;i++) {
            for(int j=0;j<colLen;j++){
                if ( board[i][j] == 'X') {
                    visited[i][j]=2;
                } else if ( board[i][j] == 'O') {
                    visited[i][j]=0;
                    if( i==0 || i==rowLen-1 || j==0 || j==colLen-1) {
                        queue.offer(new Pair(i,j));
                    }
                }
            }
        }

        solveBFS(visited,board,rowLen,colLen,queue);

        updateBoard(visited,board,rowLen,colLen);
    }
}

// Pacific Atlantic Water Flow :
class Reach {
    boolean canReachFromPacific;
    boolean canReachFromAtlantic;

    Reach(boolean canReachFromPacific, boolean canReachFromAtlantic) {
        this.canReachFromPacific = canReachFromPacific;
        this.canReachFromAtlantic = canReachFromAtlantic;
    }
}

class Pair {
    int row;
    int col;

    Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

class Solution {

    int[] dirRow = { -1, 1, 0, 0 };
    int[] dirCol = { 0, 0, -1, 1 };

    // heights,visited,reach,rowLen,colLen,Ocean.PACIFIC,queue
    private void bfs(int[][] heights, boolean[][] visited, Reach[][] reach, int rowLen, int colLen, boolean isPacific,
            Queue<Pair> queue) {
        int newRow,newCol;
        Pair p;
        int lastHeight;
        while(!queue.isEmpty()) {
            p = queue.poll();
            visited[p.row][p.col] = true;
            if(isPacific) {
                reach[p.row][p.col].canReachFromPacific = true;
            }else {
                reach[p.row][p.col].canReachFromAtlantic = true;
            }
            lastHeight = heights[p.row][p.col];
            for(int i=0;i<4;i++) {
                newRow = p.row+dirRow[i];
                newCol = p.col+dirCol[i];


                if(newRow>=0 && newRow<rowLen && newCol>=0 && newCol<colLen && visited[newRow][newCol]==false && heights[newRow][newCol]>=lastHeight) {
                    if(isPacific) {
                        reach[newRow][newCol].canReachFromPacific = true;
                    }else {
                        reach[newRow][newCol].canReachFromAtlantic = true;
                    }
                    visited[newRow][newCol]=true;
                    queue.offer(new Pair(newRow,newCol));                  
                }
            }
        }
    }

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int rowLen = heights.length;
        int colLen = heights[0].length;
        Reach[][] reach = new Reach[rowLen][colLen];
        boolean[][] visited = new boolean[rowLen][colLen];
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                reach[i][j] = new Reach(false, false);
                visited[i][j] = false;
            }
        }
        Queue<Pair> queue = new LinkedList<>();
        int row;
        int col;
        row = 0;
        for (col = 0; col < colLen; col++) {
            reach[row][col].canReachFromPacific = true;
            visited[row][col] = true;
            queue.offer(new Pair(row, col));
        }
        col = 0;
        for (row = 1; row < rowLen; row++) {
            reach[row][col].canReachFromPacific = true;
            visited[row][col] = true;
            queue.offer(new Pair(row, col));
        }
        bfs(heights, visited, reach, rowLen, colLen, true, queue);
        queue.clear();

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                visited[i][j] = false;
            }
        }
        row = rowLen - 1;
        for (col = 0; col < colLen; col++) {
            reach[row][col].canReachFromAtlantic = true;
            visited[row][col] = true;
            queue.offer(new Pair(row, col));
        }
        col = colLen - 1;
        for (row = 0; row < rowLen - 1; row++) {
            reach[row][col].canReachFromAtlantic = true;
            visited[row][col] = true;
            queue.offer(new Pair(row, col));
        }
        bfs(heights, visited, reach, rowLen, colLen, false, queue);
        queue.clear();
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if(reach[i][j].canReachFromPacific && reach[i][j].canReachFromAtlantic) {
                    List<Integer> lst = new ArrayList<>();
                    lst.add(i);
                    lst.add(j);
                    ans.add(lst);
                }
            }
        }
        return ans;
    }
}


// Max area of Island :

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

// Graph is Valid Tree 
class Solution {

    private void initAndPopulateGraph(int n, int[][] edges,ArrayList<Integer>[] graph) {
        for(int i=0;i<n;i++) {
            graph[i] = new ArrayList<Integer>();
        }
        int len = edges.length;
        int u,v;
        for(int i=0;i<len;i++){
            u = edges[i][0];
            v = edges[i][1];
            graph[u].add(v);
            graph[v].add(u);
        }
    }

    private boolean cycleExist(ArrayList<Integer>[] graph ,int[] visited ,int source,int parent) {
        visited[source] = 1;

        ArrayList<Integer> adjacentNodes = graph[source];
        int adjacentNode;
        for(int i=0;i<adjacentNodes.size();i++) {
            adjacentNode = adjacentNodes.get(i);
            if (visited[adjacentNode]==0){
                if(cycleExist(graph ,visited ,adjacentNode,source)){
                    return true;
                }
            }else if (adjacentNode!=parent) {
                return true;
            }
        }
        return false;
    }

    public boolean validTree(int n, int[][] edges) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        initAndPopulateGraph(n,edges,graph);
        int[] visited = new int[n];
        Arrays.fill(visited,0);
        if(cycleExist(graph,visited,0,-1)) {
            return false;
        }
        for(int i=0;i<n;i++) {
            if(visited[i]==0){
                return false;
            }
        }
        return true;
    }
}

// Number of connected Components 

class Solution {

    private void initAndPopulateGraph(int n, int[][] edges,ArrayList<Integer>[] graph) {
        for(int i=0;i<n;i++) {
            graph[i] = new ArrayList<Integer>();
        }
        int len = edges.length;
        int u,v;
        for(int i=0;i<len;i++){
            u = edges[i][0];
            v = edges[i][1];
            graph[u].add(v);
            graph[v].add(u);
        }
    }

    private void dfs(ArrayList<Integer>[] graph,int[] visited,int source,int parent) {
        visited[source] = 1;
        ArrayList<Integer> adjacentNodes = graph[source];
        int adjacentNode;
        for(int i=0;i<adjacentNodes.size();i++) {
            adjacentNode = adjacentNodes.get(i);
            if (visited[adjacentNode]==0 && adjacentNode!=parent){
                dfs(graph ,visited ,adjacentNode,source);
            }
        }        
    }


    public int countComponents(int n, int[][] edges) {
        ArrayList<Integer>[] graph = new ArrayList[n];
        initAndPopulateGraph(n,edges,graph);
        int[] visited = new int[n];
        Arrays.fill(visited,0);
        int componentCounts = 0;

        for(int i=0;i<n;i++) {
            if(visited[i]==0){
                dfs(graph,visited,i,-1);
                componentCounts++;
            }
        }
        return componentCounts;        
    }
}


// Bipartite Graph 

    private boolean isBipartite(int source,int[][] graph,int[] visited,int color) {
        visited[source]=color;
        int adjacentNodes;

        for(int i=0;i<graph[source].length;i++) {
            adjacentNodes = graph[source][i];
            if(visited[adjacentNodes]==color) {
                return false;
            }else if (visited[adjacentNodes]==0) {
                if ( !isBipartite(adjacentNodes,graph,visited,3-color) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBipartite(int[][] graph) {
        int rowLen;
        rowLen = graph.length;
        int[] visited = new int[rowLen];
        for(int i=0;i<rowLen;i++) {
            if (visited[i]==0) {
                if(!isBipartite(i,graph,visited,1)) {
                    return false;
                }
            }
        }
        return true;
    }
}

// Network Delay Time :
class Pair {
    int node;
    int weight;
    Pair(int node,int weight) {
        this.node = node;
        this.weight = weight;
    }
}

class PairWeightComparator implements Comparator<Pair> {
    @Override
    public int compare(Pair a, Pair b) {
        if  (a.weight < b.weight ) {
            return -1;
        } else if(a.weight > b.weight ) {
            return 1;
        }
        return 0;
    }
}

class Solution {
    
    private void initGraph(ArrayList<Pair> [] graph,int n){
        for(int i=0;i<n;i++){
            graph[i] = new ArrayList<>();
        }        
    }
    private void populateGraph(ArrayList<Pair> [] graph,int[][] time){
        int len = time.length;
        int u,v,w;
        for(int i=0;i<len;i++){
            u = time[i][0]-1;
            v = time[i][1]-1;
            w = time[i][2];
            graph[u].add(new Pair(v,w));
        }
    }

    private void dijkstras(ArrayList<Pair>[] graph,int[] dist,int source,int n){
        dist[source] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>(new PairWeightComparator());
        pq.offer(new Pair(source, 0));
        Pair curr,next;
        int currNode,currDistance;
        int nextNode,edgeWeight;
        while(!pq.isEmpty()){
            curr = pq.poll();
            currNode = curr.node;
            currDistance = curr.weight;

            if (currDistance>dist[currNode]) {
                continue;
            }

            for(int i=0;i<graph[currNode].size();i++){
                next = graph[currNode].get(i);
                nextNode = next.node;
                edgeWeight = next.weight;

                if (dist[currNode] != Integer.MAX_VALUE && dist[currNode] + edgeWeight < dist[nextNode]) {
                    dist[nextNode] = dist[currNode] + edgeWeight;
                    pq.offer(new Pair(nextNode, dist[nextNode]));
                }                
            }
        }
    }

    private int findMaxDist(int[] dist) {
        int maxi = Integer.MIN_VALUE;
        for(int i=0;i<dist.length;i++) {
            maxi = Math.max(maxi,dist[i]);
        }
        return maxi;
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k-1] = 0;
        ArrayList<Pair>[] graph = new ArrayList[n];
        initGraph(graph,n);
        populateGraph(graph,times);
        dijkstras(graph,dist,k-1,n);
        int maxTime = findMaxDist(dist);
        if ( maxTime == Integer.MAX_VALUE){
            return -1;
        }
        return maxTime;
    }
}


