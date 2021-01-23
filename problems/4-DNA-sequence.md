# DNA sequence

https://vjudge.net/problem/HDU-1560

## 题目

21世纪是生物科技飞速发展的时代。我们都知道基因是由DNA组成的，而DNA的基本组成单位是A,C,G,T。在现代生物分子计算中，如何找到DNA之间的最长公共子序列是一个基础性问题

但是我们的问题不是那么简单：现在我们给定了数个DNA序列，请你构造出一个最短的DNA序列，使得所有我们给定的DNA序列都是它的子序列

例如，给定"ACGT","ATGC","CGTT","CAGT",你可以构造的一个最短序列为"ACAGTGCT"，但是需要注意的是，这并不是此问题的唯一解

![](https://user-images.githubusercontent.com/17093811/105573353-dcc0cd80-5d97-11eb-860a-e191bb22b9fd.png)

### 输入

第一行含有一个数t，代表数据组数

每组数据的第一行是一个数n，代表给定的DNA序列数量；接下来的n行每行一个字符串s，代表给定的n个DNA序列

1<=n<=8，1<=|s|<=5

### 输出

对于每一组数据，输出一行中含有一个数，代表满足条件的最短序列的长度

### 样例输入

```
1
4
ACGT
ATGC
CGTT
CAGT
```

### 样例输出

```
8
```

## 题意

给你一堆（1~8条）只有四种字符的字符串，每条字符串长度是1~5

字符串中间可以插入任意空格，然后拼合成一个无空格的长串，问这个长串最短是多长

## 题解

神秘的dfs

搜索的时候需要对深度进行不断尝试.

首先读取数据进来的时候, 先设置深度为最长的字符串长度 （当前深度最深为最长的字符串长度）

然后逐次让深度+1尝试能否完成匹配完字符串

当某个深度完成匹配就可以输出

## 代码

```java
import java.util.*;

public class Main {
    private static int deep;
    private static byte[][] arr = null;
    private static int[] indexes = null;
    private static boolean dfs(int currentDeep) { // 匹配完成返回 true
        int maxRemains = 0, len = arr.length;
        for (int i = 0; i < len; i++) {
            int tmp = arr[i].length - 1 - indexes[i]; // 获取还剩下的最长的串长度
            if (tmp > maxRemains) maxRemains = tmp;
        }
        if (maxRemains + currentDeep > deep) return false; // 如果已经使用长度 + 还可以使用的最长长度 > 当前允许的迭代深度, 则继续迭代
        if (maxRemains == 0) return true; // 如果已经完成了匹配则不让继续匹配
        for (byte i = 1; i <= 4; i++) {
            boolean matched = false;
            int[] clonedIndexes = null;
            for (int j = 0; j < len; j++){ // 对全部的 DNA 片段进行扫描
                if (arr[j][indexes[j]] == i) { // 如果匹配到了
                    if (!matched) {
                        clonedIndexes = indexes.clone();
                        matched = true;
                    }
                    indexes[j]++;
                }
            }
            if (matched) { // 有符合的则向下搜索
                if (dfs(currentDeep + 1)) return true;
                else indexes = clonedIndexes; //没有匹配则还原之前的状态
            }
        }
        return false;
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        while (all-- > 0) {
            int len = cin.nextInt();
            cin.nextLine();
            arr = new byte[len][];
            indexes = new int[len];
            deep = 0;
            for (int j = 0; j < len; j++) {
                char[] chars = cin.nextLine().toCharArray();
                int m = chars.length;
                if (m > deep) deep = m;
                byte[] a = arr[j] = new byte[m + 1];
                for (int i = 0; i < m; i++) switch (chars[i]) {
                    case 'A':
                        a[i] = 1;
                        break;
                    case 'C':
                        a[i] = 2;
                        break;
                    case 'G':
                        a[i] = 3;
                        break;
                    case 'T':
                        a[i] = 4;
                }
            }
            while (!dfs(0)) deep++;
            System.out.println(deep);
        }
    }
}
```
