# Adrien and Austin

https://vjudge.net/problem/Gym-101981A#author=SWUN2019

## 题目

给你两个数n, k. n表示一个编号由1到n的的序列. 2个人开始轮流从序列中选择一个最小为1, 最多为k的连续的数. 谁没法取数就输了比赛.

给定n和k, 输出谁能获胜

Adrien先手, Austin后手

输出获胜的人名

### 提示

假设n=5, k=3
序列为1, 2, 3, 4, 5
假设第一个人选择了2, 3, 4这3个连续的数
那么第二个的策略集只有选择1或者选择, 不能同时选择是因为这2个数不连续

n 的取值范围为(0<= n <= 1e9)
k 的取值范围为(1<=k<=max(n,1))

### input1

```
1 1
```

### output1

```
Adrien
```

### input2

```
9 3
```

### output2

```
Adrien
```

## 题解

![](https://user-images.githubusercontent.com/17093811/105572676-99646000-5d93-11eb-92d1-1433df9c6544.png)

## 代码

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNextInt()) {
            int a = cin.nextInt(), b = cin.nextInt();
            System.out.println(a == 0
                    ? "Austin"
                    : b == 1
                    ? a % 2 == 1 ? "Adrien" : "Austin"
                    : "Adrien");
        }
    }
}
```
