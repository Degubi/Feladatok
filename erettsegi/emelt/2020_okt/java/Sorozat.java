import java.time.*;
import java.util.*;

public class Sorozat {
    public static final LocalDate HIANYZO_DATUM = LocalDate.MIN;

    public final LocalDate adasbaKerulesiDatum;
    public final String cim;
    public final int evadokSzama;
    public final int epizodokSzama;
    public final int epizodonkentiHossz;
    public final boolean lattaEMarAKeszito;

    public Sorozat(List<String> lines, int index) {
        var datumStr = lines.get(index);
        var epizodInfoSplit = lines.get(index + 2).split("x");

        adasbaKerulesiDatum = datumStr.equals("NI") ? HIANYZO_DATUM : LocalDate.parse(datumStr.replace('.', '-'));
        cim = lines.get(index + 1);
        evadokSzama = Integer.parseInt(epizodInfoSplit[0]);
        epizodokSzama = Integer.parseInt(epizodInfoSplit[1]);
        epizodonkentiHossz = Integer.parseInt(lines.get(index + 3));
        lattaEMarAKeszito = Integer.parseInt(lines.get(index + 4)) == 1;
    }
}