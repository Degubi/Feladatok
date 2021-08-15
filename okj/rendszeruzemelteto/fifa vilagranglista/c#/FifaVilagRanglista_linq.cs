using System;
using System.IO;
using System.Linq;

var eredmenyek = File.ReadLines("fifa.txt")
                     .Skip(1)
                     .Select(k => new Eredmeny(k))
                     .ToArray();

Console.WriteLine($"3. Feladat: Csapatok száma: {eredmenyek.Length}");

var atlagPontszam = eredmenyek.Select(k => k.pontszam)
                              .Average();

Console.WriteLine("4. Feladat: Átlagpontszám: " + atlagPontszam.ToString("#.##"));

var legnagyobbValtozas = eredmenyek.Max(k => k.valtozas);
var legtobbetJavito = eredmenyek.First(k => k.valtozas == legnagyobbValtozas);

Console.WriteLine($"5. Feladat: Legtöbbet javító csapat: {legtobbetJavito.csapat}" +
                  $", helyezés: {legtobbetJavito.helyezes}, pontszam: {legtobbetJavito.pontszam}");

var voltEMo = eredmenyek.Any(k => k.csapat == "Magyarország");

Console.WriteLine(voltEMo ? "6. Feladat: Csapatok között van Magyarország" : "6. Feladat: Csapatok között nincs Magyarország");
Console.WriteLine("7. Feladat:");

eredmenyek.Select(k => k.valtozas)
          .GroupBy(k => k)
          .Where(k => k.Count() > 1)
          .ToList()
          .ForEach(k => Console.WriteLine($"    {k.Key} helyet változott: {k.Count()} csapat"));