# Multiplication Puzzle

https://vjudge.net/problem/POJ-1651

## 题目

The multiplication puzzle is played with a row of cards, each containing a single positive integer.

During the move player takes one card out of the row and scores the number of points equal to the product of the number on the card taken and the numbers on the cards on the left and on the right of it.

It is not allowed to take out the first and the last card in the row. After the final move, only two cards are left in the row.

The goal is to take cards in such order as to minimize the total number of scored points.

For example, if cards in the row contain numbers 10 1 50 20 5, player might take a card with 1, then 20 and 50, scoring

```
10*1*50 + 50*20*5 + 10*50*5 = 500+5000+2500 = 8000
```

If he would take the cards in the opposite order, i.e. 50, then 20, then 1, the score would be

```
1*50*20 + 1*20*5 + 10*1*5 = 1000+100+50 = 1150.
```

### Input

The first line of the input contains the number of cards N (3 <= N <= 100). The second line contains N integers in the range from 1 to 100, separated by spaces.

### Output

Output must contain a single integer - the minimal score.

### Sample Input

```
6
10 1 50 50 20 5
```

### Sample Output

```
3650
```

## 题意

给出一组N个数, 每次从中抽出一个数 (第一和最后一个不能抽), 该次的得分即为抽出的数与相邻两个数的乘, 直到只剩下首尾两个数为止, 问最小得分是多少.

## 题解

区间dp, `dp[start][end]` 表示序列内以第 `start` 个元素开头和第 `end` 个元素为结尾的区间内的最小值是多少.

因此只需要枚举区间的长度 `len`, 从 `2` (因为加上左端本身就是3, 即区间左端的元素, 区间内被枚举的元素和区间右端的元素) 到 `N - 1` 即可 (这里思考一下就懂为什么是这个范围了).

然后再依次枚举序列的左端下标 (从 `0` 一直枚举到 `N - 2` 个), 然后加上 `len` 即为当前区间的右端下标 `end`.

然后再依次枚举这个序列内的全部元素  **(意思是从第 `start + 1` 个元素到 `end - 1` 个元素)**, 同时令下标为 `k`, 即可依次得到从最小区间到最大区间 (即整个序列第 `0` 个元素一直到 `N - 1` 个元素) 的最小结果.

然后取 `dp[0][N - 1]` 即为答案, 输出即可.

状态转移方程: `dp[start][end] = Math.min(dp[start][end], dp[start][k] + dp[k][end] + arr[start] * arr[k] * arr[end])`

分析也可以得到这类题的通式为 `dp[start][end] = Math.min(dp[start][end], dp[start][k] + dp[k][end] + 跟k相关的式子())`

## 代码

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        int[] arr = new int[n];
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) arr[i] = cin.nextInt();
        for (int len = 2; len < n; len++) { // 区间长度
            for (int i = 0, end; (end = i + len) < n; i++) { // 起点
                dp[i][end] = Integer.MAX_VALUE;
                for (int j = i + 1; j <= end; j++) { // 选择的元素
                    dp[i][end] = Math.min(dp[i][end], dp[i][j] + dp[j][end] + arr[i] * arr[j] * arr[end]);
                }
            }
        }
        System.out.println(dp[0][n - 1]);
    }
}
```
