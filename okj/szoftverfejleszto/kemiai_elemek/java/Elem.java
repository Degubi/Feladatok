public class Elem {
    public final int ev;
    public final boolean okor;
    public final String nev;
    public final String vegyjel;
    public final int rendszam;
    public final String felfedezo;

    public Elem(String line) {
        var split = line.split(";");

        ev = getEv(split[0]);
        okor = ev == -1;
        nev = split[1];
        vegyjel = split[2];
        rendszam = Integer.parseInt(split[3]);
        felfedezo = split[4];
    }

    private static int getEv(String evStr) {
        try{
            return Integer.parseInt(evStr);
        }catch (NumberFormatException e) {
            return -1;
        }
    }
}