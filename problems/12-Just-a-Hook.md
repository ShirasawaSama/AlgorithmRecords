# Just a Hook

https://vjudge.net/problem/HRBUST-1820

## 题目

在Dota游戏中，屠夫的钩子对于大多数的英雄来说还真是个可怕的东西。这个钩子是由一些连续的拥有相同长度的金属节点连接而成。

现在屠夫想要对他的钩子进行一些操作。

我们现在把这个长长的钩子从一个端点起依次标记为 1 到 N。对于每一次操作，屠夫可以改变一段连续的金属节点的属性。例如从节点X 到节点 Y，它可以把这段区间的所有节点变成铁质的，银质的或者金质的节点。屠夫的钩子的总的威力值为钩子上这N个金属节点对应价值的总和，更精确的来讲，这些金属对应的价值依次为：

每个铁质节点，价值为 1.

每个银质节点，价值为 2.

每个金质节点，价值为 3.

屠夫想知道在他对他的钩子进行一些操作之后，他的钩子的威力值为多少。

一开始，钩子上的每个节点都是铁质的。

### Input

输入包含多组 测试数据。第一行输入一个T表示测试的组数。

对于每组数据：

第一行输入一个值 N，1<=N<=100,000，代表屠夫钩子的总节点数

第二行输入一个值 Q，0<=Q<=100,000，表示屠夫进行操作的次数。

接下来的 Q 行，每行包含3个整数 X,Y,Z ,  (1<=X<=Y<=N) (1<=Z<=3)表示进行的操作为：

把从节点 X 到节点 Y 的部分变成 Z 金属 ，（Z=1表示铁质，Z=2表示银质，Z=3表示金质）。

### Output

对于每组测试数据，输出一个整数，表示屠夫进行所有的操作 之后，钩子的威力值，输出格式参照样例，每组输出占一行。

### Sample Input

```
1 10 2 1 5 2 5 9 3
```

### Sample Output

```
Case 1: The total value of the hook is 24.
```

## 题意

给一段长度n, 和q个操作, 每次操作把一个区间全部更新成 1 或 2 或 3, 最后查询出总长度

## 题解

先构建一棵带有lazy标记的线段树, 设置线段树的根节点为1 (意思是底下全部的子节点都是1)

之后进行更新操作, 每次操作都只尝试更新lazy标记, 比方说最开始的时候要更新 1 ~ n/2 为 2, 只需要设置这段节点的lazy标记为2即可.

当下次读取到某个有lazy标记的节点, 并且需要继续读取本节点的子节点时, 则需要将这个标记转移到子节点来实现状态更新

累加的时候只需要将lazy标记乘以当前节点拥有的子节点数量最后相加即可

## 代码

```java
import java.util.*;

public class Main {
    private final static class SegmentTree {
        private final int[] arr;
        public SegmentTree(int len) {
            arr = new int[len * 4];
        }
        public void update(int left, int right, int curLeft, int curRight, int index, int value) {
            if (left <= curLeft && right >= curRight) arr[index] = value;
            else {
                if (arr[index] != 0) {
                    arr[index * 2 + 1] = arr[index * 2] = arr[index];
                    arr[index] = 0;
                }
                int mid = (curLeft + curRight) / 2;
                if (left <= mid) update(left, right, curLeft, mid, index * 2, value);
                if (right > mid) update(left, right, mid + 1, curRight, index * 2 + 1, value);
            }
        }
        public int query(int left, int right, int curLeft, int curRight, int index) {
            if (left <= curLeft && right >= curRight && arr[index] != 0) return arr[index] * (curRight - curLeft + 1);
            int mid = (curLeft + curRight) / 2, sum = 0;
            if (left <= mid) sum += query(left, right, curLeft, mid, index * 2);
            if (right > mid) sum += query(left, right, mid + 1, curRight, index * 2 + 1);
            return sum;
        }
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        for (int j = 1; j <= all; j++) {
            int len = cin.nextInt(), q = cin.nextInt();
            SegmentTree tree = new SegmentTree(len);
            tree.update(1, len, 1, len, 1, 1);
            while (q-- > 0) {
                tree.update(cin.nextInt(), cin.nextInt(), 1, len, 1, cin.nextInt());
            }
            System.out.println("Case " + j + ": The total value of the hook is " + tree.query(1, len, 1, len, 1) + ".");
        }
    }
}
```
