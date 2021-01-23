# Fliptile

https://vjudge.net/problem/POJ-3279#author=1701400204

## 题目

现在有一个矩阵，矩阵大小为M x N. 矩阵中个位置的数值只有0或1, 每次选择一个位置，会使得该位置与他相邻的上下左右位置的数值改变（0变1,1变0）.

问需要至少多少次操作可以使得矩阵中所有的值变为0?

请输出翻转方案，若没有方案，输出 "IMPOSSIBLE"

若有多种方案符合题意，请首先输出翻转次数最少的方案；若方案个数仍不唯一，则输出字典序最小的方案

### Input

第一行输入两个数：M和N。（1 <= M , N <= 15）

接下来M行，每行N个数，其值只为0或1。

### Output

输出M行，每行N个数

每个数代表该位置翻转次数

### Sample Input

```
4 4
1 0 0 1
0 1 1 0
0 1 1 0
1 0 0 1
```

### Sample Output

```
0 0 0 0
1 0 0 1
1 0 0 1
0 0 0 0
```

## 题解

因为它一个格子变了之后周围四个也会变, 所以假设某一行确定了, 如果要把这行的1变为0, 那么就只能下一行的同一纵坐标的格子变.

比方说

```
1 0 1
? ? ?
```

如果要第一行变为 `0 0 0`, 那么第二行的变化次数就是 `1 0 1`.

所以这样话, 就只需要把第一行确定了, 底下的所有行都是确定了的.

因此只需要暴力枚举第一行可能的结果.

然后因为需要按字典序来输出结果, 所以只需要从后往前枚举第一行即可 如 `000`, `001`, `010`, `011`, `100`, `101`, `110`, `111`.

此时只需要最后一行全为0就说明当前操作可行, 并记录下当前操作的数量, 并之前成功的操作比较操作次数, 当发现操作次数更小的就等到最后输出.

## 代码

```java
import java.util.*;

public class Main {
    public static void reverse(int[] arr, int x, int y, boolean moveLeft) {
        int cur = arr[y];
        if (moveLeft) cur ^= x << 1;
        cur ^= x >> 1;
        arr[y] = cur ^ x;
        if (y > 0) arr[y - 1] ^= x;
        if (y < arr.length - 1) arr[y + 1] ^= x;
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNextInt()) {
            final int y = cin.nextInt(), x = cin.nextInt(), xLimit = x - 1;
            int[] arr = new int[y];
            for (int i = 0; i < y; i++) for (int j = 0; j < x; j++) {
                arr[i] <<= 1;
                arr[i] |= cin.nextInt();
            }
            LinkedList<Integer> list = new LinkedList<Integer>();
            list.add(0);
            list.add(1);
            int times = (int) Math.pow(2, x), min = Integer.MAX_VALUE;
            boolean[][] lastResult = null;
            while (times-- > 0) {
                int cur = list.pop(), curCopy = cur, k = 0, count = 0;
                int[] clone = arr.clone();
                boolean[][] result = new boolean[y][x];
                while (cur != 0) {
                    if ((cur & 1) != 0) {
                        count++;
                        result[0][x - k - 1] = true;
                        reverse(clone, 1 << k, 0, k != xLimit);
                    }
                    cur >>= 1;
                    k++;
                }
                for (int i = 1; i < y; i++) {
                    int prev = clone[i - 1];
                    for (int j = 0; j < x; j++) {
                        int c = 1 << j;
                        if ((prev & c) != 0) {
                            count++;
                            result[i][x - j - 1] = true;
                            reverse(clone, c, i, j != xLimit);
                        }
                    }
                }
                if (clone[y - 1] == 0 && min > count) {
                    min = count;
                    lastResult = result;
                }
                if (curCopy == 0) continue;
                list.add(curCopy <<= 1);
                list.add(curCopy | 1);
            }
            if (lastResult == null) System.out.println("IMPOSSIBLE");
            else for (boolean[] a : lastResult) {
                for (int i = 0; i < x; i++) {
                    if (i != 0) System.out.print(' ');
                    System.out.print(a[i] ? 1 : 0);
                }
                System.out.println();
            }
        }
    }
}
```
