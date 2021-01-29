public final class Edmonds {
    // private int pos = 0;   pos减去不包含虚根的边就是1~N种情况
    // edges: Array<[point1, point2, weight]>
    private static int edmonds(int[][] edges, int pointCount, int root) {
        // pos = 0;
        int ans = 0;
        int[] in = new int[pointCount], pre = new int[pointCount], id = new int[pointCount], vis = new int[pointCount];
        while (true) {
            Arrays.fill(in, Integer.MAX_VALUE);
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1];
                if(edge[2] < in[v] && u != v) {
                    pre[v] = u;
                    in[v] = edge[2];
                    // if (u == root) pos = i;
                }
            }
            for (int i = 0; i < pointCount; i++) {
                if (i != root && in[i] == Integer.MAX_VALUE) return -1;
            }
            int count = 0;
            Arrays.fill(id, -1);
            Arrays.fill(vis, -1);
            in[root] = 0;
            for (int i = 0; i < pointCount; i++) {
                ans += in[i]; 
                int v = i;
                while (vis[v] != i && id[v] == -1 && v != root) {
                    vis[v] = i;
                    v = pre[v];
                }
                if (v != root && id[v] == -1) {
                    for (int u = pre[v]; u != v; u = pre[u]) id[u] = count;
                    id[v] = count++;
                }
            }
            if (count == 0) break;
            for (int i = 0; i < pointCount; i++) if(id[i] == -1) id[i] = count++;
            for (int[] edge : edges) {
                int v = edge[1];
                edge[0] = id[edge[0]];
                edge[1] = id[edge[1]];
                if (edge[0] != edge[1]) edge[2] -= in[v];
            }
            pointCount = count;
            root = id[root];
        }
        return ans;
    }
}
