import java.time.*;
import java.time.format.*;

public class Pilota {
    public static final int URES_RAJTSZAM = -1;
    private static final DateTimeFormatter DATUM_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public final String nev;
    public final LocalDate szuletesiDatum;
    public final String nemzetiseg;
    public final int rajtszam;

    public Pilota(String line) {
        var split = line.split(";");

        nev = split[0];
        szuletesiDatum = LocalDate.parse(split[1], DATUM_FORMAT);
        nemzetiseg = split[2];
        rajtszam = line.charAt(line.length() - 1) == ';' ? URES_RAJTSZAM : Integer.parseInt(split[3]);
    }
}