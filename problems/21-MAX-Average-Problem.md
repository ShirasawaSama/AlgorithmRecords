# MAX Average Problem

https://vjudge.net/problem/HDU-2993

## 题目

Consider a simple sequence which only contains positive integers as a1, a2 ... an, and a number k.

Define ave(i,j) as the average value of the sub sequence ai ... aj, i<=j.

Let’s calculate max(ave(i,j)), 1<=i<=j-k+1<=n.

### Input

There multiple test cases in the input, each test case contains two lines.

The first line has two integers, N and k (k<=N<=10^5).

The second line has N integers, a1, a2 ... an. All numbers are ranged in [1, 2000].

### Output

For every test case, output one single line contains a real number, which is mentioned in the description, accurate to 0.01.

### Sample Input

```java
10 6
6 4 2 10 3 8 5 9 4 1
```

### Sample Output

```
6.50
```

## 题意

给你一个数列, 求大于给定长度的区间的最大平均值

## 题解

斜率优化dp, 式子为 `(sum[i] - sum[j]) / (i - j)`, 之后看懂了再来补充.

> 注: 这题比较坑, 一个是读入的数值比较多需要用快速读入; 其次如果是java, 每一行结束都需要以 `\r\n` 结尾 (C和C++是 `\n`); 不能用模板, 也不能用java自带的 `LinkedList` 等数据结构, 不然容易超时.

## 代码

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static class FastScanner {
        private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 16384);
        private StringTokenizer st;
        public FastScanner() { eat(""); }
        public void eat(String s) { st = new StringTokenizer(s); }
        public String nextLine() {
            try { return br.readLine(); } catch (Exception e) { return null; }
        }
        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String s = nextLine();
                if (s == null) return false;
                eat(s);
            }
            return true;
        }
        public String next() {
            hasNext();
            return st.nextToken();
        }
        public int nextInt() { return Integer.parseInt(next()); }
    }
    private final static int MAX = 100005;
    public static void main(String[] args) {
        int[] list = new int[MAX];
        long[] sum = new long[MAX];
        FastScanner cin = new FastScanner();
        while (cin.hasNext()) {
            int n = cin.nextInt(), k = cin.nextInt();
            for (int i = 1; i <= n; i++) {
                sum[i] = cin.nextInt() + sum[i - 1];
            }
            double[] dp = new double[MAX];
            int head = 0, tail = 0;
            for (int i = k; i <= n; i++) {
                while (head + 1 < tail && (sum[i - k] - sum[list[tail - 1]]) * (list[tail - 1] - list[tail - 2]) <=
                        (i - k - list[tail - 1]) * (sum[list[tail - 1]] - sum[list[tail - 2]])) tail--;
                list[tail++] = i - k;
                while (head + 1 < tail && (sum[i] - sum[list[head]]) * (i - list[head + 1]) <=
                        (i - list[head]) * (sum[i] - sum[list[head + 1]])) head++;
                dp[i] = Double.max(dp[i - 1], ((double) (sum[i] - sum[list[head]])) / (double) (i - list[head]));
            }
            System.out.printf("%.2f\r\n", dp[n]);
        }
    }
}
```

下面的代码使用了 `LinkedList` 作为栈, 逻辑和上面的代码一样, 但是会超时:

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static class FastScanner {
        private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 16384);
        private StringTokenizer st;
        public FastScanner() { eat(""); }
        public void eat(String s) { st = new StringTokenizer(s); }
        public String nextLine() {
            try { return br.readLine(); } catch (Exception e) { return null; }
        }
        public boolean hasNext() {
            while (!st.hasMoreTokens()) {
                String s = nextLine();
                if (s == null) return false;
                eat(s);
            }
            return true;
        }
        public String next() {
            hasNext();
            return st.nextToken();
        }
        public int nextInt() { return Integer.parseInt(next()); }
    }
    private final static long[] sum = new long[100005];
    private static double calc(int i, int j) {
        return ((double) (sum[i] - sum[j])) / (i - j);
    }
    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) {
        FastScanner cin = new FastScanner();
        while (cin.hasNext()) {
            int n = cin.nextInt(), k = cin.nextInt();
            for (int i = 1; i <= n; i++) {
                sum[i] = cin.nextInt() + sum[i - 1];
            }
            double ans = 0;
            LinkedList<Integer> stack = new LinkedList<>();
            for (int i = k; i <= n; i++) {
                while (stack.size() > 1 && calc(i - k, stack.peekLast()) < calc(stack.peekLast(), stack.get(stack.size() - 2))) stack.removeLast();
                stack.add(i - k);
                while (stack.size() > 1 && calc(i, stack.peekFirst()) < calc(i, stack.get(1))) stack.removeFirst();
                ans = Double.max(ans, calc(i, stack.peekFirst()));
            }
            System.out.printf("%.2f\r\n", ans);
        }
    }
}
```
