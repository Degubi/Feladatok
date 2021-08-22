using System;
using System.Linq;
using System.IO;

var csatlakozasok = File.ReadLines("EUcsatlakozas.txt", System.Text.Encoding.GetEncoding("ISO-8859-1"))
                        .Select(k => new Csatlakozas(k))
                        .ToArray();

Console.WriteLine($"3. Feladat: 2018-ig csatlakozott országok száma: {csatlakozasok.Length}");

var tagallamok2007ben = csatlakozasok.Count(k => k.datum.Year == 2007);
Console.WriteLine($"4. Feladat: 2007-ben csatlakozott országok száma: {tagallamok2007ben}");

var magyarorszagCsat = csatlakozasok.First(k => k.orszag == "Magyarország");
Console.WriteLine($"5. Feladat: Magyarország csatlakozása: {magyarorszagCsat.datum}");

var voltEMajusban = csatlakozasok.Any(k => k.datum.Month == 5);
Console.WriteLine("6. Feladat: " + (voltEMajusban ? "Volt" : "Nem volt") + " májusban csatlakozás");

var utoljaraCsatlakozo = csatlakozasok.OrderByDescending(k => k.datum).First();
Console.WriteLine($"7. Feladat: Utoljára csatlakozott: {utoljaraCsatlakozo.orszag}");
Console.WriteLine("8. Feladat:");

csatlakozasok.GroupBy(k => k.datum.Year)
             .ToList()
             .ForEach(k => Console.WriteLine($"{k.Key} - {k.Count()} db ország"));