using System;
using System.Linq;
using System.IO;

var fuvarLista = File.ReadLines("tavok.txt")
                     .Select(k => new Fuvar(k))
                     .ToArray();

Console.WriteLine("2. Feladat\nA hét legelső útja km-ben: " + fuvarLista.First().tavolsag + " km");
Console.WriteLine("3. Feladat\nA hét utolsó útja km-ben: " + fuvarLista.Last().tavolsag + " km");

Enumerable.Range(1, 7).ToList().ForEach(day => {
    if(!fuvarLista.Select(k => k.nap).Distinct().Any(k => k == day)) {
        Console.WriteLine($"A {day}. nap szabadnap volt");
    }
});

Console.WriteLine("5. Feladat\nLegtöbb fuvarú nap: " + fuvarLista.Where(k => k.tavolsag == fuvarLista.Max(l => l.tavolsag)).First().nap);
Console.WriteLine("6. Feladat");
Enumerable.Range(1, 7).ToList()
          .ForEach(day => Console.WriteLine($"A {day}. nap távja: " + fuvarLista.Where(k => k.nap == day)
                                                                                .Select(k => k.tavolsag)
                                                                                .Sum()));
Console.WriteLine("7.Feladat\nÍrj be 1 távolságot!");

var readKm = int.Parse(Console.ReadLine());
Console.WriteLine($"{readKm} km esetén fizetendő: {CalcPrice(readKm)}");

File.WriteAllLines("dijazas.txt", fuvarLista.Select(k => $"{k.nap}. nap {k.sorszam}. fuvar: {CalcPrice(k.tavolsag)} FT"));
Console.WriteLine("9. Feladat\nAz egész heti fizetés: " + fuvarLista.Select(k => CalcPrice(k.tavolsag)).Sum());


int CalcPrice(int distance) => distance <= 2 ? 500 :
                               distance <= 5 ? 700 :
                               distance <= 10 ? 900 :
                               distance <= 20 ? 1400 : 2000;