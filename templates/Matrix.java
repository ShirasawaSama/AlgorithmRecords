class Matrix {
    public static long[][] pow(long[][] x, long y) {
        int len = x.length;
        long[][] ans = new long[len][len];
        for (int i = 0; i < len; i++) ans[i][i] = 1;
        while (y != 0) {
            if((y & 1) == 1) ans = mul(ans, x);
            x = mul(x,x);
            y >>= 1;
        }
        return ans;
    }

    public static long[][] mul(long[][] a, long[][] b) {
        int len1 = a.length, len2 = b[0].length, len3 = b.length;
        long[][] ans = new long[len1][len2];
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                for (int k = 0; k < len3; k++) ans[i][j] = ans[i][j] + a[i][k] * b[k][j];
                ans[i][j] %= 10000007;
            }
        }
        return ans;
    }
}
