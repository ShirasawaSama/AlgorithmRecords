# Oil Deposits

https://vjudge.net/problem/HDU-1241#author=UoU

## 题目

给定一个矩形区域，询问有多少个全为‘@’的连通块

a与b属于同一连通块当且仅当至少满足下列的一个条件:

1. a与b相邻。（当a在以b为中心的8个位置中的一个时, 即左上, 正上, 右上, 正左, 正右, 左下, 正下和右下）
2. a的相邻点与b或b的相邻点属于同一连通块
3. b的相邻点与a或a的相邻点属于同一连通块


### Input

输入可能有多个矩形区域（即可能有多组测试）

每个矩形区域的起始行包含m和n，表示行和列的数量，1<=n,m<=100

当m = 0时，输入结束

接下来是n行，每行m个字符

每个字符对应一个小方格，要么是'*'，代表禁止区域，要么是'@'

### Output

对于每一个矩形区域，输出'@'的连通块数量

### text Input
 
```
1 1
*
3 5
*@*@*
**@**
*@*@*
1 8
@@****@*
5 5 
****@
*@@*@
*@**@
@@@*@
@@**@
0 0 
```

### text Output

```
0
1
2
2
```

## 题解

并查集

### 代码

```java
import java.util.*;

public class Main {
    private static int findParent(int[] arr, int i) {
        while (arr[i] != i) i = arr[i];
        return i;
    }
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int m;
        while ((m = cin.nextInt()) != 0) {
            int n = cin.nextInt(), max = m * n + 1;
            int[] arr = new int[max];
            cin.nextLine();
            for (int i = 0, t = 1; i < m; i++) for (char ch : cin.nextLine().toCharArray()) {
                if (ch == '@') arr[t] = t;
                t++;
            }
            int lastLine = n * (m - 1);
            for (int i = 1; i < max; i++) {
                if (arr[i] == 0) continue;
                int cur = arr[i], tmp = i % n, id;
                boolean top = i > n, bottom = i < lastLine, left = tmp != 1, right = tmp != 0;
                id = i - n - 1;
                if (cur != i) cur = findParent(arr, cur);
                if (left && top && arr[id] != 0 /* LEFT-TOP */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
                id = i - n;
                if (top && arr[id] != 0 /* TOP */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
                id = i + 1 - n;
                if (right && top && arr[id] != 0 /* RIGHT-TOP */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
                id = i - 1;
                if (left && arr[id] != 0 /* LEFT */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
                id = i + 1;
                if (right && arr[id] != 0 /* RIGHT */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
                id = i - 1 + n;
                if (left && bottom && arr[id] != 0 /* LEFT-BOTTOM */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
                id = i + n;
                if (bottom && arr[id] != 0 /* BOTTOM */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
                id = i + 1 + n;
                if (right && bottom && arr[id] != 0 /* RIGHT-BOTTOM */) {
                    int parent = findParent(arr, id);
                    if (cur == i) cur = arr[i] = parent;
                    else arr[parent] = cur;
                }
            }
            HashSet<Integer> set = new HashSet<>();
            int g = 0;
            for (int it : arr) if (it == g++ && it != 0) set.add(it);
            System.out.println(set.size());
        }
    }
}
```
