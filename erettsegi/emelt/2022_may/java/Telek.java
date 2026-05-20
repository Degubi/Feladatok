public class Telek {

    public final int adoszam;
    public final String utcaNev;
    public final String hazSzam;
    public final String adosav;
    public final int terulet;

    public Telek(String[] data) {
        adoszam = Integer.parseInt(data[0]);
        utcaNev = data[1];
        hazSzam = data[2];
        adosav = data[3];
        terulet = Integer.parseInt(data[4]);
    }
}