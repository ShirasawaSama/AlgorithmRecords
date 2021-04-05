// 注释的地方用于判断负环
public class spfa { // 求最短路
    // edge: new int[] { linkedPoint, weight };
    public static int[] spfa(int firstPoint, ArrayList<int[]>[] edges) {
        int len = edges.length;
        boolean[] visited = new boolean[len];
        int[] distances = new int[len] /* , times = new int[len] */;
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(firstPoint);
        visited[firstPoint] = true;
        for (int i = 1; i < len; i++) distances[i] = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int cur = queue.pop();
            // times[cur]++;
            visited[cur] = false;
            int dis = distances[cur];
            if (edges[cur] == null) continue;
            for (int[] it : edges[cur]) {
                int next = it[0], tmp = dis + it[1], tmp1 = distances[next];
                if (tmp >= tmp1) continue;
                distances[next] = tmp;
                if (visited[next] /* || times[next] > len */) continue;
                visited[next] = true;
                queue.add(next);
            }
        }
        return distances;
    }
}
