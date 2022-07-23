using System;
using System.IO;
using System.Linq;

var lines = File.ReadAllLines("eladott.txt");
var firstLineSplit = lines[0].Split(' ');

var vonalHossz = int.Parse(firstLineSplit[1]);
var arPer10km = int.Parse(firstLineSplit[2]);
var utasok = Enumerable.Range(1, lines.Length - 1)
                       .Select(i => new Utas(lines[i], i))
                       .ToArray();

var utolso = utasok[utasok.Length - 1];

Console.WriteLine($"2.Feladat: Utolsó utas ülése: {utolso.ules} utazott távolság: {utolso.leszallasKm - utolso.felszallasKm}");
Console.WriteLine("3.Feladat:");

utasok.Where(k => (k.leszallasKm - k.felszallasKm) == vonalHossz)
      .ToList()
      .ForEach(k => Console.Write(k.sorszam + " "));

Console.WriteLine($"\n4.Feladat: Összes bevétel: {utasok.Sum(k => Utas.getAr(k, arPer10km))}");

var utolsoElottiMegalloKm = utasok.Select(k => k.leszallasKm)
                                  .Where(k => k != vonalHossz)
                                  .Max();

var utoljaraFelszallok = utasok.Count(k => k.felszallasKm == utolsoElottiMegalloKm);
var utoljaraLeszallok  = utasok.Count(k => k.leszallasKm == utolsoElottiMegalloKm);

Console.WriteLine($"5.Feladat: Utolsó megállónál felszállók: {utoljaraFelszallok}, leszállók: {utoljaraLeszallok}");

var megallokSzama = utasok.Select(k => k.leszallasKm)
                          .Concat(utasok.Select(k => k.felszallasKm))
                          .Distinct()
                          .Count() - 2;

Console.WriteLine($"6.Feladat: Megállók száma: {megallokSzama}");
Console.WriteLine("Írj be 1 km számot!");

var bekertKm = int.Parse(Console.ReadLine());
var fileba = Enumerable.Range(1, 48)
                       .Select(i => getUlesStatus(i, bekertKm, utasok));

File.WriteAllLines("kihol.txt", fileba);


string getUlesStatus(int ules, int bekertKm, Utas[] utasok) {
    var utasUlesen = utasok.FirstOrDefault(k => k.ules == ules && (k.felszallasKm == bekertKm || k.leszallasKm == bekertKm));

    return ules + ". ülés: " + (utasUlesen == null ? "üres" : (utasUlesen.sorszam + ". utas"));
}