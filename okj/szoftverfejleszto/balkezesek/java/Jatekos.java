import java.time.*;

public class Jatekos {
    
    public final String nev;
    public final LocalDate kezdes;
    public final LocalDate befejezes;
    public final int suly;
    public final int magassag;
    
    public Jatekos(String line) {
        var split = line.split(";");
        
        nev = split[0];
        kezdes = LocalDate.parse(split[1]);
        befejezes = LocalDate.parse(split[2]);
        suly = Integer.parseInt(split[3]);
        magassag = Integer.parseInt(split[4]);
    }
}