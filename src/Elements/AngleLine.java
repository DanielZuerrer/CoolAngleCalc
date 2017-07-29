package Elements;

import javafx.scene.shape.Line;

public class AngleLine {

    protected Line line;

    protected double rotation = 0;

    protected double baseAngle;
    protected double adj = 1;
    protected double opp;

    public AngleLine(double baseAngle) {
        this.baseAngle = baseAngle;
        this.opp = Math.tan(Math.toRadians(this.baseAngle));
    }

    public void rotate(double rotation) {
        this.rotation = rotation;
        line.setRotate(rotation + baseAngle);
    }

    public void flip() {
        baseAngle = 180 - baseAngle;
        rotate(rotation);
    }

    public void setLine(Line line) {
        this.line = line;
        rotate(0);
    }
}
