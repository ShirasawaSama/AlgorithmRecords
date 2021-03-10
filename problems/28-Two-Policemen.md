# Two Policemen

https://codeforces.com/contest/1488/problem/C

## 题目

There is a street that can be represented as an array of length n.

There are two policemen patrolling a street: the first one is standing at the point x of the street and the second one is standing at the point y of the street.

During one minute, both policemen can decide what to do (independently): move left (if the current position is greater than 1), move right (if the current position is less than n), or do nothing.

The street is considered clear if each point of the street is visited by at least one policeman.

Your task is to find the minimum number of minutes the policemen need to visit each point of the street (again, each point should be visited by at least one of them).

You have to answer t independent test cases.

### Input

The first line of the input contains one integer t (1≤t≤2⋅104) — the number of test cases. Then t test cases follow.

The only line of the test case contains three integers n, x and y (2≤n≤106; 1≤x,y≤n; x≠y) — the length of the street, the position of the first policeman and the position of the second policeman, respectively.

It is guaranteed that the sum of n does not exceed 106 (∑n≤106).

### Output

For each test case, print one integer — the minimum number of minutes the policemen need to visit each point of the street.

### Example Input

```
6
4 1 2
7 7 1
10 2 6
8 5 2
2 1 2
20 4 14
```

### Example Output

```
2
3
5
4
0
12
```

## 题意

给你一条有n个点的路还有两个警察的不同初始位置点, 然后每个警察每分钟都可以同时进行以下的一个行动:

- 左走一格
- 右走一格
- 不动

然后问最少需要几分钟整条街能被两个警察走完.

## 题解

首先比较一下两个警察的初始位置, 小的放左边大的放右边.

然后依次枚举两个警察之间的碰头点, 并算出每个警察离最近的顶点的距离和离碰头点的距离, 然后用每个警察必须要走的距离加上上述两个距离的最小值, 然后枚举完毕取总共的最小值即可.

## 代码

```kotlin
import java.util.*
import kotlin.math.*

fun main() {
    val cin = Scanner(System.`in`)
    var n = cin.nextInt()
    while (n-- > 0) {
        val m = cin.nextInt()
        var a = cin.nextInt()
        var b = cin.nextInt()
        if (a > b) {
            val t = a
            a = b
            b = t
        }
        var ans = Int.MAX_VALUE
        for (i in a until b) {
            val a1 = a - 1
            val a2 = i - a
            val b1 = m - b
            val b2 = b - (i + 1)
            ans = min(ans, max(a1 + a2 + min(a1, a2), b1 + b2 + min(b1, b2)))
        }
        println(ans)
    }
}
```
