import java.time.*;

public class Csatlakozas {

    public final String orszag;
    public final LocalDate datum;

    public Csatlakozas(String line) {
        var split = line.split(";");

        orszag = split[0];
        datum = LocalDate.parse(split[1].replace('.', '-'));
    }
}