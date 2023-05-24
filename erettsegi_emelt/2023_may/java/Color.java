public class Color {

    public final int red;
    public final int green;
    public final int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int sum() {
        return red + green + blue;
    }

    @Override
    public String toString() {
        return "RGB(" + red + "," + green + "," + blue + ")";
    }
}