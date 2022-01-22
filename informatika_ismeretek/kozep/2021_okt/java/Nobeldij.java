public class Nobeldij {

    public final int ev;
    public final String nev;
    public final Elethossz elethossz;
    public final String orszagkod;

    public Nobeldij(String line) {
        var split = line.split(";");

        this.ev = Integer.parseInt(split[0]);
        this.nev = split[1];
        this.elethossz = new Elethossz(split[2]);
        this.orszagkod = split[3];
    }
}