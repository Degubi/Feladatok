import java.util.*;

public class JelszoGeneralo {
    private final Random rnd;

    public JelszoGeneralo(Random r) {
        rnd = r;
    }

    public String jelszo(int jelszoHossz) {
        var jlsz = new StringBuilder();
        
        while (jlsz.length() < jelszoHossz) {
            var c = (char) (rnd.nextInt(75) + 48);
            
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')) {
                jlsz.append(c);
            }
        }
        return jlsz.toString();
    }
}