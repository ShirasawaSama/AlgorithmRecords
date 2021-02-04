# B-number

https://vjudge.net/problem/HDU-3652#author=chen_zhe_

## 题目

统计区间 [1,n] 中含有 '13' 且模 13 为 0 的数字有多少个。

### 输入格式

多组数据

每行一个正整数n(1 ≤ n ≤ 1e9)

一直输入到文件结束

### 输出格式

输出一行表示满足条件的答案

### 样例输入

```
13
100
200
1000
```

### 样例输出

```
1
1
2
2
```

## 题解

数位 DP, `dp[pos][num][state]` 表示在 `pos` 位下的以数字 `num` 为结尾的状态为 `state` 的数字的数量, `state` 可取以下值:

0. 不包含13
1. 最开始的一位是1
2. 数字包含13

> -1 为初始值, 表示未被搜索

## 代码

```java
import java.util.*;

public class Main {
    private final static int MAX = 20;
    private final static int[] arr = new int[MAX];
    private static final int[][][] dp = new int[MAX][MAX][3];
    // state: 0:不包含13 1:i+1位是1 2:包含了13
    private static int dfs(int pos, int state, int num, boolean flag) {
        if (pos == 0) return state == 2 && num % 13 == 0 ? 1 : 0;
        if (flag && dp[pos][num][state] != -1) return dp[pos][num][state];
        int ans = 0, end = flag ? 9 : arr[pos];
        for (int i = 0; i <= end; i++) {
            int nextNum = (num * 10 + i) % 13;
            if (state == 2 || (state == 1 && i == 3)) ans += dfs(pos - 1, 2, nextNum, flag || i < end);
            else if (i == 1) ans += dfs(pos - 1, 1, nextNum, flag || i < end);
            else ans += dfs(pos - 1, 0, nextNum, flag || i < end);
        }
        if (flag) dp[pos][num][state] = ans;
        return ans;
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        for (int i = 0; i < MAX; i++) for (int j = 0; j < MAX; j++) Arrays.fill(dp[i][j], -1);
        while (cin.hasNextInt()) {
            int num = cin.nextInt(), len = 0;
            while (num != 0) {
                arr[++len] = num % 10;
                num /= 10;
            }
            System.out.println(dfs(len, 0, 0, false));
        }
    }
}
```
