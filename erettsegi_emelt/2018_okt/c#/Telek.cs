public struct Telek {
    public readonly bool parosE;
    public readonly int szelesseg;
    public readonly int hazszam;
    public readonly char keritesSzine;

    public Telek(bool parosE, int hazszam, int szelesseg, char keritesSzine) {
        this.parosE = parosE;
        this.szelesseg = szelesseg;
        this.keritesSzine = keritesSzine;
        this.hazszam = hazszam;
    }
}