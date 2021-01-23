# Invitation Cards

https://vjudge.net/problem/POJ-1511#author=5120187418

## 题目

炎龙侠在为他的科幻小说小说中的世界，设计一幅交通图

大部分行星之间都建有传送门，但是传送门只能单向通过，而且，因为技术条件的差异，以及设备的老化，每个传送门，传送需要的时间基本都不相同

他在小说中设计了一个情节，主角梅川瓦子得到了情报，有外星人要同时对她所在势力的n颗行星发动攻击，她在编号为1的行星上

由于超距通讯系统的损坏，她只能派出一架无人机，穿过传送门，前去通知编号2~n的行星

有关外星人的资料很多，无人机的存储器无法存下，于是她准备了n-1块硬盘。无人机每次可以携带一块硬盘（根据sy的建议，硬盘应该绑在无人机的腿上），前去通知完一颗行星后，它要回到梅川瓦子身边，再带上新的硬盘，通知下一个行星

时间紧迫，梅川瓦子希望你能帮帮她计算一下，无人机前去通知n-1颗行星，再回到它身边，所花费的最短时间的总和

### Input

输入由T个案例组成。输入的第一行只包含正整数T

接下来是N和M，1 <= N,M <= 1000000，表示N个行星和连接N个行星的M个单向传送门

然后有M行，每行包括三个值U,V,W，表示从行星U到行星V的传送门，传送需要的时间W

### Output

对于每个案例，打印一行，表示花费时间总和的最小值

### Sample Input

```
2
2 2
1 2 6
2 1 4
3 5
1 2 8
2 1 16
1 3 50
2 3 41
3 2 47
```

### Sample Output

```
10
136
```

### Hints

注意不要使用万能头文件，不要使用cin和cout

## 题意

给一个有权值的单向图, 问第一点到每一点的最短距离与每一点到第一点的最短距离之和

## 题解

spfa + 快速读入

- 建立边记录的时候，假如是a->b这样直接搜索, spfa的 `distances` 数组就是从第一点到每一点的最短距离.
- 建立边记录的时候，假如是b->a这样反向搜索, spfa的 `distances` 数组就是从每一点到第一点的最短距离.

> 注意: 由于本题输入数据量大得离谱, 需要使用快速读入, 否则会超时

## 代码

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@SuppressWarnings("unchecked")
public class Main {
    private final static class FastScanner{
        BufferedReader br;
        StringTokenizer st;
        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in),16384);
            eat("");
        }
        public void eat(String s) {
            st = new StringTokenizer(s);
        }
        public String nextLine() {
            try { return br.readLine(); } catch (Exception e) { return null; }
        }
        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String s = nextLine();
                if (s == null) return false;
                eat(s);
            }
            return true;
        }
        public String next() {
            hasNext();
            return st.nextToken();
        }
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
    private static int[] spfa(ArrayList<int[]>[] edges) {
        int len = edges.length;
        boolean[] visited = new boolean[len];
        int[] distances = new int[len];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
        visited[0] = true;
        for (int i = 1; i < len; i++) distances[i] = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int cur = queue.pop();
            visited[cur] = false;
            int dis = distances[cur];
            if (edges[cur] == null) continue;
            for (int[] it : edges[cur]) {
                int next = it[0], tmp = dis + it[1], tmp1 = distances[next];
                if (tmp >= tmp1) continue;
                distances[next] = tmp;
                if (visited[next]) continue;
                visited[next] = true;
                queue.add(next);
            }
        }
        return distances;
    }
    static {
        FastScanner cin = new FastScanner();
        int all = cin.nextInt();
        while (all-- > 0) {
            int len = cin.nextInt(), m = cin.nextInt();
            ArrayList<int[]>[] edges = new ArrayList[len], reversedEdges = new ArrayList[len];
            while (m-- > 0) {
                int a = cin.nextInt() - 1, b = cin.nextInt() - 1, time = cin.nextInt();
                ArrayList<int[]> it = edges[a];
                if (it == null) edges[a] = it = new ArrayList<int[]>();
                it.add(new int[] { b, time });
                it = reversedEdges[b];
                if (it == null) reversedEdges[b] = it = new ArrayList<int[]>();
                it.add(new int[] { a, time });
            }
            int[] firstToOthers = spfa(edges), othersToFirst = spfa(reversedEdges);
            long ans = 0;
            for (int i = 0; i < len; i++) ans += firstToOthers[i] + othersToFirst[i];
            System.out.println(ans);
        }
    }
    public static void main(String[] args) { }
}
```
