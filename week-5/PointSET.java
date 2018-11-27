import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> set;

    public PointSET() {
        this.set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");
        set.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");
        return set.contains(p);
    }

    public void draw() {
        for (Point2D p: this.set) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("can't be null");

        List<Point2D> points = new ArrayList<>();

        this.set.forEach(p -> {
            if (rect.contains(p)) points.add(p);
        });

        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");
        if (this.set.isEmpty()) return null;

        Point2D k = this.set.first();
        double distance = p.distanceSquaredTo(k);
        Point2D point = k;

        for (Point2D point2D: this.set) {
            double dis = p.distanceSquaredTo(point2D);

            if (dis < distance) {
                distance = dis;
                point = point2D;
            }
        }

        return point;
    }

}

