public class Versenyzo {
    public final String nev;
    public final String orszag;
    public final double techPont;
    public final double kompPont;
    public final int hibaPont;

    public Versenyzo(String line) {
        var split = line.split(";");

        nev = split[0];
        orszag = split[1];
        techPont = Double.parseDouble(split[2]);
        kompPont = Double.parseDouble(split[3]);
        hibaPont = Integer.parseInt(split[4]);
    }

    public double sumPont() {
        return techPont + kompPont - hibaPont;
    }
}