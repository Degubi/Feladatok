using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

var lines = File.ReadAllLines("penztar.txt");
var vasarlasKezdoZaroIndexek = Enumerable.Range(-1, lines.Length)
                                         .Where(i => i == -1 || lines[i] == "F")
                                         .ToArray();

var vasarlasok = Enumerable.Range(0, vasarlasKezdoZaroIndexek.Length - 1)
                           .Select(i => lines[vasarlasKezdoZaroIndexek[i] + 1 .. vasarlasKezdoZaroIndexek[i + 1]])
                           .Select(k => k.GroupBy(n => n).ToDictionary(n => n.Key, n => n.Count()))
                           .ToArray();

Console.WriteLine($"2. Feladat: Vásárlások száma: {vasarlasok.Length}");
Console.WriteLine($"3. Feladat: Elsö vásárlásnál vett dolgok száma: {vasarlasok[0].Count}");
Console.WriteLine("4. Feladat:");
Console.WriteLine("Írj be 1 sorszámot");
var bekertSorszam = int.Parse(Console.ReadLine());

Console.WriteLine("Írj be 1 árut");
var bekertAru = Console.ReadLine();

Console.WriteLine("Írj be 1 mennyiséget");
var bekertDBszam = int.Parse(Console.ReadLine());

var bekertAruElsoVasarlasIndex = Enumerable.Range(0, vasarlasok.Length)
                                           .Where(i => vasarlasok[i].ContainsKey(bekertAru))
                                           .First();

var bekertAruUtolsoVasarlasIndex = Enumerable.Range(0, vasarlasok.Length)
                                             .Reverse()
                                             .Where(i => vasarlasok[i].ContainsKey(bekertAru))
                                             .First();

var bekertAruOsszDbSzam = vasarlasok.Sum(k => k.GetValueOrDefault(bekertAru, 0));

Console.WriteLine("5. Feladat:");
Console.WriteLine($"Először a {bekertAruElsoVasarlasIndex + 1}. vásárlásnál vettek {bekertAru}-t");
Console.WriteLine($"Utoljára a {bekertAruUtolsoVasarlasIndex + 1}. vásárlásnál vettek {bekertAru}-t");
Console.WriteLine($"Összesen {bekertAruOsszDbSzam}-szor vettek {bekertAru}-t");
Console.WriteLine($"6. Feladat: {bekertDbszam} db esetén a fizetendő: {ertek(bekertDbszam)}");
Console.WriteLine($"7. Feladat: A {bekertSorszam}. vásárláskor vásárolt dolgok:");

vasarlasok[bekertSorszam - 1].ToList().ForEach(k => Console.WriteLine($"{k.Key}-ből: {k.Value} db"));

var fileba = Enumerable.Range(0, vasarlasok.Length)
                       .Select(i => (i + 1) + ": " + calculateVasarlasOsszertek(vasarlasok[i]));

File.WriteAllLines("osszeg.txt", fileba);


static int calculateVasarlasOsszertek(Dictionary<string, int> vasarlasItemek) => vasarlasItemek.Values.Sum(k => ertek(k));
static int ertek(int dbSzam) => dbSzam == 1 ? 500 : dbSzam == 2 ? 950 : 1350 + (400 * (dbSzam - 3));