using System;
using System.Linq;
using System.IO;

var jeloltek = File.ReadLines("szavazatok.txt")
                   .Select(k => new Szavazat(k))
                   .ToArray();

Console.WriteLine($"2. Feladat\nA választáson indult jelöltek száma: {jeloltek.Length}");
Console.WriteLine("3.Feladat\nÍrd be 1 jelölt nevét!");

var bekertNev = Console.ReadLine();
var miEmberunk = jeloltek.Where(k => k.nev == bekertNev).FirstOrDefault();

Console.WriteLine(miEmberunk == null ? "Nem indult ilyen jelölt" : $"Szavazatok száma: {miEmberunk.szavazatok}");

var osszSzavazat = jeloltek.Select(k => k.szavazatok).Sum();
var arany = (float) osszSzavazat / 12345 * 100;

Console.WriteLine("4. Feladat");
Console.WriteLine("A választáson " + osszSzavazat + " polgár, a jogosultak {0:F2}%-a vett részt", arany);
Console.WriteLine("5. Feladat");

jeloltek.Select(k => k.part)
        .Distinct()
        .GroupBy(k => jeloltek.Where(l => l.part == k)
                                .Select(l => l.szavazatok)
                                .Sum() / (float) osszSzavazat * 100)
        .ToList()
        .ForEach(k => Console.WriteLine(k.Key.ToString("#.##") + "% " + k.Aggregate((m, a) => m + " " + a)));

var legtobbSzavazat = jeloltek.Max(k => k.szavazatok);
var legtobb = jeloltek.Where(k => k.szavazatok == legtobbSzavazat)
                      .First();

Console.WriteLine($"6. Feladat\nLegtobb szavazatot kapta: {legtobb.nev}, partja: {legtobb.part}");
Console.WriteLine("7. Feladat");

var fileba = jeloltek.GroupBy(k => k.part)
                     .Select(k => new { Part = k.Key, Szavazat = k.First(l => l.szavazatok == k.Max(m => m.szavazatok)) })
                     .OrderBy(k => k.Szavazat.kerSzam)
                     .Select(k => k.Szavazat.kerSzam + " " + k.Szavazat.nev + " " + k.Part);

File.WriteAllLines("kepviselok.txt", fileba);