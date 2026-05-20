import java.time.*;

public class Athaladas {
    private static int bentlevoSzamlalo = 0;

    public final LocalTime idopont;
    public final int szemelyID;
    public final boolean belepes;
    public final int bentlevok;

    public Athaladas(String sor) {
        var split = sor.split(" ");

        this.idopont = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        this.szemelyID = Integer.parseInt(split[2]);
        this.belepes = split[3].equals("be");
        bentlevoSzamlalo += belepes ? 1 : -1;
        this.bentlevok = bentlevoSzamlalo;
    }
}