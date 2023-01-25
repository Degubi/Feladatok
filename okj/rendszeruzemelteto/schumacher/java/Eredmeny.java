import java.time.*;

public class Eredmeny {

    public final LocalDate datum;
    public final String dijNev;
    public final int helyezes;
    public final int befejezettKorok;
    public final int szerzettPontok;
    public final String csapat;
    public final String vegeredmenyStatusz;

    public Eredmeny(String line) {
        var split = line.split(";");

        this.datum = LocalDate.parse(split[0]);
        this.dijNev = split[1];
        this.helyezes = Integer.parseInt(split[2]);
        this.befejezettKorok = Integer.parseInt(split[3]);
        this.szerzettPontok = Integer.parseInt(split[4]);
        this.csapat = split[5];
        this.vegeredmenyStatusz = split[6];
    }
}