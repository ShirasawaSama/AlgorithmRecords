# Assign the task

https://vjudge.net/problem/HDU-3974#author=CaprYang

## 题目

现有一棵树，有以下操作:

1. 节点x及其所有子孙颜色都变更为k。
2. 要求你回答节点x的颜色。

初始所有点都没有染色。

### Input

第一行一个整数T(T <= 10)，表示样例组数。

对于每个测试样例：

第一行一个整数n(n <= 5e4)，表示树的节点个数。

接下来n-1行，每行两个整数u, v(1 <= u, v <= n)，表示树中u的父节点是v。

接下来一行一个整数q(q <= 5e4)，表示询问数。

接下来q行：

若为染色操作则输入“T x k”，若为查询操作则输入“C x”，(1 <= x <= n, 0 <= y <= 1e9)。

### Output

每个测试样例首先输出一行"Case #x:"，其中x为当前样例编号。

对于每个询问操作输出一个整数，表示当前节点的颜色，若还未染色则输出-1。

### Sample Input

```
1
5
4 3
3 2
1 3
5 2
5
C 3
T 2 1
C 3
T 3 2
C 3
```

### Sample Output

```
Case #1:
-1 
1 
2
```

## 题解

首先根据给的数据进行建树, 当染色的时候, 首先先获取当前要染色节点的全部父节点, 建立一个路径.

然后从根节点依次对这个路径上的点进行以下操作:

1. 若当前节点的lazy有值, 则将当前值转移到所有第一层子节点的lazy和color上 (还需要检查一下被转移值的子节点是不是当前要染色的节点, 如果是的话就不转移).
2. 清空当前节点的lazy值.
3. 遍历当前路径的下一个节点.

操作完成后再设置当前要染色的节点的lazy和color值为所染的色.

当查询某个节点颜色的时候, 依旧需要获取当前要染色节点的全部父节点, 建立一个路径.

然后再这个路径上寻找离整棵树的根节点最近的带有lazy值得节点即可, 如果找到的话当前节点颜色值就是所找到节点的lazy值, 否则就是当前节点的color值.

## 代码

```java
import java.util.*;

public class Main {
    private final static class Node extends ArrayList<Node> {
        public Node parent;
        public int color = -1, lazy = -1;
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        for (int j = 1; j <= all; j++) {
            int len = cin.nextInt();
            Node[] nodes = new Node[len];
            nodes[0] = new Node();
            for (int i = 1; i < len; i++) {
                if (nodes[i] == null) nodes[i] = new Node();
                int ui = cin.nextInt() - 1, vi = cin.nextInt() - 1;
                Node u = nodes[ui], v = nodes[vi];
                if (u == null) u = nodes[ui] = new Node();
                if (v == null) v = nodes[vi] = new Node();
                v.add(u);
                u.parent = v;
            }
            int q = cin.nextInt();
            cin.nextLine();
            System.out.println("Case #" + j + ":");
            while (q-- > 0) {
                String[] arr = cin.nextLine().trim().split(" ");
                switch (arr[0]) {
                    case "T": {
                        Node node = nodes[Integer.parseInt(arr[1]) - 1], nodeCopy = node;
                        node = node.parent;
                        ArrayList<Node> road = new ArrayList<>();
                        while (node != null) {
                            road.add(node);
                            node = node.parent;
                        }
                        int k = road.size();
                        while (k-- > 0) {
                            node = road.get(k);
                            if (node.lazy != -1) {
                                final int curColor = node.lazy;
                                for (Node it : node) if (it != nodeCopy) it.lazy = it.color = curColor;
                                node.lazy = -1;
                            }
                        }
                        nodeCopy.color = nodeCopy.lazy = Integer.parseInt(arr[2]);
                        break;
                    }
                    case "C": {
                        Node node = nodes[Integer.parseInt(arr[1]) - 1];
                        int color = node.color;
                        node = node.parent;
                        while (node != null) {
                            if (node.lazy != -1) color = node.lazy;
                            node = node.parent;
                        }
                        System.out.println(color);
                    }
                }
            }
        }
    }
}
```
