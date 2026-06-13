package Capitolul_08;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class AlgoritmiAcoperireConvexa {

    public static class Point {
        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static double ccw(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }

    public static List<Point> grahamScan(List<Point> points) {
        if (points.size() < 3) return new ArrayList<>(points);
        List<Point> pts = new ArrayList<>(points);
        Point p0 = pts.get(0);
        for (Point p : pts) {
            if (p.y < p0.y || (p.y == p0.y && p.x < p0.x)) {
                p0 = p;
            }
        }
        final Point lowest = p0;
        pts.remove(lowest);
        Collections.sort(pts, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                double val = ccw(lowest, p1, p2);
                if (val == 0) {
                    double d1 = Math.pow(p1.x - lowest.x, 2) + Math.pow(p1.y - lowest.y, 2);
                    double d2 = Math.pow(p2.x - lowest.x, 2) + Math.pow(p2.y - lowest.y, 2);
                    return Double.compare(d1, d2);
                }
                return val > 0 ? -1 : 1;
            }
        });
        Stack<Point> stack = new Stack<>();
        stack.push(lowest);
        stack.push(pts.get(0));
        stack.push(pts.get(1));
        for (int i = 2; i < pts.size(); i++) {
            Point pi = pts.get(i);
            while (stack.size() > 1) {
                Point top = stack.pop();
                Point nextToTop = stack.peek();
                if (ccw(nextToTop, top, pi) > 0) {
                    stack.push(top);
                    break;
                }
            }
            stack.push(pi);
        }
        return new ArrayList<>(stack);
    }

    public static double distanceLinePoint(Point a, Point b, Point p) {
        return Math.abs((b.y - a.y) * p.x - (b.x - a.x) * p.y + b.x * a.y - b.y * a.x);
    }

    public static void findHull(List<Point> sk, Point p, Point q, List<Point> hull) {
        if (sk.isEmpty()) return;
        Point farthest = null;
        double maxDist = -1;
        for (Point pt : sk) {
            double dist = distanceLinePoint(p, q, pt);
            if (dist > maxDist) {
                maxDist = dist;
                farthest = pt;
            }
        }
        int insertIdx = hull.indexOf(q);
        hull.add(insertIdx, farthest);
        List<Point> s1 = new ArrayList<>();
        List<Point> s2 = new ArrayList<>();
        for (Point pt : sk) {
            if (pt == farthest) continue;
            if (ccw(p, farthest, pt) > 0) {
                s1.add(pt);
            } else if (ccw(farthest, q, pt) > 0) {
                s2.add(pt);
            }
        }
        findHull(s1, p, farthest, hull);
        findHull(s2, farthest, q, hull);
    }

    public static List<Point> quickHull(List<Point> points) {
        if (points.size() < 3) return new ArrayList<>(points);
        List<Point> hull = new ArrayList<>();
        Point minX = points.get(0);
        Point maxX = points.get(0);
        for (Point p : points) {
            if (p.x < minX.x) minX = p;
            if (p.x > maxX.x) maxX = p;
        }
        hull.add(minX);
        hull.add(maxX);
        List<Point> s1 = new ArrayList<>();
        List<Point> s2 = new ArrayList<>();
        for (Point p : points) {
            if (p == minX || p == maxX) continue;
            if (ccw(minX, maxX, p) > 0) {
                s1.add(p);
            } else if (ccw(maxX, minX, p) > 0) {
                s2.add(p);
            }
        }
        findHull(s1, minX, maxX, hull);
        findHull(s2, maxX, minX, hull);
        return hull;
    }

    public static List<Point> giftWrapping(List<Point> points) {
        if (points.size() < 3) return new ArrayList<>(points);
        List<Point> hull = new ArrayList<>();
        Point minX = points.get(0);
        for (Point p : points) {
            if (p.x < minX.x) {
                minX = p;
            }
        }
        Point pointOnHull = minX;
        do {
            hull.add(pointOnHull);
            Point endpoint = points.get(0);
            for (int j = 1; j < points.size(); j++) {
                Point candidate = points.get(j);
                if (endpoint == pointOnHull || ccw(pointOnHull, endpoint, candidate) > 0) {
                    endpoint = candidate;
                }
            }
            pointOnHull = endpoint;
        } while (pointOnHull != minX);
        return hull;
    }

    public static void main(String[] args) {
        List<Point> points = new ArrayList<>();
        points.add(new Point(0, 3));
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(4, 4));
        points.add(new Point(0, 0));
        points.add(new Point(1, 2));
        points.add(new Point(3, 1));
        points.add(new Point(3, 3));

        System.out.println("Puncte date: " + points);

        List<Point> chGraham = grahamScan(points);
        System.out.println("Graham Scan Convex Hull: " + chGraham);

        List<Point> chQuick = quickHull(points);
        System.out.println("Quickhull Convex Hull:   " + chQuick);

        List<Point> chWrapping = giftWrapping(points);
        System.out.println("Gift Wrapping Convex Hull: " + chWrapping);
    }
}
