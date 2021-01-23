# Find The Multiple

https://vjudge.net/problem/POJ-1426#author=lcuacm

## 题目

在2100年科学家发现了平行宇宙，但是新发现的Earth2的世界中所有数字都是由0和1组成的十进制数，如果从我们的世界穿越到Earth2，数字将发生一些变化，例如：一个正整数n，将被转化为n的一个非零的倍数m，这个m应当符合Earth2的数字规则。你可以假定n不大于200且m不多于100位。

> 提示：本题采用Special Judge，你无需输出所有符合条件的m，你只需要输出任一符合条件的m即可。

### 输入

输入包含多组数据，每组数据仅一行，只包含一个正整数n，n==0时输入结束 (1 <= n <= 200).

### 输出

对于输入的每组n，都输出任一符合条件的m。即使有多个符合条件的m，你也只需要输出一个即可。(已知long long范围内存在表示数据中所有的m)

### Sample Input

```
2
6
19
0
```

### Sample Output

```
10
100100100100100100
111111111111111111
```

## 题意

给定一个数，找到一个只由0和1组成的数, 并且可以整除给定的数.

## 题解

枚举(dfs和bfs都可以) + 打表(不打表应该也可以)

## 代码

```java
import java.util.*;

public class Main {
    public static void main(string[] args) {
        System.out.println("import java.util.*;\npublic class Main {\npublic static void main(String[] args){\n" +
                "Scanner cin = new Scanner(System.in);\nint n;\nwhile((n = cin.nextInt()) != 0) switch(n){");
        for (int i = 1; i <= 200; i++) {
            final LinkedList<Long> queue = new LinkedList<>();
            queue.add(1L);
            while (true) {
                long it = queue.pop();
                if (it % i == 0) {
                    System.out.println("case " + i + ": System.out.println(\"" + it + "\");break; ");
                    break;
                }
                long next = it * 10L;
                queue.add(next);
                queue.add (next + 1);
                System.out.println("}}}");
            }
        }
    }
}
```
