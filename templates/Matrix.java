class Matrix {
    public static void mul(long[][] a, long[][] b) {
        for (int i = 0, len = b.length; i < len; i++) {
            for (int j = 0; j < len; j++) {
                for (int k = 0; k < len; k++) {
                    a[i][j] = (a[i][j] + a[i][k] * a[k][j]);
                }
                a[i][j] %= 10000007;
            }
        }
    }
}
