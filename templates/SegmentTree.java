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
}
