public class Igeny {
    public final String azonosito;
    public final String indulas;
    public final String cel;
    public final int szemelyek;

    public Igeny(String sor) {
        var split = sor.split(";");

        azonosito = split[0];
        indulas = split[1];
        cel = split[2];
        szemelyek = Integer.parseInt(split[3]);
    }
}