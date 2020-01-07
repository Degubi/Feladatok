import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class Csudh_stream {
    public static void main(String[] args) throws IOException {
        var pairs = Files.lines(Path.of("csudh.txt"))
                         .skip(1)
                         .map(Pair::new)
                         .toArray(Pair[]::new);
        
        System.out.println("3. Feladat: Párok száma: " + pairs.length);
        System.out.println("5. Feladat");
        
        IntStream.range(0, 5).forEach(i -> System.out.println((i + 1) + ". szint: " + domain(i, pairs[0].domain)));
        
        var header = "<table>\n" +
                     "<tr>\n" +
                     "<th style='text-align: left'>Sorszam</th>\n" +
                     "<th style='text-align: left'>Host domain neve</th>\n" +
                     "<th style='text-align: left'>Host IP címe</th>\n" +
                     "<th style='text-align: left'>1. szint</th>\n" +
                     "<th style='text-align: left'>2. szint</th>\n" +
                     "<th style='text-align: left'>3. szint</th>\n" +
                     "<th style='text-align: left'>4. szint</th>\n" +
                     "<th style='text-align: left'>5. szint</th>\n" +
                     "</tr>";
        
        var formattedPairs = IntStream.range(0, pairs.length)
                                      .mapToObj(i -> formatPair(i, pairs))
                                      .collect(Collectors.joining("</tr>\n"));
        
        Files.writeString(Path.of("table.html"), header + formattedPairs + "</table>");
    }
    
    public static String formatPair(int index, Pair[] pairs) {
        var pair = pairs[index];
        
        return "<tr>\n" +
               "<th style='text-align: left'>" + (index + 1) + ".</th>\n" +
               "<td>" + pair.domain + "</td>\n" +
               "<td>" + pair.ip + "</td>\n" +
               IntStream.range(0, 5)
                           .mapToObj(i -> "<td>" + domain(i, pair.domain) + "</td>")
                           .collect(Collectors.joining("\n")) + "\n";
    }
    
    public static String domain(int szint, String domain) {
        var split = domain.split("\\.");
        var utolsoIndex = split.length - 1;
        
        return utolsoIndex < szint ? "nincs" : split[utolsoIndex - szint];
    }
    
    public static class Pair{
        public final String domain;
        public final String ip;
        
        public Pair(String line) {
            var split = line.split(";");
            
            domain = split[0];
            ip = split[1];
        }
    }
}