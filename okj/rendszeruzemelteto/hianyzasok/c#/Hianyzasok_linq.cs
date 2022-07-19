using System;
using System.IO;
using System.Linq;

var hianyzasok = File.ReadLines("szeptember.csv")
                     .Skip(1)
                     .Select(k => new Hianyzas(k))
                     .ToArray();

var totalHianyzottOrak = hianyzasok.Sum(k => k.mulasztottOrak);

Console.WriteLine($"2. Feladat: Hianyzott orak: {totalHianyzottOrak}");
Console.WriteLine("3. Feladat: Írj be egy napot(1-30) és egy nevet!");

var bekertNap = int.Parse(Console.ReadLine());
var bekertNev = Console.ReadLine();
var bekertHianyzottE = hianyzasok.Any(k => k.nev == bekertNev);

Console.WriteLine($"4. Feladat: {bekertNev} {(bekertHianyzottE ? " hiányzott" : " nem hiányzott")}");
Console.WriteLine("5. Feladat");

var azonANaponHianyoztak = hianyzasok.Where(k => bekertNap >= k.elsoNap && bekertNap <= k.utolsoNap)
                                     .ToList();

if(azonANaponHianyoztak.Count == 0) {
    Console.WriteLine("Nem volt hiányzó");
}else{
    azonANaponHianyoztak.ForEach(k => Console.WriteLine($"{k.nev} {k.osztaly}"));
}

var hianyzasokStat = hianyzasok.GroupBy(k => k.osztaly)
                               .Select(k => $"{k.Key};{k.Sum(n => n.mulasztottOrak)}");

File.WriteAllLines("osszesitett.csv", hianyzasokStat);