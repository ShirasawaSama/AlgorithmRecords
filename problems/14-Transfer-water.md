# Transfer water

https://vjudge.net/problem/HDU-4009

## 题目

XiaoA lives in a village. Last year flood rained the village. So they decide to move the whole village to the mountain nearby this year.

There is no spring in the mountain, so each household could only dig a well or build a water line from other household.

If the household decide to dig a well, the money for the well is the height of their house multiplies X dollar per meter.

If the household decide to build a water line from other household, and if the height of which supply water is not lower than the one which get water, the money of one water line is the Manhattan distance of the two households multiplies Y dollar per meter.

Or if the height of which supply water is lower than the one which get water, a water pump is needed except the water line. Z dollar should be paid for one water pump.

In addition, therelation of the households must be considered. Some households may do not allow some other households build a water line from there house.

Now given the 3‐dimensional position (a, b, c) of every household the c of which means height, can you calculate the minimal money the whole village need so that every household has water, or tell the leader if it can’t be done.

### Input

Multiple cases.

First line of each case contains 4 integers n (1<=n<=1000), the number of the households, X (1<=X<=1000), Y (1<=Y<=1000), Z (1<=Z<=1000).

Each of the next n lines contains 3 integers a, b, c means the position of the i‐th households, none of them will exceeded 1000.

Then next n lines describe the relation between the households. The n+i+1‐th line describes the relation of the i‐th household. The line will begin with an integer k, and the next k integers are the household numbers that can build a water line from the i‐th household.

If n=X=Y=Z=0, the input ends, and no output for that.

### Output

One integer in one line for each case, the minimal money the whole village need so that every household has water. If the plan does not exist, print `poor XiaoA` in one line.

### Sample Input

```
2 10 20 30
1 3 2
2 4 1
1 2
2 1 2
0 0 0 0
```

### Sample Output

```
30
```

### Hint

In 3‐dimensional space Manhattan distanc of point A (x1,  y1,  z1) and B(x2, y2, z2) is |x2‐x1|+|y2‐y1|+|z2‐z1|.

## 题意

在三维地图上有n个村庄, 然后要给这n个村庄通水, 可以有两个选择, 一个是自己打井, 花费为村庄的高度乘X, 还有就是可以和其他村庄连管道:

1. 如果被连接的村庄比连接的村庄低或相等, 花费就是曼哈顿距离*Y(曼哈顿距离就是题目下方的Hint).
2. 如果被连接的村庄比连接的村庄高(水往高处流), 就需要花费Z来加一个水泵.

刚开始所有村庄都没有井和管道.

输入格式是n,X,Y,Z, 然后n行输入第i个村庄的三维坐标, 接下来的n行的第一个数k表示后面有k个村庄要被第i个村庄连接.

## 题解

首先由于如果一个村庄不和其他村庄相连可以自己打井, 所以无论如何都可以通水, 即无论如何也不需要输出 `poor XiaoA`.

然后可以设置一个超级点 _(也称虚根)_, 同时设置其id为0. 然后将 **打井** 操作转换为某个要打井的村庄与这个超级点相连, 权值为打井的花费.

接着进行连边操作, 将接下来可以连接的管道设置为边, 也包含权值.

最后将所有的边使用 **Edmonds算法** _(也称朱刘算法)_ 求出最小生成图, 然后输出权值即可.

> 注意: 本题需要使用快速读入, 否则会超时!

## 代码

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            int n = cin.nextInt(), x = cin.nextInt(), y = cin.nextInt(), z = cin.nextInt();
            if (n == 0 && x == 0 && y == 0 && z == 0) return;
            int[][] houses = new int[n + 1][3];
            for (int i = 1; i <= n; i++) {
                int[] it = houses[i];
                it[0] = cin.nextInt();
                it[1] = cin.nextInt();
                it[2] = cin.nextInt();
            }
            ArrayList<int[]> list = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                final int[] a = houses[i];
                list.add(new int[] { 0, i, a[2] * x });
                int m = cin.nextInt();
                while (m-- > 0){
                    int it = cin.nextInt();
                    if (i == it) continue;
                    final int[] b = houses[it];
                    int w = (Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) + Math.abs(a[2] - b[2])) * y;
                    if (a[2] < b[2]) w += z;
                    list.add(new int[] { i , it, w });
                }
            }
            System.out.println(edmonds(list.toArray(new int[list.size()][]), n + 1, 0));
        }
    }

    // edges: Array<[point1, point2, weight]>
    private static int edmonds(int[][] edges, int pointCount, int root) {
        int ans = 0;
        int[] in = new int[pointCount], // 最小入边权
                pre = new int[pointCount], // 其前面的点（该边的起点）
                id = new int[pointCount],
                vis = new int[pointCount];
        while (true) {
            Arrays.fill(in, Integer.MAX_VALUE);
            // 1. 最短弧集合E0
            for (int[] edge : edges) {
                int u = edge[0], v = edge[1];
                if(edge[2] < in[v] && u != v){ // 顶点v有更小的入边, 记录下来, 更新操作, u!=v是为了确保缩点之后, 我们的环将会变成点的形式
                    pre[v] = u; // 节点u指向v
                    in[v] = edge[2]; // 最小入边
                }
            }
            // 2. 检查E0
            for (int i = 0; i < pointCount; i++) { // 判断是否存在最小树形图
                if (i != root && in[i] == Integer.MAX_VALUE) return -1; // 除了根节点以外, 有点没有入边, 则根本无法抵达它, 说明是独立的点, 一定不能构成树形图
            }
            // 3. 收缩图中的有向环
            int count = 0; // 接下来要去求环，用以记录环的个数  找环开始
            Arrays.fill(id, -1);
            Arrays.fill(vis, -1);
            in[root] = 0;
            for (int i = 0; i < pointCount; i++){ // 标记每个环
                ans += in[i]; // 加入每个点的入边（既然是最小入边，所以肯定符合最小树形图的思想）
                int v = i; // v一开始先从第i个节点进去
                while (vis[v] != i && id[v] == -1 && v != root) { //退出的条件有“形成了一个环，即vis回归”、“到了一个环，此时就不要管了，因为那边已经建好环了”、“到了根节点，就是条链，不用管了”
                    vis[v] = i;
                    v = pre[v];
                }
                if (v != root && id[v] == -1) { //如果v是root就说明是返回到了根节点，是条链，没环；又或者，它已经是进入了对应环的编号了，不需要再跑一趟了
                    for (int u = pre[v]; u != v ; u = pre[u]) id[u] = count; // 跑这一圈的环, 同时标记点u是第几个环
                    id[v] = count++; // 如果再遇到，就是下个点了
                }
            }
            if (count == 0) break; // 无环的情况，就说明已经取到了最优解，直接返回，或者说是环已经收缩到没有环的情况了
            for (int i = 0; i < pointCount; i++) if(id[i] == -1) id[i] = count++; //这些点是环外的点，是链上的点，单独再给他们赋值
            for (int[] edge : edges) { // 准备开始建立新图  缩点，重新标记
                int v = edge[1];
                // 建立新图，以新的点进入
                edge[0] = id[edge[0]];
                edge[1] = id[edge[1]];
                if (edge[0] != edge[1]) edge[2] -= in[v]; // 为了不改变原来的式子，使得展开后还是原来的式子
            }
            pointCount = count; // 之后的点的数目
            root = id[root]; // 新的根节点的序号，因为id[]的改变，所以根节点的序号也改变了
        }
        return ans;
    }
}
```
