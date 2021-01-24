# Extended Traffic

https://vjudge.net/problem/LightOJ-1074#author=SDUProgramming

## 题目

这一晚，TT 做了个美梦！

在梦中，TT 的愿望成真了，他成为了喵星的统领！喵星上有 N 个商业城市，编号 1 ～ N，其中 1 号城市是 TT 所在的城市，即首都。

喵星上共有 M 条有向道路供商业城市相互往来。但是随着喵星商业的日渐繁荣，有些道路变得非常拥挤。正在 TT 为之苦恼之时，他的魔法小猫咪提出了一个解决方案！TT 欣然接受并针对该方案颁布了一项新的政策。

具体政策如下：对每一个商业城市标记一个正整数，表示其繁荣程度，当每一只喵沿道路从一个商业城市走到另一个商业城市时，TT 都会收取它们（目的地繁荣程度 - 出发地繁荣程度）^ 3 的税。

TT 打算测试一下这项政策是否合理，因此他想知道从首都出发，走到其他城市至少要交多少的税，如果总金额小于 3 或者无法到达请悄咪咪地打出 '?'。 

### Input

第一行输入 T，表明共有 T 组数据。（1 <= T <= 50）

对于每一组数据，第一行输入 N，表示点的个数。（1 <= N <= 200）

第二行输入 N 个整数，表示 1 ～ N 点的权值 a[i]。（0 <= a[i] <= 20）

第三行输入 M，表示有向道路的条数。（0 <= M <= 100000）

接下来 M 行，每行有两个整数 A B，表示存在一条 A 到 B 的有向道路。

接下来给出一个整数 Q，表示询问个数。（0 <= Q <= 100000）

每一次询问给出一个 P，表示求 1 号点到 P 号点的最少税费。

### Output

每个询问输出一行，如果不可达或税费小于 3 则输出 `?`。

### Sample Input

```
2
5
6 7 8 9 10
6
1 2
2 3
3 4
1 5
5 4
4 5
2
4
5
10
1 2 4 4 5 6 7 8 9 10
10
1 2
2 3
3 1
1 4
4 5
5 6
6 7
7 8
8 9
9 10
2
3 10
```

### Sample Output

```
Case 1:
3
4
Case 2:
?
?
```

## 题意

给一个图, 求第一点到其他点的最短距离, 同时由于费用可能是负的 (表示不可达, 题目没说), 所以需要判断负环.

当某个点在负环内则输出 `?`, 反之输出最短距离

spfa + 判断负环

## 代码

```java
import java.util.*;

@SuppressWarnings("unchecked")
public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        for (int j = 1; j <= all; j++) {
            int len = cin.nextInt();
            int[] howRich = new int[len];
            for (int i = 0; i < len; i++) howRich[i] = cin.nextInt();
            int count = cin.nextInt();
            ArrayList<int[]>[] edges = new ArrayList[len];
            while (count-- > 0) {
                int a = cin.nextInt() - 1, b = cin.nextInt() - 1;
                ArrayList<int[]> it = edges[a];
                if (it == null) edges[a] = it = new ArrayList<>();
                it.add(new int[] { b, (int) Math.pow(howRich[b] - howRich[a], 3) });
            }
            boolean[] visited = new boolean[len];
            int[] distances = new int[len], pushToQueueTimes = new int[len];
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(0);
            visited[0] = true;
            for (int i = 1; i < len; i++) distances[i] = Integer.MAX_VALUE;
            while (!queue.isEmpty()) {
                int cur = queue.pop();
                visited[cur] = false;
                pushToQueueTimes[cur]++;
                int dis = distances[cur];
                if (edges[cur] == null) continue;
                for (int[] it : edges[cur]) {
                    int next = it[0], tmp = dis + it[1], tmp1 = distances[next];
                    if (tmp >= tmp1) continue;
                    distances[next] = tmp;
                    if (visited[next] || pushToQueueTimes[next] > len) continue;
                    visited[next] = true;
                    queue.add(next);
                }
            }
            count = cin.nextInt();
            System.out.println("Case " + j + ":");
            while (count-- > 0) {
                int it = cin.nextInt() - 1, tmp = distances[it];
                if (tmp == Integer.MAX_VALUE || tmp < 3 || pushToQueueTimes[it] > len) System.out.println('?');
                else System.out.println(tmp);
            }
        }
    }
}
```
