# 233 Matrix

https://vjudge.net/problem/HDU-5015

## 题目

In our daily life we often use 233 to express our feelings. Actually, we may say 2333, 23333, or 233333 ... in the same meaning.

And here is the question:

Suppose we have a matrix called 233 matrix. In the first line, it would be 233, 2333, 23333... (it means a0,1 = 233,a0,2 = 2333,a0,3 = 23333...)

Besides, in 233 matrix, we got ai,j = ai-1,j +ai,j-1( i,j ≠ 0).

Now you have known a1,0,a2,0,...,an,0, could you tell me an,m in the 233 matrix?

### Input

There are multiple test cases. Please process till EOF.

For each case, the first line contains two postive integers n,m(n ≤ 10,m ≤ 109). The second line contains n integers, a1,0,a2,0,...,an,0(0 ≤ ai,0 < 231).

### Output

For each case, output an,m mod 10000007.

### Sample Input

```
1 1
1
2 2
0 0
3 7
23 47 16
```

### Sample Output

```
234
2799
72937
```

## 题解

矩阵快速幂模板

这个挺不错, 也容易看懂: https://blog.csdn.net/qq_41280600/article/details/103083637

## 代码

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Scanner cin = new Scanner(System.in);
        while (cin.hasNextInt()) {
            int n = cin.nextInt(), m = cin.nextInt();
            int[] firstRow = new int[n + 2];
            firstRow[0] = 23;
            firstRow[n + 1] = 3;
            for (int i = 1; i <= n; i++) firstRow[i] = cin.nextInt();

            long[][] g = new long[n + 2][n + 2];
            for (int i = 0; i <= n; i++) {
                g[i][0] = 10;
                g[i][n + 1] = 1;
                for (int j = 1; j <= i; j++) g[i][j] = 1;
            }
            g[n + 1][n + 1] = 1;

            long[][] A = pow(g, m);

            long ans = 0;
            for (int k = 0; k < n + 2; k++) {
                ans = (ans + A[n][k] * firstRow[k]) % 10000007;
            }
            System.out.println(ans);
        }
    }

    public static long[][] pow(long[][] x, long y) {
        int len = x.length;
        long[][] ans = new long[len][len];
        for (int i = 0; i < len; i++) ans[i][i] = 1;
        while (y != 0) {
            if((y & 1) == 1) ans = mul(ans, x);
            x = mul(x,x);
            y >>= 1;
        }
        return ans;
    }

    public static long[][] mul(long[][] a, long[][] b) {
        int len1 = a.length, len2 = b[0].length, len3 = b.length;
        long[][] ans = new long[len1][len2];
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                for (int k = 0; k < len3; k++) ans[i][j] = ans[i][j] + a[i][k] * b[k][j];
                ans[i][j] %= 10000007;
            }
        }
        return ans;
    }
}
```
