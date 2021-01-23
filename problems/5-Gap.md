# Gap

https://vjudge.net/problem/HDU-1067

## 题目

Let's play a card game called Gap.

You have 28 cards labeled with two-digit numbers. The first digit (from 1 to 4) represents the suit of the card, and the second digit (from 1 to 7) represents the value of the card.

First, you shu2e the cards and lay them face up on the table in four rows of seven cards, leaving a space of one card at the extreme left of each row. The following shows an example of initial layout.

![](https://user-images.githubusercontent.com/17093811/105573439-8e5ffe80-5d98-11eb-9223-2fc52a199499.png)

Next, you remove all cards of value 1, and put them in the open space at the left end of the rows: "11" to the top row, "21" to the next, and so on.

Now you have 28 cards and four spaces, called gaps, in four rows and eight columns. You start moving cards from this layout.

![](https://user-images.githubusercontent.com/17093811/105573442-91f38580-5d98-11eb-8232-a74f639771b0.png)

At each move, you choose one of the four gaps and fill it with the successor of the left neighbor of the gap. The successor of a card is the next card in the same suit, when it exists. For instance the successor of "42" is "43", and "27" has no successor.

In the above layout, you can move "43" to the gap at the right of "42", or "36" to the gap at the right of "35". If you move "43", a new gap is generated to the right of "16". You cannot move any card to the right of a card of value 7, nor to the right of a gap.

The goal of the game is, by choosing clever moves, to make four ascending sequences of the same suit, as follows.

![](https://user-images.githubusercontent.com/17093811/105573455-b2234480-5d98-11eb-8bcd-993df584323a.png)

Your task is to find the minimum number of moves to reach the goal layout.

### Input

The input starts with a line containing the number of initial layouts that follow.

Each layout consists of five lines - a blank line and four lines which represent initial layouts of four rows. Each row has seven two-digit numbers which correspond to the cards.

### Output

For each initial layout, produce a line with the minimum number of moves to reach the goal layout. Note that this number should not include the initial four moves of the cards of value 1. If there is no move sequence from the initial layout to the goal layout, produce "-1".

### Sample Input

```
4

12 13 14 15 16 17 21
22 23 24 25 26 27 31
32 33 34 35 36 37 41
42 43 44 45 46 47 11

26 31 13 44 21 24 42
17 45 23 25 41 36 11
46 34 14 12 37 32 47
16 43 27 35 22 33 15

17 12 16 13 15 14 11
27 22 26 23 25 24 21
37 32 36 33 35 34 31
47 42 46 43 45 44 41

27 14 22 35 32 46 33
13 17 36 24 44 21 15
43 16 45 47 23 11 26
25 37 41 34 42 12 31
```

### Sample Output

```
0
33
60
-1
```

## 题意

给一个4*7的矩阵，然后把11,21,31,41移动到最前面，他们的位置都用空格表示

每次移动，只能是用一个数与空格的位置交换，交换的条件是，这个数必须是空格所在位置之前一个数+1的值

最终使这个矩阵变成:

![](https://user-images.githubusercontent.com/17093811/105573455-b2234480-5d98-11eb-8bcd-993df584323a.png)

问最少的移动次数(最开始移动的11,21,32,41不算), 如果没法移动成上面那样就输出-1

## 题解

bfs, 每次都拿空格前面那个数+1进行移动, 然后再看看什么时候移动成功就输出

> 注意: 需要使用哈希来记录每一次矩阵的布局以防止重复搜索

## 代码

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int all = cin.nextInt();
        loop: while (all-- > 0) {
            byte[] arr = new byte[32];
            int[] empty = new int[4];
            for (int i = 0, t = 0; i < 4; i++) for (int j = 1; j <= 7; j++) {
                byte it = cin.nextByte();
                int index = i * 8 + j;
                if (it % 10 == 1) empty[t++] = index; else arr[index] = it;
            }
            arr[0] = 11;
            arr[8] = 21;
            arr[16] = 31;
            arr[24] = 41;
            boolean flag = true;
            for (int i = 1; i <= 32; i++) {
                byte tmp = arr[i - 1];
                if (tmp != 0 && (tmp / 10 != i / 8 + 1 || tmp % 10 != i % 8)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println(0);
                continue;
            }
            LinkedList<Object[]> list = new LinkedList<>();
            list.add(new Object[] { arr, empty, 0 });
            HashSet<Integer> set = new HashSet<>();
            while (!list.isEmpty()) {
                Object[] elm = list.pop();
                byte[] curArr = (byte[]) elm[0];
                int[] curEmpty = (int[]) elm[1];
                int times = (int) elm[2] + 1;
                for (int j = 0; j < 4; j++) {
                    int curEmptyIndex = curEmpty[j];
                    if (curEmptyIndex == 0) continue;
                    byte curEmptyBefore = (byte) (curArr[curEmptyIndex - 1] + 1);
                    if (curEmptyBefore == 1 || curEmptyBefore % 10 == 8) continue;
                    int index = 0;
                    for (byte it : curArr) {
                        if (it == curEmptyBefore) break;
                        index++;
                    }
                    if (index == 32) continue;
                    byte value = curArr[index];
                    curArr[index] = 0;
                    curArr[curEmptyIndex] = value;
                    boolean finished = true;
                    int hash = 1;
                    for (int i = 1; i <= 32; i++) {
                        byte tmp = curArr[i - 1];
                        hash = 31 * hash + tmp;
                        if (tmp != 0 && (tmp / 10 != i / 8 + 1 || tmp % 10 != i % 8)) finished = false;
                    }
                    if (finished) {
                        System.out.println(times);
                        continue loop;
                    }
                    if (!set.contains(hash)) {
                        set.add(hash);
                        int[] clonedEmpty = curEmpty.clone();
                        clonedEmpty[j] = index;
                        list.add(new Object[] { curArr.clone(), clonedEmpty, times });
                    }
                    curArr[curEmptyIndex] = 0;
                    curArr[index] = value;
                }
            }
            System.out.println(-1);
        }
    }
}
```
