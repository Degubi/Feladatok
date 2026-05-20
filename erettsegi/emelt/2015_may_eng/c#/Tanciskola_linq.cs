using System;
using System.IO;
using System.Linq;

var lines = File.ReadAllLines("tancrend.txt");
var tancok = Enumerable.Range(0, lines.Length)
                       .Where(i => i % 3 == 0)
                       .Select(i => new Tanc(lines, i))
                       .ToArray();

Console.WriteLine($"Első tánc neve: {tancok.First().category}, az utolsóé: {tancok.Last().category}");

var szambazokSzama = tancok.Where(k => k.category == "samba")
                           .Count();

Console.WriteLine($"Összesen ennyien táncoltak szambát: {szambazokSzama}");

var vilmaKategoriai = tancok.Where(k => k.woman == "Vilma")
                            .Select(k => k.category)
                            .Distinct()
                            .ToArray();

Console.WriteLine("Vilma által táncolt táncok: " + string.Join(", ", vilmaKategoriai));
Console.WriteLine("Írj be 1 kategóriát!");

var tancNev = Console.ReadLine();
var kivelTancoltaVilma = tancok.Where(k => k.woman == "Vilma" && k.category == tancNev)
                               .Select(k => k.man)
                               .DefaultIfEmpty("senkivel sem")
                               .FirstOrDefault();

Console.WriteLine($"Vilma a {tancNev} táncot {kivelTancoltaVilma}-vel táncolta");

var lanyokToTancalkalmak = tancok.GroupBy(k => k.woman)
                                 .ToDictionary(k => k.Key, k => k.Count());

var fiukToTancalkalmak = tancok.GroupBy(k => k.man)
                               .ToDictionary(k => k.Key, k => k.Count());

File.WriteAllText("szereplok.txt", "Lányok: " + string.Join(", ", lanyokToTancalkalmak.Keys) +
                                   "\nFiúk: " + string.Join(", ", fiukToTancalkalmak.Keys));

var grillMax = lanyokToTancalkalmak.Values.Max();
var boiMax = fiukToTancalkalmak.Values.Max();

var popularGrills = lanyokToTancalkalmak.Where(k => k.Value == grillMax)
                                        .Select(k => k.Key)
                                        .ToArray();

var popularBois = fiukToTancalkalmak.Where(k => k.Value == boiMax)
                                    .Select(k => k.Key)
                                    .ToArray();

Console.WriteLine("A legtöbbet táncolt lányok: " + string.Join(", ", popularGrills));
Console.WriteLine("A legtöbbet táncolt fiúk: " + string.Join(", ", popularBois));