import java.util.*;
import java.util.stream.*;

public class Karakter {

    public final String karakter;
    public final List<String> matrixLines;

    public Karakter(String karakter, List<String> matrixLines) {
        this.karakter = karakter;
        this.matrixLines = matrixLines;
    }

    public String formatMatrix() {
        return matrixLines.stream()
                          .map(k -> {
                              var chars = k.chars()
                                           .map(n -> n == '0' ? ' ' : 'X')
                                           .toArray();

                              return new String(chars, 0, chars.length);
                          })
                          .collect(Collectors.joining("\n"));
    }

    public boolean felismer(Karakter other) {
        return matrixLines.equals(other.matrixLines);
    }
}