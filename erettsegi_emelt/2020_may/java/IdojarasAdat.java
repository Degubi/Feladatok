import java.time.*;

public class IdojarasAdat {
        
    public final String telepules;
    public final LocalTime ido;
    public final String szelIrany;
    public final int szelErosseg;
    public final int homerseklet;
    
    public IdojarasAdat(String line) {
        var split = line.split(" ");
        var teljesIdo = Integer.parseInt(split[1]);
        
        telepules = split[0];
        ido = LocalTime.of(teljesIdo / 100, teljesIdo % 100);
        szelIrany = split[2].substring(0, 3);
        szelErosseg = Integer.parseInt(split[2].substring(3));
        homerseklet = Integer.parseInt(split[3]);
    }
}