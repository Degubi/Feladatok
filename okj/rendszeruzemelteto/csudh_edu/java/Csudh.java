import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Csudh {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("csudh.txt"));
        var pairs = new ArrayList<Pair>();
        
        for(var i = 1; i < lines.size(); ++i) {
            pairs.add(new Pair(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Párok száma: " + pairs.size());
        System.out.println("5. Feladat");
        
        var elsoDomain = pairs.get(0).domain;
        for(var i = 0; i < 5; ++i) {
            System.out.println((i + 1) + ". szint: " + domain(i, elsoDomain));
        }
        
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
        
        try(var table = new PrintWriter("table.html")){
            table.println(header);
            
            for(var sorszam = 0; sorszam < pairs.size(); ++sorszam) {
                var pair = pairs.get(sorszam);
                
                table.println("<tr>");
                table.println("<th style='text-align: left'>" + (sorszam + 1) + ".</th>");
                table.println("<td>" + pair.domain + "</td>");
                table.println("<td>" + pair.ip + "</td>");
                
                for(var k = 0; k < 5; ++k) {
                    table.println("<td>" + domain(k, pair.domain) + "</td>");
                }
                
                table.println("</tr>");
            }
            
            table.println("</table>");
        }
    }
    
    public static String domain(int szint, String domain) {
        var split = domain.split("\\.");
        var utolsoIndex = split.length - 1;
        
        return utolsoIndex < szint ? "nincs" : split[utolsoIndex - szint];
    }
}