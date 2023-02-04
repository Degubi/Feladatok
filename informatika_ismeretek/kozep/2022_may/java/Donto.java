import java.time.*;
import java.time.format.*;

public class Donto {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

    public final int sorszam;
    public final LocalDate datum;
    public final String gyoztesCsapatNeve;
    public final int gyoztesPontjai;
    public final int vesztesPontjai;
    public final String vesztesCsapatNeve;
    public final String stadion;
    public final String varos;
    public final String allam;
    public final int nezoszam;

    public Donto(String line) {
        var split = line.split(";");
        var arabSzamString = new RomaiSorszam(split[0]).getArabSsz();
        var pontSplit = split[3].split("-");
        var varosAllamSplit = split[6].split(", ");

        sorszam = Integer.parseInt(arabSzamString.substring(0, arabSzamString.length() - 1));
        datum = LocalDate.parse(split[1], DATE_FORMAT);
        gyoztesCsapatNeve = split[2];
        gyoztesPontjai = Integer.parseInt(pontSplit[0]);
        vesztesPontjai = Integer.parseInt(pontSplit[1]);
        vesztesCsapatNeve = split[4];
        stadion = split[5];
        varos = varosAllamSplit[0];
        allam = varosAllamSplit[1];
        nezoszam = Integer.parseInt(split[7]);
    }
}