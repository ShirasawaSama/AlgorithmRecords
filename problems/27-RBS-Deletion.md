# RBS Deletion

https://codeforces.com/contest/1488/problem/B

## 题目

A bracket sequence is a string containing only characters "(" and ")". A regular bracket sequence (or, shortly, an RBS) is a bracket sequence that can be transformed into a correct arithmetic expression by inserting characters "1" and "+" between the original characters of the sequence. For example:

- bracket sequences "()()" and "(())" are regular (the resulting expressions are: "(1)+(1)" and "((1+1)+1)");

- bracket sequences ")(", "(" and ")" are not.

You are given a string s, which is an RBS. You can apply any number of operations to this string. Each operation can have one of the following types:

- choose some non-empty prefix of s and remove it from s, so s is still an RBS. For example, we can apply this operation as follows: "(())()(())()()" → "()()" (the first 10 characters are removed);
- choose some contiguous non-empty substring of s and remove it from s, so s is still an RBS. For example, we can apply this operation as follows: "(())()(())()()" → "(())()()()" (the characters from the 7-th to the 10-th are removed).

The operation 2 can be applied at most k times. Calculate the maximum number of operations you can apply until s becomes empty.

### Input

The first line contains one integer t (1≤t≤105) — the number of test cases.

Each test case is described by two lines. The first line contains two integers n and k (2≤n≤2⋅105; 1≤k≤n; n is even) — the length of s and the maximum number of operations of type 2 you can apply.

The second line contains a string s of n characters '(' and ')'. This string is an RBS.

The sum of n over all test cases doesn't exceed 2⋅105.

### Output

For each test case, print one integer — the maximum number of operations you can apply.

### Example Input

```
3
12 2
(()())((()))
6 3
()()()
8 1
(((())))
```

### Example Ouput

```
4
3
2
```

## 题意

将若干个正确闭环的小括号称为 **RBS**, 如 `()()` 和 `(())`.

然后有两种操作方式对所给的只有若干个只含有 **RBS** 的字符串中的括号进行删除:

1. 删除开头若干个 **RBS** 子字符串
2. 删除中间的若干个 **RBS** 子字符串, 但是有次数限制 (每组第二个数字)

问最多的操作次数为几次可以将整个字符串变为空白.

## 题解

贪心

因为问的是次数最多, 所以每次都尽量删除最小, 同时由于操作2可以实现这样的删除: `()(())()` -> `()()()`. 所以操作2得多用!

然后就依次从开头遍历字符串, 计算出总共的 **RBS** 有多少个, 同时也要计算出操作2最多可以删除掉多少个小括号组 `()`, 这里直接计算有多少个被嵌套的括号即可.

如 `()(())(())(())` 的上述两个值为: `4` 和 `3`.

最后用最多能进行的操作2的数量 (跟给定的最大值比较以下, 取最小值) 加上上述第一个值即可.

## 代码

```kotlin
import java.util.*
 
fun main() {
    val cin = Scanner(System.`in`)
    var n = cin.nextInt()
    while (n-- > 0) {
        val a = cin.nextInt()
        val b = cin.nextInt()
        cin.nextLine()
        val s = cin.nextLine().substring(0 until a)
        var times = 0
        var i = 0
        val len = s.length
        var t = 0
        var y = 0
        while (i < len) {
            if (s[i] == '(') {
                t++
            } else {
                t--
                if (t != 0) y++
            }
            if (t == 0) times++
            i++
        }
        println(y.coerceAtMost(b) + times)
    }
}
```
