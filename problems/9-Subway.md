# Subway

https://vjudge.net/problem/POJ-2502

## 题目

一个人从家要到学校去，途中有许多车站，所以有步行和做地铁两种方式，其速度分别是10km/h 和40km/h。输入的规则是第一行输入的是x1,y1,x2,y2,分别代表家的坐标和学校的坐标。以后输入的是车站的坐标，数目不超过200，相邻的两个站点可以坐地铁，其他的需要步行。问到达学校的最短时间是多少？（因为不知道输入的数据有多少，所以用while（scanf（）！=EOF）。其他的就没有什么要注意的了，建图很重要。）

### Input

输入包括家和学校的x，y坐标，其次是几条地铁线的规格。每条地铁线路由线路上每个站点的非负整数x，y坐标组成，每条线至少有两个停靠点。每条地铁线的末端后跟虚拟坐标对-1 -1。该市共有200个地铁站。

### Output

输出是上学所需的时间，四舍五入到最近的分钟，采取最快的路线。

### Sample Input

```
0 0 10000 1000
0 200 5000 200 7000 200 -1 -1 
2000 600 5000 600 10000 600 -1 -1
```

### Sample Output

```
21
```

## 题解

spfa

最主要是建图比较麻烦, 首先将起始点和终点加入到表中, 之后如果是同一列地铁的就将本节点和同一地铁的上一个节点的距离(40KM/h)加入到边集合中

再遍历之前已经加入到边集合的全部点, 进行连接走路的时间(10KM/h)

最后进行搜索, 注意: 计算时间需要用时间除以距离, 最后再四舍五入到整数输出

## 代码

```java
import java.util.*;

public class Main {
    private static final Double max = (double) Integer.MAX_VALUE;
    public static Map<String, Double> spfa(String firstPoint, HashMap<String, ArrayList<Object[]>> edges) {
        HashSet<String> visited = new HashSet<String>();
        HashMap<String, Double> distances = new HashMap<String, Double>() /* , times = new int[len] */;
        LinkedList<String> queue = new LinkedList<String>();
        distances.put(firstPoint, 0.0);
        queue.add(firstPoint);
        visited.add(firstPoint);
        while (!queue.isEmpty()) {
            String cur = queue.pop();
            visited.remove(cur);
            Double dis = distances.get(cur);
            if (dis == null) dis = max;
            for (Object[] it : edges.get(cur)) {
                String next = (String) it[0];
                double tmp = dis + (Double) it[1];
                Double tmp1 = distances.get(next);
                if (tmp1 == null) tmp1 = max;
                if (tmp >= tmp1) continue;
                distances.put(next, tmp);
                if (visited.contains(next)) continue;
                visited.add(next);
                queue.add(next);
            }
        }
        return distances;
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        HashMap<String, ArrayList<Object[]>> map = new HashMap<String, ArrayList<Object[]>>();
        int sx = cin.nextInt(), sy = cin.nextInt(), dx = cin.nextInt(), dy = cin.nextInt();
        addEdge(map, sx, sy, -1, -1);
        addEdge(map, dx, dy, -1, -1);
        int prevX = -1, prevY = -1;
        while (cin.hasNextInt()) {
            int x = cin.nextInt(), y = cin.nextInt();
            if (x != -1 && y != -1) addEdge(map, x, y, prevX, prevY);
            prevX = x;
            prevY = y;
        }
        System.out.println(Math.round(spfa(sx + "," + sy, map).get(dx + "," + dy)));
    }
    private static void addEdge(HashMap<String, ArrayList<Object[]>> map, int x, int y, int prevX, int prevY) {
        String str = x + "," + y, prev = prevX + "," + prevY;
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        for (Map.Entry<String, ArrayList<Object[]>> it : map.entrySet()) {
            String k = it.getKey();
            String[] c = k.split(",");
            int ax = Integer.parseInt(c[0]), ay = Integer.parseInt(c[1]);
            Double distance = Math.sqrt(Math.pow(ax - x, 2) + Math.pow(ay - y, 2)) / 1000.0 / (prev.equals(k) ? 40.0 : 10.0) * 60.0;
            list.add(new Object[] { k, distance });
            it.getValue().add(new Object[] { str, distance });
        }
        map.put(str, list);
    }
}
```
