using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

var lines = File.ReadAllLines("utca.txt");
var firstLineSplit = lines[0].Split(' ').Select(int.Parse).ToArray();
var fizetendoAdokSavonkent = new Dictionary<string, int>{{ "A", firstLineSplit[0] }, { "B", firstLineSplit[1] }, { "C", firstLineSplit[2] }};
var telkek = lines.Skip(1)
                  .Select(k => new Telek(k.Split(' ')))
                  .ToArray();

Console.WriteLine($"2. Feladat: Telkek száma: {telkek.Length}");
Console.WriteLine("3. Feladat: Írj be 1 adószámot!");

var bekertAdoszam = int.Parse(Console.ReadLine());
var bekertTelkei = telkek.Where(k => k.adoszam == bekertAdoszam)
                         .ToArray();

var kiirando = bekertTelkei.Length == 0 ? "Nem szerepel az adatállományban"
                                        : String.Join('\n', bekertTelkei.Select(k => k.utcaNev + " " + k.hazSzam));
Console.WriteLine(kiirando);
Console.WriteLine("5. Feladat:");

telkek.GroupBy(k => k.adosav)
      .Select(k => new { sav = k.Key, telkekSzama = k.Count(), teljesAdo = k.Sum(n => ado(n, fizetendoAdokSavonkent)) })
      .ToList()
      .ForEach(k => Console.WriteLine($"{k.sav} sáv: {k.telkekSzama} telek, adó: {k.teljesAdo}"));

Console.WriteLine("6. Feladat: Több sávba sorolt utcák:");

telkek.GroupBy(k => k.utcaNev)
      .Where(k => k.Select(m => m.adosav).Distinct().Count() > 1)
      .ToList()
      .ForEach(k => Console.WriteLine(k.Key));

var fileba = telkek.GroupBy(k => k.adoszam)
                   .Select(k => $"{k.Key} {k.Sum(m => ado(m, fizetendoAdokSavonkent))}");

File.WriteAllLines("fizetendo.txt", fileba);


static int ado(Telek telek, Dictionary<string, int> fizetendoAdokSavonkent) {
    var mennyiseg = fizetendoAdokSavonkent[telek.adosav] * telek.terulet;

    return mennyiseg < 10000 ? 0 : mennyiseg;
}