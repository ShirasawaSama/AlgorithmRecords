# Borg Maze

https://vjudge.net/problem/POJ-3026

## 题目

The Borg is an immensely powerful race of enhanced humanoids from the delta quadrant of the galaxy. The Borg collective is the term used to describe the group consciousness of the Borg civilization. Each Borg individual is linked to the collective by a sophisticated subspace network that insures each member is given constant supervision and guidance.

Your task is to help the Borg (yes, really) by developing a program which helps the Borg to estimate the minimal cost of scanning a maze for the assimilation of aliens hiding in the maze, by moving in north, west, east, and south steps. The tricky thing is that the beginning of the search is conducted by a large group of over 100 individuals. Whenever an alien is assimilated, or at the beginning of the search, the group may split in two or more groups (but their consciousness is still collective.). The cost of searching a maze is definied as the total distance covered by all the groups involved in the search together. That is, if the original group walks five steps, then splits into two groups each walking three steps, the total distance is 11=5+3+3.

### Input

On the first line of input there is one integer, N <= 50, giving the number of test cases in the input. Each test case starts with a line containg two integers x, y such that 1 <= x,y <= 50. After this, y lines follow, each which x characters. For each character, a space `` '' stands for an open space, a hash mark ``#'' stands for an obstructing wall, the capital letter ``A'' stand for an alien, and the capital letter ``S'' stands for the start of the search. The perimeter of the maze is always closed, i.e., there is no way to get out from the coordinate of the ``S''. At most 100 aliens are present in the maze, and everyone is reachable.

### Output

For every test case, output one line containing the minimal cost of a succesful search of the maze leaving no aliens alive.

### Sample Input

```
2
6 5
##### 
#A#A##
# # A#
#S  ##
##### 
7 7
#####  
#AAA###
#    A#
# S ###
#     #
#AAA###
#####  
```

### Sample Output

```
8
11
```

## 题意

从 S 出发，去到达每一个 A ，求最小的总路径长度，空格是空地，# 是墙，并且在走的过程中我们可以在 S 或 A 点分裂，也就是从该点可以延伸出多条路径到其他点，但是每一次只能让其中的一个继续行走

## 题解

因为到达一个点后可以进行分裂(一个点可以继续往别的点走, 也可以瞬移到前面走过的点再往别的点走), 所以只需要建立一棵包含 `S` 和 `A` 点的最小生成树即可.

由于数据是图格形式, 因此需要先转换成一个完全图, 利用bfs计算一个 `S` 或 `A` 点到其他点的距离.

之后再用 kruskal 法求最小生成树即可.

## 代码

```java
import java.util.*;

public class Main {
    private static int find(int[] arr, int index) {
        int tmp = index;
        while (arr[tmp] != tmp) tmp = arr[tmp];
        return tmp;
    }
    public static int kruskal(int[][] edges, int max) {
        Arrays.sort(edges, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });
        int ans = 0, t = 0, count = 0;
        int[] points = new int[max + 1];
        for (int i = 0; i <= max; i++) points[i] = i;
        while (count != max) {
            int[] edge = edges[t++];
            int sa = find(points, edge[0]), sb = find(points, edge[1]);
            if (sa == sb) continue;
            ans += edge[2];
            points[sb] = sa;
            count++;
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        while (all-- > 0) {
            int y = cin.nextInt(), x = cin.nextInt();
            cin.nextLine();
            char[][] arr = new char[x][];
            int aCount = 0;
            for (int i = 0; i < x; i++) {
                arr[i] = cin.nextLine().toCharArray();
                for (char ch : arr[i]) if (ch == 'A') aCount++;
            }
            int[][] edges = new int[aCount * (aCount + 1) / 2][3];
            int id = 0, k = 0;
            HashMap<String, Integer> map = new HashMap<String, Integer>();
            for (int i = 0; i < x; i++) for (int j = 0; j < y; j++) {
                switch (arr[i][j]) {
                    case 'S':
                    case 'A': {
                        boolean[][] visited = new boolean[x][y];
                        LinkedList<int[]> queue = new LinkedList<int[]>();
                        queue.add(new int[] { i, j, 0 });
                        int visitedCount = 0;
                        String loc = i + "," + j;
                        while (visitedCount != id && !queue.isEmpty()) {
                            int[] it = queue.pop();
                            int u = it[0], v = it[1];
                            if (visited[u][v]) continue;
                            visited[u][v] = true;
                            int deep = it[2];
                            switch (arr[u][v]) {
                                case 'A':
                                case 'S':
                                    if (deep != 0) {
                                        String nextLoc = u + "," + v;
                                        if (map.containsKey(nextLoc)) {
                                            int[] tmp = edges[k++];
                                            tmp[0] = map.get(nextLoc);
                                            tmp[1] = id;
                                            tmp[2] = deep;
                                            visitedCount++;
                                        }
                                    }
                                case ' ': break;
                                default: continue;
                            }
                            deep++;
                            if (u > 0) queue.add(new int[] { u - 1, v, deep });
                            if (v > 0) queue.add(new int[] { u, v - 1, deep });
                            if (u < y - 1) queue.add(new int[] { u + 1, v, deep });
                            if (v < x - 1) queue.add(new int[] { u, v + 1, deep });
                        }
                        map.put(loc, id++);
                    }
                }
            }
            System.out.println(kruskal(edges, aCount));
        }
    }
}

```
