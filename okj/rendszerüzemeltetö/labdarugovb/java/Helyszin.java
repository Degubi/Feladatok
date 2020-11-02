public class Helyszin {
    public final String varos;
    public final String nev1;
    public final String nev2;
    public final int ferohely;

    public Helyszin(String line) {
        var split = line.split(";");

        this.varos = split[0];
        this.nev1 = split[1];
        this.nev2 = split[2];
        this.ferohely = Integer.parseInt(split[3]);
    }
}