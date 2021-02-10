package ir.sharif.math.bp99_1.snake_and_ladder.graphic.models;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Snake extends GraphicalModel {
    private final Point2D start; // head
    private final Point2D end;
    private final double bodyWidth;
    private final double waveHeight;
    private final double tailStart;
    private final double waves;
    private final Shape body;
    private final Shape head;
    private final Shape eyeR;
    private final Shape eyeL;
    private final Shape irisR;
    private final Shape irisL;
    private final Color color;

    private Snake(Point2D start, Point2D end
            , double bodyWidth, double waveHeight, double tailStart, double headLength, double headWidth
            , double eyeRadius, double irisRadius, double waves, Color color) {
        this.bodyWidth = bodyWidth;
        this.waveHeight = waveHeight;
        this.tailStart = tailStart;
        this.waves = waves;
        this.start = start;
        this.end = end;
        AffineTransform at = AffineTransform.getRotateInstance(currentAngleRad(), start.getX(), start.getY());
        at.translate(start.getX(), start.getY());
        this.body = createBody(at);
        Shape head = new Ellipse2D.Double(-headLength, -headWidth, headLength + headLength, headWidth + headWidth);
        this.head = at.createTransformedShape(head);
        Shape eyeR = new Ellipse2D.Double(-headLength * 0.5 - eyeRadius, -headWidth * 0.6 - eyeRadius,
                eyeRadius + eyeRadius, eyeRadius + eyeRadius);
        Shape eyeL = new Ellipse2D.Double(-headLength * 0.5 - eyeRadius, headWidth * 0.6 - eyeRadius,
                eyeRadius + eyeRadius, eyeRadius + eyeRadius);
        this.eyeR = at.createTransformedShape(eyeR);
        this.eyeL = at.createTransformedShape(eyeL);
        Shape irisR = new Ellipse2D.Double(-headLength * 0.4 - eyeRadius, -headWidth * 0.6 - irisRadius,
                irisRadius + irisRadius, irisRadius + irisRadius);
        Shape irisL = new Ellipse2D.Double(-headLength * 0.4 - eyeRadius, headWidth * 0.6 - irisRadius,
                irisRadius + irisRadius, irisRadius + irisRadius);
        this.irisR = at.createTransformedShape(irisR);
        this.irisL = at.createTransformedShape(irisL);
        this.color = color;
    }

    public static class SnakeBuilder {
        private Point2D start; // head
        private Point2D end;
        private double bodyWidth = 6;
        private double tailStart = 0.5;
        private double headLength = 12;
        private double headWidth = 12;
        private double eyeRadius = 2.9;
        private double irisRadius = 1.5;
        private double waves = 5;
        private double waveHeight = waves / 200;
        private Color color;

        public SnakeBuilder setColor(ir.sharif.math.bp99_1.snake_and_ladder.model.Color color) {
            switch (color) {
                case RED:
                    this.color = Color.RED;
                    break;
                case BLUE:
                    this.color = Color.BLUE;
                    break;
                case BLACK:
                    this.color = Color.DARK_GRAY;
                    break;
                case GREEN:
                    this.color = Color.GREEN;
                    break;
                case WHITE:
                    this.color = Color.PINK;
                    break;
                case YELLOW:
                    this.color = Color.YELLOW;
                    break;
            }
            return this;
        }

        public SnakeBuilder setStart(double startX, double startY) {
            this.start = new Point2D.Double(startX, startY);
            return this;
        }

        public SnakeBuilder setEnd(double endX, double endY) {
            this.end = new Point2D.Double(endX, endY);
            return this;
        }

        public SnakeBuilder setBodyWidth(double bodyWidth) {
            this.bodyWidth = bodyWidth;
            return this;
        }

        public SnakeBuilder setWaveHeight(double waveHeight) {
            this.waveHeight = waveHeight;
            return this;
        }

        public SnakeBuilder setTailStart(double tailStart) {
            this.tailStart = tailStart;
            return this;
        }

        public SnakeBuilder setHeadLength(double headLength) {
            this.headLength = headLength;
            return this;
        }

        public SnakeBuilder setHeadWidth(double headWidth) {
            this.headWidth = headWidth;
            return this;
        }

        public SnakeBuilder setEyeRadius(double eyeRadius) {
            this.eyeRadius = eyeRadius;
            return this;
        }

        public SnakeBuilder setIrisRadius(double irisRadius) {
            this.irisRadius = irisRadius;
            return this;
        }

        public SnakeBuilder setWaves(int waves) {
            this.waves = waves;
            return this;
        }

        public Snake build() {
            if (start == null || end == null)
                throw new NullPointerException("specify start and end of snake");
            return new Snake(start, end, bodyWidth, waveHeight, tailStart
                    , headLength, headWidth, eyeRadius, irisRadius, waves, color);
        }
    }

    public Color randomColor(Color color) {
        if (color.equals(Color.PINK))
            return color;
        if (color.equals(Color.DARK_GRAY))
            return color;
        float[] HSB = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), HSB);
        Random random = new Random();
        float h = HSB[0] + random.nextFloat() / 20 - 0.025f;
        if (h < 0) h += 1;
        float s = random.nextFloat() / 2 + 0.5f;
        float b = random.nextFloat() / 2 + 0.5f;
        return Color.getHSBColor(h, s, b);
    }

    private transient long lastPaint = 0L;
    private transient Color tempColor;

    public void paint(Graphics2D g) {
        long now = System.currentTimeMillis();
        if (lastPaint == 0L) {
            lastPaint = now;
            tempColor = color;
        }
        if (now - lastPaint > 400) {
            this.tempColor = randomColor(color);
            lastPaint = now;
        }
        g.setColor(tempColor);
        g.fill(body);
        g.fill(head);
        g.setColor(Color.WHITE);
        g.fill(eyeR);
        g.fill(eyeL);
        g.setColor(Color.BLACK);
        g.fill(irisR);
        g.fill(irisL);
    }

    private Shape createBody(AffineTransform at) {
        double distance = end.distance(start);
        int steps = 100;
        Path2D body = new Path2D.Double();
        Point2D previousPoint = null;
        for (int i = 0; i < steps; i++) {
            double alpha = (double) i / (steps - 1);
            Point2D point = computeCenterPoint(alpha, distance);
            if (previousPoint != null) {
                Point2D bodyPoint =
                        computeBodyPoint(alpha, point, previousPoint);
                if (i == 1) {
                    body.moveTo(bodyPoint.getX(), bodyPoint.getY());
                } else {
                    body.lineTo(bodyPoint.getX(), bodyPoint.getY());
                }
            }
            previousPoint = point;
        }
        previousPoint = null;
        for (int i = steps - 1; i >= 0; i--) {
            double alpha = (double) i / (steps - 1);
            Point2D point = computeCenterPoint(alpha, distance);
            if (previousPoint != null) {
                Point2D bodyPoint =
                        computeBodyPoint(alpha, point, previousPoint);
                body.lineTo(bodyPoint.getX(), bodyPoint.getY());
            }
            previousPoint = point;
        }
        return at.createTransformedShape(body);
    }

    private Point2D computeBodyPoint(double alpha, Point2D point, Point2D previousPoint) {
        double dx = point.getX() - previousPoint.getX();
        double dy = point.getY() - previousPoint.getY();
        double d = Math.hypot(dx, dy);
        double localBodyWidth = bodyWidth;
        if (alpha > tailStart) {
            localBodyWidth *= (1 - (alpha - tailStart) / (1.0 - tailStart));
        }
        double px = point.getX() + -dy * (1.0 / d) * localBodyWidth;
        double py = point.getY() + dx * (1.0 / d) * localBodyWidth;
        return new Point2D.Double(px, py);
    }

    private Point2D computeCenterPoint(double alpha, double distance) {
        double r = alpha * Math.PI * 2 * waves;
        double verticalScaling = 1 - (alpha * 2 - 1) * (alpha * 2 - 1);
        double y = Math.sin(r) * distance * waveHeight * verticalScaling;
        double x = alpha * distance;
        return new Point2D.Double(x, y);
    }

    private double currentAngleRad() {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        return Math.atan2(dy, dx);
    }
}