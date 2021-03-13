public class MergeSegment {
    public static ArrayList<int[]> mergeSegment(int[][] list){
        ArrayList<int[]> res = new ArrayList<>();
        int left = Integer.MIN_VALUE, right = left;
        for (int[] it : list) {
            if (right < it[0]) {
                if(left != Integer.MIN_VALUE) res.add(new int[] { left, right });
                left = it[0];
                right = it[1];
            } else if (it[1] > right) right = it[1];
        }
        if(left != Integer.MIN_VALUE) res.add(new int[] { left, right });
        return res;
    }
}
