# Currency Exchange

https://vjudge.net/problem/POJ-1860#author=riba2534

## 题目

Several currency exchange points are working in our city. Let us suppose that each point specializes in two particular currencies and performs exchange operations only with these currencies. There can be several points specializing in the same pair of currencies. Each point has its own exchange rates, exchange rate of A to B is the quantity of B you get for 1A. Also each exchange point has some commission, the sum you have to pay for your exchange operation. Commission is always collected in source currency.

For example, if you want to exchange 100 US Dollars into Russian Rubles at the exchange point, where the exchange rate is 29.75, and the commission is 0.39 you will get (100 - 0.39) * 29.75 = 2963.3975RUR.

You surely know that there are N different currencies you can deal with in our city. Let us assign unique integer number from 1 to N to each currency. Then each exchange point can be described with 6 numbers: integer A and B - numbers of currencies it exchanges, and real RAB, CAB, RBA and CBA - exchange rates and commissions when exchanging A to B and B to A respectively.

Nick has some money in currency S and wonders if he can somehow, after some exchange operations, increase his capital. Of course, he wants to have his money in currency S in the end. Help him to answer this difficult question. Nick must always have non-negative sum of money while making his operations.

### Input

The first line of the input contains four numbers: N - the number of currencies, M - the number of exchange points, S - the number of currency Nick has and V - the quantity of currency units he has. The following M lines contain 6 numbers each - the description of the corresponding exchange point - in specified above order. Numbers are separated by one or more spaces. 1<=S<=N<=100, 1<=M<=100, V is real number, 0<=V<=103.

For each point exchange rates and commissions are real, given with at most two digits after the decimal point, 10-2<=rate<=102, 0<=commission<=102.

Let us call some sequence of the exchange operations simple if no exchange point is used more than once in this sequence. You may assume that ratio of the numeric values of the sums at the end and at the beginning of any simple sequence of the exchange operations will be less than 104.

### Output

If Nick can increase his wealth, output YES, in other case output NO to the output file.

### Sample Input

```
3 2 1 20.0
1 2 1.00 1.00 1.00 1.00
2 3 1.10 1.00 1.10 1.00
```

### Sample Output

```
YES
```

## 题意

有一个人缺钱，想到了用不同的货币换来换去然后赚差价，无中生有

给定n种货币和m个银行, 每个银行只能两种币之间兑换, 如a币到b币或者b币到a币. 同时每两种币之间只有一家银行能换

然后知道那个人一开始有k币种的c块钱

问能否通过换来换去的方式实现钱币的无中生有

## 题解

dfs判断图中是否存在正环

首先设置一个叫money的数组, 用来记录某个货币当前已经换到的最大钱币

然后就从给的那个货币进行dfs, 当搜索到的下一种钱币能实现钱数增加, 就把下一种钱币做个标记（下边的isInCircle数组）, 看看下一种货币在不在环里:

- 如果在环里则说明可以持续无中生有, 输出 YES
- 如果不在环里则继续搜索这种钱币

## 代码

```java
import java.util.*;

public class Main {
    private static final int len;
    private static final double[][] rate, fee;
    private static final double[] money;
    private static final boolean[] isInCircle;
    private static boolean dfs(int type) {
        isInCircle[type] = true;
        for (int i = 0; i < len; i++) {
            double curToOtherRate = rate[type][i];
            if (curToOtherRate == 0) continue;
            double tmp = (money[type] - fee[type][i]) * curToOtherRate;
            if (tmp > money[i]) { // 钱在增加
                money[i] = tmp;
                if (isInCircle[i] || // 如果下一个点也在当前搜索的环里则说明开始套娃, 钱已经可以持续无中生有了
                        dfs(i) // 搜索下一个节点
                ) return true;
            }
        }
        isInCircle[type] = false;
        return false;
    }
    static {
        Scanner cin = new Scanner(System.in);
        len = cin.nextInt();
        int all = cin.nextInt(), initType = cin.nextInt() - 1;
        rate = new double[len][len];
        fee = new double[len][len];
        isInCircle = new boolean[len];
        money = new double[len];
        money[initType] = cin.nextDouble();
        while (all-- > 0) {
            int a = cin.nextInt() - 1, b = cin.nextInt() - 1;
            rate[a][b] = cin.nextDouble();
            fee[a][b] = cin.nextDouble();
            rate[b][a] = cin.nextDouble();
            fee[b][a] = cin.nextDouble();
        }
        System.out.println(dfs(initType) ? "YES" : "NO");
    }
    public static void main(String[] args) { }
}
```
