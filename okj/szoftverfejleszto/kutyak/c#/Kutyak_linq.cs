using System;
using System.IO;
using System.Linq;

// A KutyaFajtak.csv 388. sorában hibás adat van, ám az eredeti név nem kell sehol a feladatban, ezért azt el se tároljuk
var nevLookup = File.ReadLines("KutyaNevek.csv")
                    .Skip(1)
                    .Select(k => k.Split(';'))
                    .ToDictionary(k => int.Parse(k[0]), k => k[1]);

var fajtaLookup = File.ReadLines("KutyaFajtak.csv")
                      .Skip(1)
                      .Select(k => k.Split(';'))
                      .ToDictionary(k => int.Parse(k[0]), k => k[1]);

var kutyik = File.ReadLines("Kutyak.csv")
                 .Skip(1)
                 .Select(k => new Kutya(k.Split(';')))
                 .ToArray();

Console.WriteLine("3. Feladat: Kutyanevek száma: " + nevLookup.Count);
Console.WriteLine($"6. Feladat: Átlag életkor: {kutyik.Average(k => k.eletkor).ToString("0.00")} év");

var legidosebbKutyi = kutyik.OrderByDescending(k => k.eletkor).First();

Console.WriteLine($"7. Feladat: Legidősebb kutya neve + fajtája: {nevLookup[legidosebbKutyi.nevId]}: {fajtaLookup[legidosebbKutyi.fajtaId]}");
Console.WriteLine("8. Feladat: 2018 január 10-én vizsgált kutyik:");

kutyik.Where(k => k.ellenorzes.Year == 2018 && k.ellenorzes.Month == 1 && k.ellenorzes.Day == 10)
      .GroupBy(k => fajtaLookup[k.fajtaId])
      .ToList()
      .ForEach(k => Console.WriteLine($"{k.Key}: {k.Count()} kutya"));

var (legjobbanTerheltNap, legjobbanTerheltNapDb) = kutyik.GroupBy(k => k.ellenorzes)
                                                         .Select(k => (k.Key, k.Count()))
                                                         .OrderByDescending(k => k.Item2)
                                                         .First();

Console.WriteLine($"9. Feladat: Legjobban leterhelt nap: {legjobbanTerheltNap}: {legjobbanTerheltNapDb} db kutya");

var fileba = kutyik.GroupBy(k => nevLookup[k.nevId])
                   .Select(k => (k.Key, k.Count()))
                   .OrderByDescending(k => k.Item2)
                   .Select(k => $"{k.Key};{k.Item2}");

File.WriteAllLines("nevstatisztika.txt", fileba);