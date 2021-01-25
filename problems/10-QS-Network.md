# QS Network

https://vjudge.net/problem/FZU-1096

## 题目

想用路由器把N个地方连接起来，给定N个路由器安装点，接下来的N个数字表示在某地安装一个路由器的费用。再接下来N*N的矩阵表示两地进行连接时的花费。每个路由器只能用一次，例如在AB之间连接了，就需要分别在A和B购买一个路由器，然后再把他们连起来，然后再想在AC之间连接，A就必须要再买一个路由器，不能重复使用同一个路由器，求他们的最小花费

### Input

T组输入。输入N代表需要N地互联，下面一行有N个数字，代表在第i个地方安装一个路由器的费用。下面N行为一个矩阵，代表第i个地方到第j个地方连接起来所需费用。 保证所有数字不超过1000。

### Output

每组输出一个结果

### Sample Input

```
1
3
10 20 30
0 100 200
100 0 300
200 300 0
```

### Sample Output

```
370
```

## 题解

最小生成树, Prime法或Kruskal法均可.

## 代码

### Kruskal法:

```java
import java.util.*;

public class Main {
    private static short find(short[] arr, short index) {
        while (arr[index] != index) index = arr[index];
        return index;
    }
    public static void main(String[] args) {
        final Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        while (all-- > 0) {
            int len = cin.nextInt();
            if (len <= 0) {
                System.out.println(0);
                continue;
            }
            short[] points = new short[len];
            for (int i = 0; i < len; i++) points[i] = cin.nextShort();
            short[][] edges = new short[len * (len - 1) / 2][3];
            int t = 0, count = 1;
            for (short i = 0; i < len; i++) for (short j = 0; j < len; j++) {
                short it = cin.nextShort();
                if (i >= j) continue;
                short[] arr = edges[t++];
                arr[0] = i;
                arr[1] = j;
                arr[2] = (short) (it + points[i] + points[j]);
            }
            Arrays.sort(edges, new Comparator<short[]>() {
                @Override
                public int compare(short[] o1, short[] o2) {
                    return o1[2] - o2[2];
                }
            });
            long ans = 0;
            t = 0;
            for (short i = 0; i < len; i++) points[i] = i;
            while (count != len) {
                short[] edge = edges[t++];
                short sa = find(points, edge[0]), sb = find(points, edge[1]);
                if (sa == sb) continue;
                ans += edge[2];
                points[sb] = sa;
                count++;
            }
            System.out.println(ans);
        }
    }
}
```

### Prime法:

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        while (all-- > 0) {
            int len = cin.nextInt();
            int[] points = new int[len];
            int[] distances = new int[len];
            for (int i = 0; i < len; i++) {
                points[i] = cin.nextInt();
                distances[i] = Integer.MAX_VALUE;
            }
            int[][] arr = new int[len][len];
            for (int i = 0; i < len; i++) for (int j = 0; j < len; j++) arr[i][j] = cin.nextInt() + points[i] + points[j];
            int ans = 0;
            boolean[] vis = new boolean[len];
            for (int i = 0; i < len; i++) {
                int t = -1;
                for (int j = 0; j < len; j++) {
                    if (!vis[j] && (t == -1 || distances[t] > distances[j])) t = j;
                }
                vis[t] = true;
                if (i != 0) ans += distances[t];
                for (int j = 0; j < len; j++) distances[j] = Math.min(distances[j], arr[t][j]);
            }
            System.out.println(ans);
        }
    }
}
```
