using System;
using System.IO;
using System.Linq;

var autok = File.ReadLines("autok.csv", System.Text.Encoding.Latin1)
                .Skip(1)
                .Select(k => new Auto(k))
                .ToArray();

Console.WriteLine($"2. Feladat: Hirdetesek szama: {autok.Length}");

var bpToMiskolc = autok.Where(k => k.indulas == "Budapest" && k.cel == "Miskolc")
                       .Sum(k => k.ferohely);

Console.WriteLine($"3. Feladat: BP to Miskolc hely: {bpToMiskolc}");

var spotokToFerohelyek = autok.GroupBy(k => $"{k.indulas}-{k.cel}")
                              .ToDictionary(k => k.Key, k => k.Sum(n => n.ferohely));

var maxFerohelyek = spotokToFerohelyek.Values.Max();
var maxSpot = spotokToFerohelyek.First(k => k.Value == maxFerohelyek).Key;

Console.WriteLine($"4. Feladat: {maxSpot}: {maxFerohelyek} hely");
Console.WriteLine("5. Feladat");

var igenyek = File.ReadLines("igenyek.csv", System.Text.Encoding.Latin1)
                  .Skip(1)
                  .Select(k => new Igeny(k))
                  .ToArray();

var igenyekToAuto = igenyek.Select(igeny => new { igeny = igeny, auto = autok.FirstOrDefault(k => k.indulas == igeny.indulas && k.cel == igeny.cel && k.ferohely >= igeny.szemelyek) })
                           .ToArray();

igenyekToAuto.Where(k => k.auto != null)
             .ToList()
             .ForEach(k => Console.WriteLine($"{k.igeny.azonosito} -> {k.auto.rendszam}"));

var fileba = igenyekToAuto.Select(k => k.auto == null ? $"{k.igeny.azonosito}: Sajnos nem sikerült autót találni"
                                                      : $"{k.igeny.azonosito}: Rendszam: {k.auto.rendszam}, Telefonszam: {k.auto.telefonszam}");
File.WriteAllLines("utazasuzenetek.txt", fileba);