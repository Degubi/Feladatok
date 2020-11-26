import java.time.*;
import java.time.format.*;

public class IdozitettFelirat {
    public final String idozites;
    public final String felirat;

    public IdozitettFelirat(String idoz, String felir){
        idozites = idoz;
        felirat = felir;
    }

    public int szavakSzama(){
        return felirat.chars().filter(k -> k == ' ').map(k -> 1).sum() + 1;
    }

    private static String str_idoforma(String ido){
        return LocalTime.parse(ido, DateTimeFormatter.ISO_LOCAL_TIME.withResolverStyle(ResolverStyle.LENIENT))
                        .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String strIdozites(){
        var split = idozites.split(" - ");
        return str_idoforma("00:" + split[0]) + "--> " + str_idoforma("00:" + split[1]);
    }
}