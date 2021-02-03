# Round Numbers

https://vjudge.net/problem/POJ-3252

翻译: https://vjudge.net/problem/HYSBZ-1662

## 题目

正如你所知，奶牛们没有手指以至于不能玩“石头剪刀布”来任意地决定谁先挤奶的顺序。她们甚至也不能通过仍硬币的方式。

所以她们通过"round number"竞赛的方式:

第一头牛选取一个整数，小于20亿。第二头牛也这样选取一个整数。

如果这两个数都是 "round numbers"，那么第一头牛获胜，否则第二头牛获胜。

如果一个正整数N的二进制表示中，0的个数大于或等于1的个数，那么N就被称为 "round number" 。

例如，整数9，二进制表示是1001，1001 有两个'0'和两个'1'; 因此，9是一个round number。

26 的二进制表示是 11010 ; 由于它有2个'0'和 3个'1'，所以它不是round number。

很明显，奶牛们会花费很大精力去转换进制，从而确定谁是胜者。

Bessie 想要 作弊，而且认为只要她能够知道在一个指定区间范围内的"round numbers"个数。

帮助她写一个程序，能够告诉她在一个闭区间中有多少Hround numbers。

区间是 [start, finish]，包含这两个数。 (1 <= Start < Finish <= 2,000,000,000)

### Input

Line 1: 两个用空格分开的整数，分别表示Start 和 Finish

### Output

Line 1: Start..Finish范围内round numbers的个数

### Sample Input

```
2 12
```

### Sample Output

```
6
```

### Hint

- 2    10  1x0 + 1x1  ROUND
- 3    11  0x0 + 2x1  NOT round
- 4   100  2x0 + 1x1  ROUND
- 5   101  1x0 + 2x1  NOT round
- 6   110  1x0 + 2x1  NOT round
- 7   111  0x0 + 3x1  NOT round
- 8  1000  3x0 + 1x1  ROUND
- 9  1001  2x0 + 2x1  ROUND
- 10  1010  2x0 + 2x1  ROUND
- 11  1011  1x0 + 3x1  NOT round
- 12  1100  2x0 + 2x1  ROUND

## 题解

数位dp + 记忆化dfs

将数转换成二进制来看, 然后从最开始一位依次往最下面搜, 搜完之后把结果记录下来以便下次使用.

建议直接看代码

## 代码

```java
import java.util.*;

public class Main {
    private final static int MAX = 33;
    private static final int[] bin = new int[MAX];
    private static final int[][][] dp = new int[MAX][MAX][MAX];
    // flag 表示是否到达数位的上界(之前枚举的此数位上是否已经有过数字比给定的上界的对应数位上的数字大。真就枚举到当位，否则就枚举到1), first 表示当前数的最前头是不是1
    private static int dfs(int pos, int zeroCount, int oneCount, boolean flag, boolean first) {
        if (pos == 0) return zeroCount >= oneCount ? 1 : 0; // 如果位数已经全部算完了就返回当前的结果
        if (flag && dp[pos][zeroCount][oneCount] != -1) return dp[pos][zeroCount][oneCount]; // 如果有pos位的数的0的个数为zeroCount, 1的个数为oneCount并且已经被算过了就直接返回结果
        int ans = 0, end = flag ? 1 : bin[pos]; // 下一位数字的开头的数字
        for (int i = 0; i <= end; i++) { // 枚举下一位数字的开头是 0 和 1 的情况
            ans += !first && i == 0 // 如果下一个数存在前导0, 如果存在的话那么就不能加上前面1和0的个数
                ? dfs(pos - 1, 0, 0, flag || i < end, false)
                : dfs(pos - 1, zeroCount + (i == 0 ? 1 : 0), oneCount + (i == 1 ? 1 : 0), flag || i < end, true);
        }
        if (flag) dp[pos][zeroCount][oneCount] = ans;
        return ans;
    }
    private static int solve(int num) {
        int pos = 0;
        while (num != 0) {
            bin[++pos] = num & 1;
            num >>= 1;
        }
        return dfs(pos, 0, 0, false, false);
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        for (int i = 0; i < MAX; i++) for (int j = 0; j < MAX; j++) Arrays.fill(dp[i][j], -1);
        while (cin.hasNextInt()) {
            int start = cin.nextInt(), end = cin.nextInt();
            System.out.println(solve(end) - solve(start - 1));
        }
    }
}
```
