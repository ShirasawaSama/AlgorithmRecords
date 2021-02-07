# Treats for the Cows

https://vjudge.net/problem/POJ-3186#author=jnu2018053296

## 题目

给你n个数字v(1),v(2),...,v(n-1),v(n)，每次你可以取出最左端的数字或者取出最右端的数字，一共取n次取完。

假设你第i次取的数字是x，你可以获得i*x的价值。

你需要规划取数顺序，使获得的总价值之和最大。

### Input

第一行一个数字n(1<=n<=2000)。

下面n行每行一个数字v(i)。(1<=v(i)<=1000)

### Output

输出一个数字，表示最大总价值和。

### Sample Input

```
5
1
3
1
5
2
```

### Sample Output

```
43
```

### Hint

按照这种下标顺序取数：`1, 5, 2, 3, 4`

取出的数按顺序为：`1, 2, 3, 1, 5`

最大总价值和：`1x1 + 2x2 + 3x3 + 4x1 + 5x5 = 43`

本题不能使用万能头文件

## 题解

区间dp, `dp[left][right]` 表示当前以 `left` 为下标的元素为开头和 `right` 为下标的元素为结尾的区间内的最大值.

然后依次枚举 `left`, 从 `n - 1` 一直到 `0`. 再依次枚举 `right`, 从 `left` 一直到 `n - 1`. 由此可得区间是从右端开始, 逐次变长的.

之后再假设选择区间内的 `left + 1` 个元素或者 `right - 1` 个元素, 表示以当前区间为队列的队列头或队列尾.

接着再乘上出队的序号 `age` (这里为 `n - (right - left)`), 可以根据以下推出:

1. n=5, left=4, right=4 => age=5
2. n=5, left=3, right=3 => age=5
3. n=5, left=3, right=4 => age=4

也可以看出当 `age` 是逐渐递减的, 因此就是倒着进行区间dp.

最后可以得到状态转移方程: `dp[left][right] = max(dp[left + 1][right] + arr[left] * age, (right == 0 ? 0 : dp[left][right - 1]) + arr[right] * age)`

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
        for (int left = n - 1; left-- > 0;) for (int right = left; right < n; right++) {
            int age = n - (right - left);
            dp[left][right] = Math.max(dp[left + 1][right] + arr[left] * age, (right == 0 ? 0 : dp[left][right - 1]) + arr[right] * age);
        }
        System.out.println(dp[0][n - 1]);
    }
}
```
