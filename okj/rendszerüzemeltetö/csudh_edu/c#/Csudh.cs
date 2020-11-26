using System;
using System.Collections.Generic;
using System.IO;

public class Csudh {

    private static string domain(int szint, string domain) {
        var split = domain.Split('.');
        var utolsoIndex = split.Length - 1;
    
        return utolsoIndex < szint ? "nincs" : split[utolsoIndex - szint];
    }

    public static void Main(string[] args) {
        var lines = File.ReadAllLines("csudh.txt");
        var pairs = new List<Pair>();
    
        for(var i = 1; i < lines.Length; ++i) {
            pairs.Add(new Pair(lines[i]));
        }
    
        Console.WriteLine("3. Feladat: Párok száma: " + pairs.Count);
        Console.WriteLine("5. Feladat");
        var elsoDomain = pairs[0].domain;
    
        for(var i = 0; i < 5; ++i) {
            Console.WriteLine((i + 1) + ". szint: " + domain(i, elsoDomain));
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
    
        using(var table = new StreamWriter("table.html")){
            table.WriteLine(header);
        
            for(var sorszam = 0; sorszam < pairs.Count; ++sorszam) {
                var pair = pairs[sorszam];
            
                table.WriteLine("<tr>");
                table.WriteLine("<th style='text-align: left'>" + (sorszam + 1) + ".</th>");
                table.WriteLine("<td>" + pair.domain + "</td>");
                table.WriteLine("<td>" + pair.ip + "</td>");
            
                for(var k = 0; k < 5; ++k) {
                    table.WriteLine("<td>" + domain(k, pair.domain) + "</td>");
                }
            
                table.WriteLine("</tr>");
            }
        
            table.WriteLine("</table>");
        }
    }
}