import java.time.*;

public class Kolcsonzes {

    public final String nev;
    public final String jarmuAzonosito;
    public final LocalTime elvitelIdopont;
    public final LocalTime visszahozatalIdopont;

    public Kolcsonzes(String line) {
        var split = line.split(";");

        nev = split[0];
        jarmuAzonosito = split[1];
        elvitelIdopont = LocalTime.of(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
        visszahozatalIdopont = LocalTime.of(Integer.parseInt(split[4]), Integer.parseInt(split[5]));
    }
}