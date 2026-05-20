public class Telek {
    public readonly bool parosE;
    public readonly int szelesseg;
    public readonly int hazszam;
    public readonly char keritesSzine;

    public Telek(int hazszam, int szelesseg, char keritesSzine) {
        this.parosE = hazszam % 2 == 0;
        this.szelesseg = szelesseg;
        this.keritesSzine = keritesSzine;
        this.hazszam = hazszam;
    }
}