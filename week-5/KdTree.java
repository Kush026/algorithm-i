import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private static final int DIM = 2;
    private int size;
    private Node root;
    private Node nearestNode;
    private double min;

    private class Node {
        Point2D point;
        Node left;
        Node right;
        int axis; // 0 for horizontal axis and 1 for vertical axis

        Node(Point2D point, int axis) {
            this.point = point;
            this.axis = axis;
        }
    }

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return size;
    }

    private Node findParent(Point2D p) {

        if (p == null) throw new IllegalArgumentException("Point can't be null");

        Node parent = this.root;
        Node current = this.root;

        while (current != null) {
            parent = current;

            if (current.point.equals(p)) return current;

            if (current.axis == 0) {
                if (p.y() < current.point.y()) current = current.left;
                else current = current.right;
            }
            else if (current.axis == 1) {
                if (p.x() < current.point.x()) current = current.left;
                else current = current.right;
            }
        }

        return parent;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");

        Node parent = findParent(p);

        if (parent == null) this.root = new Node(p, 1);
        else {
            if (parent.point.equals(p)) return;
            Node child = new Node(p, (parent.axis+1) % KdTree.DIM);

            if (parent.axis == 0) {
                if (p.y() < parent.point.y()) parent.left = child;
                else parent.right = child;
            }
            else if (parent.axis == 1) {
                if (p.x() < parent.point.x()) parent.left = child;
                else parent.right = child;
            }
        }
        this.size++;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");
        return contains(p, root);
    }

    private boolean contains(Point2D p, Node node) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;

        if (node.axis == 1) {
            if (p.x() < node.point.x()) return contains(p, node.left);
            else return contains(p, node.right);
        }
        else {
            if (p.y() < node.point.y()) return contains(p, node.left);
            else return contains(p, node.right);
        }
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;
        node.point.draw();
        draw(node.left);
        draw(node.right);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("can't be null");
        List<Point2D> points = new ArrayList<>();
        pointsCollector(points, root, rect);
        return points;
    }

    private void pointsCollector(List<Point2D> collection, Node node, RectHV rect) {
        if (node == null) return;
        if (rect.contains(node.point)) collection.add(node.point);

        if (node.axis == 0) {
            if (rect.ymin() > node.point.y()) pointsCollector(collection, node.right, rect);
            else if (rect.ymax() < node.point.y()) pointsCollector(collection, node.left, rect);
            else {
                pointsCollector(collection, node.left, rect);
                pointsCollector(collection, node.right, rect);
            }
        }
        else if (node.axis == 1) {
            if (rect.xmax() < node.point.x()) pointsCollector(collection, node.left, rect);
            else if (rect.xmin() > node.point.x()) pointsCollector(collection, node.right, rect);
            else {
                pointsCollector(collection, node.left, rect);
                pointsCollector(collection, node.right, rect);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point can't be null");

        if (this.isEmpty()) return null;
        this.min = p.distanceSquaredTo(this.root.point);
        this.nearestNode = this.root;

        nearest(this.root, p);

        return nearestNode.point;
    }

    private void nearest(Node node, Point2D p) {
        if (node == null) return;

        double d = p.distanceSquaredTo(node.point);

        if (d < min) {
            min = d;
            nearestNode = node;
        }

        double dp;

        if (node.axis == 0) dp = Math.abs(p.y()-node.point.y());
        else dp = Math.abs(p.x()-node.point.x());

        if (dp*dp < min) {
            nearest(node.left, p);
            nearest(node.right, p);
        }
        else {
            if (node.axis == 0) {
                if (p.y() < node.point.y()) nearest(node.left, p);
                else nearest(node.right, p);
            }
            else if (node.axis == 1) {
                if (p.x() < node.point.x()) nearest(node.left, p);
                else nearest(node.right, p);
            }
        }
    }
}