# Super Jumping! Jumping! Jumping!

https://vjudge.net/problem/HDU-1087#author=zzuli_practice

## 题目

现在，有一种象棋游戏叫“超级跳跃！跳跃的！跳跃的！“在HDU非常流行也许你是个好孩子，对这项运动知之甚少，所以我现在就给你介绍一下。

![](https://user-images.githubusercontent.com/17093811/106416003-6bfe6d00-648b-11eb-9332-7c87f372b8c4.png)

这个游戏可以由两个或两个以上的玩家玩。它由一个棋盘（棋盘）和一些棋子（棋子）组成，所有棋子都用正整数或“开始”或“结束”标记。玩家从起点开始，最后必须跳入终点。

在跳跃的过程中，玩家将访问路径中的棋子，但是每个人都必须从一个棋子跳到另一个棋子绝对大（你可以假定起点是最小的，终点是最大的）。所有玩家都不能倒退。

一跳可以从一个棋子跳到另一个棋子，也可以跨越许多棋子，甚至你可以直接从起点跳到终点。在这种情况下你当然得零分。

一个运动员是胜利者，只要他能根据他的跳跃解决方案得到更大的分数。

注意，你的分数来自于你跳跃路径中棋子的价值之和。

你的任务是根据给定的棋子列表输出最大值。

### Input

输入包含多个测试用例。每个测试用例在一行中描述如下：

```
N value_1 value_2 …value_N
```

保证N不大于1000，且所有值均在int范围内。

以0开头的测试用例终止输入，不处理此测试用例。

### Output

对于每种情况，按规则打印最大值，一行打印一次。

### Sample Input

```
3 1 3 2
4 1 2 3 4
4 3 3 2 1
0
```

### Sample Output

```
4
10
3
```

## 题意

给定一条长度为n的序列，其中一定存在一条元素和最大的严格上升子序列，求这条序列的元素和。

## 题解

动态规划, dp[i]表示的是前i个并且包含第i个的最大递增子序列和.

设整个序列为 `arr`

首先第一个循环 `i` 遍历整个序列, 然后里边套一个循环 `j`, 遍历序列从0~i的元素.

然后如果序列中的 `arr[i]` 大于 `arr[j]` 则说明可以跳到这一格, 将前面dp的值与当前格子的值相加得到之前跳到这一格时的最大序列和 (同时比较一下是不是当前dp[j]是不是在全部前面的最大值).

还有一种情况是 `开始->100->1->2->结束` 这样的话直接跳到 100, 再直接结束这样是最大的. 因此可能出现单跳一个格子就已经比其他格子都大的情况. 所以还需要对比一下 `dp[i]` 和 `arr[i]` 的大小.

一轮遍历下来之后, 只需要选取整个 `dp` 数组中最大的值进行输出即可.

状态转移方程:

```java
dp[i] = max(dp[j] + arr[i], dp[i], arr[i]) // 如果 arr[i] > arr[j]
dp[i] = arr[i] // 如果 arr[i] > dp[i]
```

## 代码

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int len;
        while ((len = cin.nextInt()) > 0) {
            int[] arr = new int[len], dp = new int[len];
            for (int i = 0; i < len; i++) arr[i] = cin.nextInt();
            for (int i = 0; i < len; i++) {
                for (int j = 0; j <= i; j++) if (arr[j] < arr[i]) dp[i] = Math.max(dp[j] + arr[i], dp[i]);
                if (arr[i] > dp[i]) dp[i] = arr[i];
            }
            int max = 0;
            for (int it : dp) if (it > max) max = it;
            System.out.println(max);
        }
    }
}
```
