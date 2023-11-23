public class Point {
    private float x;
    private float y;
    private float z;

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Point(){

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    void setX(float x) {
        this.x = x;
    }
    void setY(float y) {
        this.y = y;
    }

    void setZ(float z) {
        this.z = z;
    }

}
