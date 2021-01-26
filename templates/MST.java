public class MST {
    private static int find(int[] arr, int index) {
        int tmp = index;
        while (arr[tmp] != tmp) tmp = arr[tmp];
        // 压缩路径
        int i = index, j;
        while (i != tmp){
            j = arr[i];
            arr[i] = tmp;
            i = j;
        }
        return tmp;
    }

    // edges: Array<[point1, point2, weight]>
    public static void kruskal(int[][] edges, int pointCount) {
        Arrays.sort(edges, Comparator.comparingInt(o -> o[2]));
        int ans = 0, t = 0, count = 1;
        int[] points = new int[pointCount];
        for (int i = 0; i < pointCount; i++) points[i] = i;
        while (count != pointCount) {
            int[] edge = edges[t++];
            int sa = find(points, edge[0]), sb = find(points, edge[1]);
            if (sa == sb) continue;
            ans += edge[2];
            points[sb] = sa;
            count++;
        }
        System.out.println(ans);
    }

    public static void Prim1(int[][] matrix) {
        int len = matrix.length;
        int[] costs = new int[len];
        costs[0] = -1; // 从顶点0开始构造最小生成树
        System.arraycopy(matrix[0], 1, costs, 1, len - 1); // for (int i = 1; i < len; i++) costs[i] = matrix[0][i];
        int ans = 0;
        for (int i = 1; i < len; i++) { // 把其他 n-1 个顶点扩展到生成树当中
            int min = Integer.MAX_VALUE, k = 0;
            for (int j = 0; j < len; j++) { // 找到当前可用的权值最小的边
                if(costs[j] != -1 && costs[j] < min) {
                    k = j;
                    min = costs[j];
                }
            }
            ans += min;
            costs[k] = -1; // 把顶点k扩展进来
            for (int t = 0; t < len; t++) if (matrix[k][t] < costs[t]) costs[i] = matrix[k][i];
        }
        System.out.println(ans);
    }
}
