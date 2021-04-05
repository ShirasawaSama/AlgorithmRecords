class Geometry { // 几何
    static final class Point {
        final double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        Point add(Point it) { return new Point(x + it.x, y + it.y); }
        Point minus(Point it) { return new Point(x - it.x, y - it.y); }
        Point times(double it) { return new Point(x * it, y * it); }
        double times(Point it) { return x * it.x + y * it.y; }
        double cross(Point it) { return x * it.y - y * it.x; }
        double distanceSquare() { return x * x + y * y; }
        double distance() { return Math.sqrt(distanceSquare()); }
        double distanceSquareTo(Point it) { return minus(it).distanceSquare(); }
        double distanceTo(Point it) { return minus(it).distance(); }
        double distanceTo(Line it) {
            Point p = it.toVector();
            return Math.abs(p.cross(minus(it.a)) / p.distance());
        }
        boolean equals(Point it) { return Math.abs(cross(it)) <= 1e-8; }

        double angle(Point it) { return Math.acos(times(it) / distance() / it.distance()); } // 夹角
        Point normal() { // 单位法向量
            double dis = distance();
            return new Point(-y / dis, x / dis);
        }
    }

    static class Line {
        final Point a, b;
        Line(Point a, Point b) {
            this.a = a;
            this.b = b;
        }
        Point intersection(Line it) { return a.add(b.times(it.b.cross(a.minus(it.a)) / b.cross(it.b))); }
        Point toVector() { return b.minus(a); }
    }
}
