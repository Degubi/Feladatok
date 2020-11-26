using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

public class Tanciskola_linq {

    public static void Main(string[] args){
        var lines = File.ReadAllLines("tancrend.txt");
        var tancok = Enumerable.Range(0, lines.Length)
                                .Where(k => k % 3 == 0)
                                .Select(k => new Tanc(lines[k], lines[k + 1], lines[k + 2]))
                                .ToArray();

        Console.WriteLine("Első tánc neve: " + tancok.First().category + ", az utolsóé: " + tancok.Last().category);
        Console.WriteLine("Összesen ennyien táncoltak szambát: " + tancok.Where(k => k.category.Equals("samba")).Count());

        Console.WriteLine("Vilma által táncolt táncok: " + tancok
                .Where(k => k.woman.Equals("Vilma"))
                .Select(k => k.category)
                .Aggregate((k, l) => k + ", " + l));

        Console.WriteLine("Írj be 1 kategóriát!");
        var tancNev = Console.ReadLine();

        Console.WriteLine("Vilma a " + tancNev + " táncot " + tancok
                .Where(k => k.woman.Equals("Vilma"))
                .Where(k => k.category.Equals(tancNev))
                .Select(k => k.man)
                .DefaultIfEmpty("senkivel sem")
                .FirstOrDefault() + "-vel táncolta");

        var lanyok = tancok.Select(k => k.woman)
                            .Distinct()
                            .Select(k => new KeyValuePair<string, int>(k, tancok.Where(a => a.woman.Equals(k)).Count()))
                            .OrderByDescending(k => k.Value)
                            .ToArray();

        var fiuk = tancok.Select(k => k.man)
                            .Distinct()
                            .Select(k => new KeyValuePair<string, int>(k, tancok.Where(a => a.man.Equals(k)).Count()))
                            .OrderByDescending(k => k.Value)
                            .ToArray();

        using(var output = new StreamWriter("szereplok.txt")){
            output.Write("Lányok: ");
            output.Write(lanyok.Select(k => k.Key).Aggregate((k, l) => k + ", " + l));
            output.Write("\nFiúk: ");
            output.Write(fiuk.Select(k => k.Key).Aggregate((k, l) => k + ", " + l));
        }

        Console.WriteLine("A legtöbbször táncolt lányok: " + lanyok.Where(k => k.Value == lanyok.First().Value)
                                                                    .Select(k => k.Key)
                                                                    .Aggregate((k, l) => k + ", " + l));

        Console.WriteLine("A legtöbbször táncolt fiúk: " + fiuk.Where(k => k.Value == fiuk.First().Value)
                                                                .Select(k => k.Key)
                                                                .Aggregate((k, l) => k + ", " + l));
    }
}