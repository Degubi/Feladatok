public class Dolgozo {

    public final String nev;
    public final String nem;
    public final String munkaReszleg;
    public final int munkabaLepesEv;
    public final int munkaBer;

    public Dolgozo(String line) {
        var split = line.split(";");

        nev = split[0];
        nem = split[1];
        munkaReszleg = split[2];
        munkabaLepesEv = Integer.parseInt(split[3]);
        munkaBer = Integer.parseInt(split[4]);
    }
}