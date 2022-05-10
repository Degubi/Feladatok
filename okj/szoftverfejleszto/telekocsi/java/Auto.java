public class Auto {
    public final String indulas;
    public final String cel;
    public final String rendszam;
    public final String telefonszam;
    public final int ferohely;

    public Auto(String sor) {
        var split = sor.split(";");

        indulas = split[0];
        cel = split[1];
        rendszam = split[2];
        telefonszam = split[3];
        ferohely = Integer.parseInt(split[4]);
    }
}