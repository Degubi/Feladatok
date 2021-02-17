import java.time.*;

public class Eredmeny {

    public final String hazaiCsapat;
    public final String idegenCsapat;
    public final int hazaiPont;
    public final int idegenPont;
    public final String helyszin;
    public final LocalDate idopont;

    public Eredmeny(String line) {
        var split = line.split(";");

        hazaiCsapat = split[0];
        idegenCsapat = split[1];
        hazaiPont = Integer.parseInt(split[2]);
        idegenPont = Integer.parseInt(split[3]);
        helyszin = split[4];
        idopont = LocalDate.parse(split[5]);
    }
}