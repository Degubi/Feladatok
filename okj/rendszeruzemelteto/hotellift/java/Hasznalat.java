import java.time.*;
import java.time.format.*;

public class Hasznalat{
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("uuuu.MM.dd.");
    
    public final LocalDate idopont;
    public final int kartyaSorszam;
    public final int induloSzint;
    public final int celSzint;
    
    public Hasznalat(String sor) {
        var split = sor.split(" ");
        
        idopont = LocalDate.parse(split[0], timeFormat);
        kartyaSorszam = Integer.parseInt(split[1]);
        induloSzint = Integer.parseInt(split[2]);
        celSzint = Integer.parseInt(split[3]);
    }
}