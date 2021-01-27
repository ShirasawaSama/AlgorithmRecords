public class SegmentTree {
    public static class SegmentTree1 {
        private final BiFunction<Integer, Integer, Integer> merger;
        private final int[] tree, lazy;
        private final int max;

        public SegmentTree1(int[] arr, BiFunction<Integer, Integer, Integer> merger) {
            this.merger = merger;
            max = arr.length - 1;
            tree = new int[arr.length * 4];
            lazy = new int[arr.length * 4];
            constructSTUtil(arr, 0, max, 0);
        }

        private void updateRangeUtil(int si, int ss, int se, int us, int ue, int diff) {
            if (lazy[si] != 0) {
                tree[si] += (se - ss + 1) * lazy[si];
                if (ss != se) {
                    lazy[si * 2 + 1] += lazy[si];
                    lazy[si * 2 + 2] += lazy[si];
                }
                lazy[si] = 0;
            }
            if (ss > se || ss > ue || se < us) return;
            if (ss >= us && se <= ue) {
                tree[si] += (se - ss + 1) * diff;
                if (ss != se) {
                    lazy[si * 2 + 1] += diff;
                    lazy[si * 2 + 2] += diff;
                }
                return;
            }
            int mid = (ss + se) / 2;
            updateRangeUtil(si * 2 + 1, ss, mid, us, ue, diff);
            updateRangeUtil(si * 2 + 2, mid + 1, se, us, ue, diff);
            tree[si] = merger.apply(tree[si * 2 + 1], tree[si * 2 + 2]);
        }

        public void updateRange(int us, int ue, int diff) {
            updateRangeUtil(0, 0, max, us, ue, diff);
        }

        private int getSumUtil(int ss, int se, int qs, int qe, int si) {
            if (lazy[si] != 0) {
                tree[si] += (se - ss + 1) * lazy[si];
                if (ss != se) {
                    lazy[si * 2 + 1] += lazy[si];
                    lazy[si * 2 + 2] += lazy[si];
                }
                lazy[si] = 0;
            }
            if (ss > se || ss > qe || se < qs) return 0;
            if (ss >= qs && se <= qe) return tree[si];
            int mid = (ss + se) / 2;
            return merger.apply(getSumUtil(ss, mid, qs, qe, 2 * si + 1),
                    getSumUtil(mid + 1, se, qs, qe, 2 * si + 2));
        }

        public int getSum(int qs, int qe) {
            if (qs < 0 || qe > max || qs > qe) throw new IllegalArgumentException("Argument error!");
            return getSumUtil(0, max, qs, qe, 0);
        }

        private void constructSTUtil(int[] arr, int ss, int se, int si) {
            if (ss > se) return;
            if (ss == se) {
                tree[si] = arr[ss];
                return;
            }
            int mid = (ss + se) / 2;
            constructSTUtil(arr, ss, mid, si * 2 + 1);
            constructSTUtil(arr, mid + 1, se, si * 2 + 2);
            tree[si] = merger.apply(tree[si * 2 + 1], tree[si * 2 + 2]);
        }
    }

    public static class SegmentTree2<E> {
        private final BiFunction<E, E, E> merger;
        private final E[] tree, data;
        @SuppressWarnings("unchecked")
        public SegmentTree2(E[] arr, BiFunction<E, E, E> merger) {
            this.merger = merger;
            data = arr;
            tree = (E[]) new Object[4 * arr.length];
            buildSegmentTree(0, 0, data.length - 1);
        }
        private void buildSegmentTree(int treeIndex, int l, int r) {
            if(l == r) {
                tree[treeIndex] = data[l];
                return;
            }
            int leftTreeIndex = leftChild(treeIndex);
            int rightTreeIndex = rightChild(treeIndex);
            int mid = l + (r - l) / 2;
            buildSegmentTree(leftTreeIndex, l, mid);
            buildSegmentTree(rightTreeIndex, mid + 1, r);
            tree[treeIndex] = merger.apply(tree[leftTreeIndex], tree[rightTreeIndex]);
        }
        public E get(int index) {
            if (index < 0 || index >= data.length) throw new IllegalArgumentException("Index is illegal.");
            return data[index];
        }
        public int getSize() { return data.length; }
        private int leftChild(int index) { return 2 * index + 1; }
        private int rightChild(int index) { return 2 * index + 2; }

        public E query(int queryL, int queryR) {
            if(queryL < 0 || queryL >= data.length || queryR < queryL) throw new IllegalArgumentException("Index is illegal.");
            return query(0, 0, data.length -1, queryL, queryR);
        }
        // 查询在以treeID为根的线段树中[l...r]的范围里，搜索区间[queryL...queryR]的值
        private E query(int treeIndex, int l, int r, int queryL, int queryR) {
            if(l == queryL && r == queryR) return tree[treeIndex];
            int mid = l + (r-l)/2;
            int leftTreeIndex = leftChild(treeIndex);
            int rightTreeIndex = rightChild(treeIndex);
            if(queryL >= mid +1) {
                return query(rightTreeIndex, mid + 1, r, queryL, queryR);
            }else if(queryR <= mid) {
                return query(leftTreeIndex, l, mid, queryL, queryR);
            }else {
                E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
                E rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
                return merger.apply(leftResult, rightResult);
            }
        }
        public void set(int index, E e) {
            if (index < 0 || index >= data.length) throw new IllegalArgumentException("Index is illegal");
            data[index] = e;
            set(0,0, data.length - 1, index, e);
        }
        private void set(int treeIndex, int l, int r, int index, E e) {
            if(l == r) {
                tree[treeIndex] = e;
                return;
            }
            int mid = l + (r - l) / 2;
            int leftTreeIndex = leftChild(treeIndex);
            int rightTreeIndex = rightChild(treeIndex);
            if (index >= mid + 1) {
                set(rightTreeIndex, mid + 1, r, index, e);
            } else {
                set(leftTreeIndex, l, mid, index, e);
            }
            tree[treeIndex] = merger.apply(tree[leftTreeIndex], tree[rightTreeIndex]);
        }
    }
}
