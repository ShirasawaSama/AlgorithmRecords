public class Tarjan { // 求解无向图的割点与桥
    public void tarjan(int u) {
        dfn[u] = low[u] = ++index;
        stack.addFirst(u);
        isInStack[u] = true;
        if (points[u] != null) for (int i : points[u]) {
            if (dfn[i] == 0) { // 未被访问过
                tarjan(i);
                low[u] = Math.min(low[u], low[i]);
            } else if (isInStack[i]) low[u] = Math.min(low[u], dfn[i]); // 已被访问过且在栈内,则需要处理; 若不在栈内说明对应强连通分量处理完成
        }
        if (dfn[u] == low[u]) { // 发现更新完一轮自己是爸爸(某强连通分量中仅第一个被访问的结点满足dfn[u] == low[u])
            Integer top;
            colors[u] = ++colorId;
            while (!stack.isEmpty() && (top = stack.peekFirst()) != u) {
                isInStack[top] = false;
                colors[top] = colorId;
                stack.pop();
            }
            isInStack[u] = false;
            stack.pop();
            index++;
        }
    }
}
