public struct Color {

    public readonly int red;
    public readonly int green;
    public readonly int blue;

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int Sum() {
        return red + green + blue;
    }

    public override string ToString() {
        return "RGB(" + red + "," + green + "," + blue + ")";
    }
}