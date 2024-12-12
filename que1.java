Time Complexity (TC): O(N + E)
Space Complexity (SC): O(N + E)

class Solution {
    List<List<Integer>> result;  // Stores critical connections
    HashMap<Integer, List<Integer>> map;  // Adjacency list representation of the graph
    int time;  // Time counter for discovery time of nodes
    
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        this.result = new ArrayList<>();  // Initialize result list
        this.time = 0;  // Initialize time counter
        
        // Initialize adjacency list
        this.map = new HashMap<>();
        for (List<Integer> li : connections) {
            if (!map.containsKey(li.get(0))) map.put(li.get(0), new ArrayList<>());
            if (!map.containsKey(li.get(1))) map.put(li.get(1), new ArrayList<>());
            map.get(li.get(1)).add(li.get(0));  // Add edge to adjacency list
            map.get(li.get(0)).add(li.get(1));
        }
        
        int[] disc = new int[n];  // Discovery time of nodes
        int[] low = new int[n];  // Lowest point reachable
        Arrays.fill(disc, -1);  // Initialize discovery times to -1 (unvisited)
        Arrays.fill(low, -1);  // Initialize low values to -1
        
        // Perform DFS for each node
        for (int i = 0; i < n; i++) {
            dfs(i, -1, disc, low);  // Start DFS from each node
        }

        return result;  // Return list of critical connections
    }
    
    private void dfs(int V, int U, int[] disc, int[] low) {
        // If already visited, return
        if (disc[V] != -1) return;
        
        disc[V] = time;  // Set discovery time
        low[V] = time;  // Set low value
        time++;  // Increment time
        
        // Visit all adjacent nodes
        for (int node : map.get(V)) {
            if (node == U) continue;  // Skip the parent node
            
            dfs(node, V, disc, low);  // DFS for the next node
            
            // Check if the connection is critical
            if (low[node] > disc[V]) {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(node);
                list.add(V);
                result.add(list);  // Add critical connection to result
            }
            
            // Update the low value of V
            low[V] = Math.min(low[node], low[V]);
        }
    }
}
