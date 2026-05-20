public class Telek{

    public final boolean parosE;
    public final int szelesseg;
    public final int hazszam;
    public final char keritesSzine;

    public Telek(int hazszam, int szelesseg, char keritesSzine) {
        this.parosE = hazszam % 2 == 0;
        this.szelesseg = szelesseg;
        this.keritesSzine = keritesSzine;
        this.hazszam = hazszam;
    }
}