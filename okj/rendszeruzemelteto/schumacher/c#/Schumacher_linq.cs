using System;
using System.IO;
using System.Linq;

var eredmenyek = File.ReadLines("schumacher.csv")
                     .Skip(1)
                     .Select(k => new Eredmeny(k))
                     .ToArray();

Console.WriteLine($"3. Feladat: Adatsorok száma: {eredmenyek.Length}");
Console.WriteLine("4. Feladat:");

eredmenyek.Where(k => k.dijNev == "Hungarian Grand Prix")
          .Where(k => k.helyezes != 0)
          .ToList()
          .ForEach(k => Console.WriteLine($"    {k.datum.ToShortDateString()}: {k.helyezes}. hely"));

Console.WriteLine("5. Feladat:");

eredmenyek.Where(k => k.helyezes == 0)
          .GroupBy(k => k.vegeredmenyStatusz)
          .ToDictionary(k => k.Key, k => k.Count())
          .Where(k => k.Value > 2)
          .ToList()
          .ForEach(k => Console.WriteLine($"    {k.Key}: {k.Value}"));