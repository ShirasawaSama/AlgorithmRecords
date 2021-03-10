# From Zero To Y

https://codeforces.com/contest/1488/problem/A

## 题目

You are given two positive (greater than zero) integers x and y. There is a variable k initially set to 0.

You can perform the following two types of operations:

add 1 to k (i. e. assign `k:=k+1`);

add x⋅10p to k for some non-negative p (i. e. assign `k:=k+x⋅10p` for some p≥0).

Find the minimum number of operations described above to set the value of k to y.

### Input

The first line contains one integer t (1≤t≤2⋅104) — the number of test cases.

Each test case consists of one line containing two integer x and y (1≤x,y≤109).

### Output

For each test case, print one integer — the minimum number of operations to set the value of k to y.

### Example Input

```
3
2 7
3 42
25 1337
```

### Example Output

```
4
5
20
```

### Note

In the first test case you can use the following sequence of operations:

- add 1;
- add 2⋅100=2;
- add 2⋅100=2;
- add 2⋅100=2.

1+2+2+2=7.

In the second test case you can use the following sequence of operations:

- add 3⋅101=30;
- add 3⋅100=3;
- add 3⋅100=3;
- add 3⋅100=3;
- add 3⋅100=3.

30+3+3+3+3=42.

## 题意

给你 `x` 和 `y` 两个数然后可以通过以下两种操作依次使数字从 `y` 一直减少到 0:

- 直接 -1
- 减去 `x` 的 `p` 次方, p为任意非负整数

问最少的操作次数

## 题解

首先最外层循环判断y是否不为0, 然后从0依次枚举 `p`, 直到大于 `y`.

如果 `p` 为非负整数则操作数+1, 同时减去对应的值, 然后继续循环.

否则操作数直接加上 `p` 当前剩余的值, 跳出循环, 输出.

## 代码

```kotlin
import java.util.*
import kotlin.math.pow
 
fun main() {
    val cin = Scanner(System.`in`)
    var n = cin.nextInt()
    while (n-- > 0) {
        val a = cin.nextInt()
        var b = cin.nextLong()
        var times = 0L
        val ww = a.toString().length
        while (b != 0L) {
            times++
            val t = b.toString().length - ww
            val e = 10.0.pow(t).toLong() * a
            if (e <= b && t >= 0) b -= e
            else if (t <= 0) {
                times += b - 1
                break
            } else b -= 10.0.pow(t - 1).toLong() * a
            if (b < 0) break
        }
        println(times)
    }
}
```
