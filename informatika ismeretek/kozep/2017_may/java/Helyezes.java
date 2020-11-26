public class Helyezes {
    public final int helyezes;
    public final int sportolokSzama;
    public final String sportag;
    public final String versenyszam;

    public Helyezes(String line) {
        var split = line.split(" ");

        helyezes = Integer.parseInt(split[0]);
        sportolokSzama = Integer.parseInt(split[1]);
        sportag = split[2];
        versenyszam = split[3];
    }

    public int pontCalc() { return helyezes == 1 ? 7 : 7 - helyezes; }
}