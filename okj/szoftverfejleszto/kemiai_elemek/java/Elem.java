public class Elem {
    public static final int OKOR_EV = -1;

    public final int ev;
    public final String nev;
    public final String vegyjel;
    public final int rendszam;
    public final String felfedezo;

    public Elem(String[] data) {
        ev = data[0].equals("Ókor") ? OKOR_EV : Integer.parseInt(data[0]);
        nev = data[1];
        vegyjel = data[2];
        rendszam = Integer.parseInt(data[3]);
        felfedezo = data[4];
    }
}