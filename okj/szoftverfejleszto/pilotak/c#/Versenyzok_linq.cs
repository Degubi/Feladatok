using System;
using System.IO;
using System.Linq;

var pilotak = File.ReadLines("pilotak.csv")
                  .Skip(1)
                  .Select(k => new Pilota(k))
                  .ToArray();

Console.WriteLine($"3. Feladat: Adatsorok száma: {pilotak.Length}");
Console.WriteLine($"4. Feladat: Utolsó pilóta neve: {pilotak[pilotak.Length - 1].nev}");
Console.WriteLine("5. Feladat:");

pilotak.Where(k => k.szuletesiDatum.Year >= 1800 && k.szuletesiDatum.Year <= 1900)
       .ToList()
       .ForEach(k => Console.WriteLine("    " + k.nev + " (" + k.szuletesiDatum + ")"));

var legkisebbRajtszam = pilotak.Where(k => k.rajtszam != Pilota.URES_RAJTSZAM)
                               .Min(k => k.rajtszam);

var legkisebbRajtszamuPilota = pilotak.Where(k => k.rajtszam == legkisebbRajtszam)
                                      .First();

Console.WriteLine("6. Feladat: " + legkisebbRajtszamuPilota.nemzetiseg);
Console.Write("7. Feladat: ");

pilotak.Where(k => k.rajtszam != Pilota.URES_RAJTSZAM)
       .GroupBy(k => k.rajtszam)
       .Select(k => new {
           Rajtszam = k.Key,
           DbSzam = k.Count()
       })
       .Where(k => k.DbSzam > 1)
       .ToList()
       .ForEach(k => Console.Write(k.Rajtszam + " "));