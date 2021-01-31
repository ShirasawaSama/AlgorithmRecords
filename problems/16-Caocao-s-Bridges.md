# Caocao's Bridges

https://vjudge.net/problem/HDU-4738#author=cqbzguomao

## 题目

在赤壁之战中，曹操被诸葛亮和周瑜击败。但他不会放弃。曹操的军队仍然不善于水战，所以他提出了另一个想法。

他在长江建造了许多岛屿，在这些岛屿的基础上，曹操的军队很容易攻击周瑜的部队。曹操还建造了连接岛屿的桥梁。

如果所有岛屿都通过桥梁相连，那么曹操的军队可以在这些岛屿中非常方便地部署。

周瑜无法忍受，所以他想要摧毁一些曹操的桥梁，这样一个或多个岛屿就会与其他岛屿分开。

但周瑜只有一枚由诸葛亮留下的炸弹，所以他只能摧毁一座桥。周瑜必须派人携带炸弹来摧毁这座桥。

桥上可能有守卫。轰炸队的士兵数量不能低于桥梁的守卫数量，否则任务就会失败。

请弄清楚周瑜至少需要多少士兵。

### Input

测试用例不超过12个。

在每个测试用例中:

第一行包含两个整数N和M，意味着有N个岛和M个桥。所有岛都从1到N编号。（2 <= N <= 1000，0 <M <= N²）

接下来的M行描述了M个桥。每条线包含三个整数U，V和W，意味着有一个连接岛U和岛V的桥，并且在该桥上有W守卫。（U≠V且0 <= W <= 10,000）

输入以N = 0且M = 0结束。

### Output

对于每个测试用例，输出周瑜完成任务所需的最少士兵数量。如果周瑜无法成功，请输出-1。

### Sample Input

```
3 3
1 2 7
2 3 4
3 1 4
3 2
1 2 7
2 3 4
0 0
```

### Sample Output

```
-1
4
```

## 题意

给定一张无向图，求其中权值最小的一座桥.

## 题解

首先使用tarjan法求图种的桥, 如果是桥的话就直接对比是否为最小士兵数量的桥, 全部对比完成之后输出最小的士兵数量就可以了.

## 代码

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        boolean flag = false;
        while (true) {
            int n = cin.nextInt(), m = cin.nextInt(), count = 0;
            if (n == 0 && m == 0) return;
            init();
            if (flag) init(); else flag = true;
            while (m-- > 0) {
                int u = cin.nextInt(), v = cin.nextInt(), value = cin.nextInt();
                addEdge(u, v, value);
                addEdge(v, u, value);
            }
            for (int i = 1; i <= n; i++) if (dfn[i] == 0) {
                tarjan(i, 0);
                count++;
            }
            System.out.println(count == 1
                ? ans == Integer.MAX_VALUE ? -1 : ans == 0 ? 1 : ans
                : 0);
        }
    }

    private static final int MAX_N = 1005, MAX_2N = 2 * MAX_N * MAX_N;
    private static int index = 1, len = 1, ans = Integer.MAX_VALUE;
    private final static int[] dfn = new int[MAX_N], low = new int[MAX_N], pointToEdges = new int[MAX_2N];
    private final static int[][] edges = new int[MAX_2N][3];
    private static void init() {
        index = len = 1;
        ans = Integer.MAX_VALUE;
        for (int i = 0; i < MAX_2N; i++) {
            if (i < MAX_N) dfn[i] = low[i] = 0;
            pointToEdges[i] = 0;
        }
    }
    public static void addEdge(int u, int v, int value) {
        int[] edge = edges[++len];
        edge[0] = v;
        edge[1] = value;
        edge[2] = pointToEdges[u];
        pointToEdges[u] = len;
    }
    public static void tarjan(int u, int pre){
        low[u] = dfn[u]= ++index;
        for (int j = pointToEdges[u]; j != 0; j = edges[j][2]) {
            int v = edges[j][0];
            if (dfn[v] == 0) {
                tarjan(v, j);
                if (low[u] > low[v]) low[u] = low[v];
                if (dfn[u] < low[v] && ans > edges[j][1]) ans = edges[j][1];
            } else if ((j ^ 1) != pre && dfn[v] < low[u]) low[u] = dfn[v];
        }
    }
}
```

> 注: 由于OJ开不出数组所以会导致内存超限
