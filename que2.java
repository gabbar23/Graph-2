TC - O(V+E)
SC - O(V+E)
class Solution {
    private void dfs(int V, int[] colorGrp, int[][] graph, int color) {
        // If the node is already colored, return
        if (colorGrp[V] != -1) return;
        // Set the color of the current node
        colorGrp[V] = color;

        // Traverse its neighbors
        for (int i = 0; i < graph[V].length; i++) {
            // If there is an edge between V and i, perform DFS on the neighbor
            if (graph[V][i] == 1) {
                dfs(i, colorGrp, graph, color);
            }
        }
    }

    public int minMalwareSpread(int[][] graph, int[] initial) {
        int nodes = graph.length;
        int[] colorGrp = new int[nodes];
        Arrays.fill(colorGrp, -1);  // Initialize color group array with -1
        int cl = 0;  // Variable to track the color group

        // Assign color groups using DFS
        for (int i = 0; i < nodes; i++) {
            dfs(i, colorGrp, graph, cl);
            cl++;
        }

        int[] groups = new int[cl];  // Array to count nodes in each color group
        for (int i = 0; i < nodes; i++) {
            int color = colorGrp[i];
            groups[color]++;
        }

        int[] infected = new int[nodes];  // Array to count infected nodes in each color group
        for (int i : initial) {
            int color = colorGrp[i];
            infected[color]++;
        }

        int result = Integer.MAX_VALUE;  // Variable to store the result node
        for (int i : initial) {
            int color = colorGrp[i];
            int count = infected[color];  // Total count of infected nodes in this color group

            // Check if there is only one infected node in this group
            if (count == 1) {
                // Select the smallest index if result is not set
                if (result == Integer.MAX_VALUE) {
                    result = i;
                } 
                // If group sizes are equal, choose the smaller index node
                else if (groups[color] == groups[colorGrp[result]] && i < result) {
                    result = i;
                } 
                // Otherwise, choose the node from the larger group
                else if (groups[color] > groups[colorGrp[result]]) {
                    result = i;
                }
            }
        }

        // If no single-node infected groups are found, return the minimum index
        if (result == Integer.MAX_VALUE) {
            for (int i : initial) {
                result = Math.min(result, i);
            }
        }

        return result;
    }
}
