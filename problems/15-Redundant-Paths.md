# Redundant Paths

https://vjudge.net/problem/POJ-3177#author=haomie

## 题目

有N个牧场（牧场编号1~N），Bessie 要从一个牧场到另一个牧场，要求至少要有2条独立的路可以走。

现已有M条路，求至少要新建多少条路，使得任何两个牧场之间至少有两条独立的路。

两条独立的路是指：没有公共边的路，但可以经过同一个中间顶点。

### Input

第1行：给出N，M，分别表示总共有N个牧场，和M条边。1<=N<=5000，N-1<=M<=10000。

第2~M+1行：每行给出两个数，表示两个牧场间有一条边。

### Output

输出需要新建道路的数量。

```
Sample Input
7 7
1 2
2 3
3 4
2 5
4 5
5 6
5 7
```

### Sample Output

```
2
```

### Hint

Explanation of the sample:

One visualization of the paths is:

```
   1   2   3
   +---+---+  
       |   |
       |   |
 6 +---+---+ 4
      / 5
     / 
    / 
 7 +
```

Building new paths from 1 to 6 and from 4 to 7 satisfies the conditions.

```
   1   2   3
   +---+---+  
   :   |   |
   :   |   |
 6 +---+---+ 4
      / 5  :
     /     :
    /      :
 7 + - - - - 
```

Check some of the routes:

- 1 – 2: 1 –> 2 and 1 –> 6 –> 5 –> 2
- 1 – 4: 1 –> 2 –> 3 –> 4 and 1 –> 6 –> 5 –> 4
- 3 – 7: 3 –> 4 –> 7 and 3 –> 2 –> 5 –> 7

Every pair of fields is, in fact, connected by two routes.

It's possible that adding some other path will also solve the problem (like one from 6 to 7). Adding two paths, however, is the minimum.

## 题意

给一个无向图, 如果需要使每个点之间至少有两条不同的边可以到达, 那么最少需要添加多少条边.

## 题解

首先我们可以对图进行边连通分量缩点 (tarjan法), 缩点后图就会变成一颗树, 代表任意2点之间的路径是唯一的.

这时候题目转化为添加最少的边使任意2点的路径至少有2条.

结论: 对有n个入度为1的点的树，至少需要 (n + 1) / 2 的边使其变成一个边连通分量.

> 注: 本题有坑点, 会出现重边!

## 代码

```java
import java.util.*;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt(), m = cin.nextInt(), ans = 0;
        edges = new HashSet[n + 1];
        dfn = new int[n + 1];
        low = new int[n + 1];
        while (m-- > 0) {
            int u = cin.nextInt(),  v =cin.nextInt();
            addConnection(u, v);
            addConnection(v, u);
        }
        for (int i = 1; i <= n; i++) if (dfn[i] == 0) tarjan(i, i);
        int[] arr = new int[5050];
        for (int i = 1; i <= n; i++) if (edges[i] != null) for (int j : edges[i]) {
            if (low[i] != low[j]) arr[low[j]]++;
        }
        for (int i = 1; i <= n; i++) if (arr[i] == 1) ans++;
        System.out.println((ans + 1) / 2);
    }

    private static int index;
    private static int[] dfn, low;
    private static HashSet<Integer>[] edges;
    private static void addConnection(int u, int v) {
        HashSet<Integer> it = edges[u];
        if (it == null) edges[u] = it = new HashSet<Integer>();
        it.add(v);
    }
    private static void tarjan(int u, int pre){
        low[u] = dfn[u]= ++index;
        if (edges[u] != null) for (int i : edges[u]) {
            if (dfn[i] == 0) {
                tarjan(i, u);
                low[u] = Math.min(low[u], low[i]);
            } else if (i != pre) low[u] = Math.min(low[u], dfn[i]);
        }
    }
}
```
