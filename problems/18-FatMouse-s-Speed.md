# FatMouse's Speed

https://vjudge.net/problem/HDU-1160#author=prayerhgq

## 题目

很多肥老鼠认为，长的越肥，奔跑速度就越快。

为了反驳这个观点，你现在需要对老鼠的体重和速度进行研究。

你要在老鼠序列中找出一个子序列，使得老鼠的体重在增加，但是速度却在减慢。

### Input

输入以eof结束。

输入中每行有两个正整数，分别表示老鼠的体重和速度，范围均在1到10000之间，输入数据最多有1000只老鼠。

某些老鼠可能有相同的体重，某些老鼠可能有相同的速度，某些老鼠可能体重和速度都相同。

### Output
 
我们是要在原来的老鼠序列中，找到一个最长的子序列，使得这个子序列中老鼠的体重在严格增加，速度却在严格降低。

首先输出满足条件的最长的子序列的长度。

其次，输出一个最长子序列的方案，要求输出每个老鼠在输入时候的编号，每个编号占一行，任意一种正确的方法都会被判正确。

### Sample Input

```
6008 1300
6000 2100
500 2000
1000 4000
1100 3000
6000 2000
8000 1400
6000 1200
2000 1900
```

### Sample Output

```
4
4
5
9
7
```

## 题解

由于有 Special Judge, 所以可以为所欲为.

首先将输入进来的数据排序, 体重大的靠后, 如果体重一样就速度满的排后面.

然后进行动态规划查找最长子序列, 设第一层循环为 `(i)`, 同时 `dp[i]` 为以某个元素为序列结尾时的最长子序列长度.

然后再套一个循环 `(j)`, 遍历前面查找过的 1~i 个子序列, 如果第 `i` 个老鼠符合体重比第 `j` 个老鼠大, 速度小 (前边的子序列可以以当前元素为结尾).

那么就尝试计算一下加上当前元素的长度是否能比其他的长, 成功的话就转移状态:

```java
dp[i] = max(dp[i], dp[j] + 1);
```

然后需要进行输出, 所以还需要统计以下最长的子序列长度.

然后输出的时候倒着查找出子序列的元素下标进行输出即可.

## 代码

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int len = 0;
        int[][] arr = new int[1001][3];
        while (cin.hasNextInt()) {
            int[] it = arr[++len];
            it[0] = cin.nextInt();
            it[1] = cin.nextInt();
            it[2] = len;
        }
        Arrays.sort(arr, 1, len, (a, b) -> {
            int tmp = a[0] - b[0];
            return tmp == 0 ? b[1] - a[1] : tmp;
        });
        int[] dp = new int[len + 1];
        int ans = 0;
        for (int i = 1; i <= len; i++) {
            dp[i] = 1;
            int curWeight = arr[i][0], curSpeed = arr[i][1];
            for (int j = 1; j < i; j++) {
                if (arr[j][0] < curWeight && arr[j][1] > curSpeed) { // 体重应该是逐渐增大, 速度应该是逐渐减小的
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] > ans) ans = dp[i]; // 记录最大值
        }
        System.out.println(ans);
        int[] output = new int[ans];
        while (len > 0) {
            if (dp[len] == ans) {
                output[--ans] = arr[len][2];
            }
            len--;
        }
        for (int it : output) System.out.println(it);
    }
}
```
