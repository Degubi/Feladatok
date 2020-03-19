import java.time.*;

public class FuvarAdat{
    public final int azonosito;
    public final LocalDateTime indulas;
    public final int idotartam;
    public final float tavolsag;
    public final float dij;
    public final float borravalo;
    public final String fizetesMod;
    
    public FuvarAdat(String line) {
        var split = line.split(";");
        
        azonosito = Integer.parseInt(split[0]);
        indulas = LocalDateTime.parse(split[1].replace(' ', 'T'));
        idotartam = Integer.parseInt(split[2]);
        tavolsag = Float.parseFloat(split[3].replace(',', '.'));
        dij = Float.parseFloat(split[4].replace(',', '.'));
        borravalo = Float.parseFloat(split[5].replace(',', '.'));
        fizetesMod = split[6];
    }
}