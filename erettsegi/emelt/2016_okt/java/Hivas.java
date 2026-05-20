import java.time.*;

public class Hivas {
    private static int sorszamSzamlalo = 0;

    public final LocalTime kezdet;
    public final LocalTime veg;
    public final int sorszam;

    public Hivas(String sor) {
        var split = sor.split(" ");

        kezdet = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        veg = LocalTime.of(Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]));
        sorszam = ++sorszamSzamlalo;
    }
}