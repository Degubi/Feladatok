using System;
using System.IO;
using System.Linq;

var kolcsonzesek = File.ReadLines("kolcsonzesek.txt")
                       .Skip(1)
                       .Select(k => new Kolcsonzes(k))
                       .ToArray();

Console.WriteLine($"5. Feladat: Kölcsönzések száma: {kolcsonzesek.Length}");
Console.WriteLine("Írj be 1 nevet!");

var bekertNev = Console.ReadLine();
var bekertNevhezTartozoKolcsonzesek = kolcsonzesek.Where(k => k.nev == bekertNev)
                                                  .ToArray();

var bekerthezKiirando = bekertNevhezTartozoKolcsonzesek.Length == 0
                        ? "Nem volt ilyen nevű kölcsönző"
                        : String.Join('\n', bekertNevhezTartozoKolcsonzesek.Select(k => $"{k.elvitelIdopont}-{k.visszahozatalIdopont}"));

Console.WriteLine(bekerthezKiirando);
Console.WriteLine("Adj meg 1 időpontot! (óra:perc)");

var bekertIdopontParts = Console.ReadLine().Split(':');
var bekertIdopont = new TimeSpan(int.Parse(bekertIdopontParts[0]), int.Parse(bekertIdopontParts[1]), 0);

Console.WriteLine("7. Feladat:");

kolcsonzesek.Where(k => bekertIdopont > k.elvitelIdopont && bekertIdopont < k.visszahozatalIdopont)
            .ToList()
            .ForEach(k => Console.WriteLine($"    {k.elvitelIdopont}-{k.visszahozatalIdopont}: {k.nev}"));

var totalStartedHours = kolcsonzesek.Select(k => (k.visszahozatalIdopont - k.elvitelIdopont).Minutes)
                                    .Select(k => (int) Math.Ceiling(k / 30.0))
                                    .Sum();

Console.WriteLine($"8. Feladat: A bevétel {totalStartedHours * 2400}Ft");

var fileba = kolcsonzesek.Where(k => k.jarmuAzonosito == "F")
                         .Select(k => $"{k.elvitelIdopont}-{k.visszahozatalIdopont}: {k.nev}");

File.WriteAllLines("F.txt", fileba);
Console.WriteLine("10. Feladat:");

kolcsonzesek.GroupBy(k => k.jarmuAzonosito)
            .Select(k => new { azonosito = k.Key, dbSzam = k.Count() })
            .OrderBy(k => k.azonosito)
            .ToList()
            .ForEach(k => Console.WriteLine($"    {k.azonosito}-{k.dbSzam}"));