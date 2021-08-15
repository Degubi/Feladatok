using System;
using System.IO;
using System.Linq;
using System.Globalization;

var napok = new []{ "v", "h", "k", "sz", "cs", "p", "szo" };
var honapok = new []{ 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };

var lines = File.ReadAllLines("lista.txt");
var sorozatok = Enumerable.Range(0, lines.Length)
                          .Where(k => k % 5 == 0)
                          .Select(i => new Sorozat(lines, i))
                          .ToArray();

var ismertDatumuakSzama = sorozatok.Where(k => k.adasbaKerulesiDatum != DateTime.MinValue)
                                   .Count();

Console.WriteLine($"2. Feladat: {ismertDatumuakSzama} db ismert dátumú epizód van");

var keszitoAltalLatottakSzama = sorozatok.Where(k => k.lattaEMarAKeszito)
                                         .Count();

var osszesElpazaroltPerc = sorozatok.Where(k => k.lattaEMarAKeszito)
                                    .Select(k => k.epizodonkentiHossz)
                                    .Sum();

var latottakSzazalek = ((float) keszitoAltalLatottakSzama / sorozatok.Length * 100);
var elpazaroltIdoStat = TimeSpan.FromMinutes(osszesElpazaroltPerc);

Console.WriteLine($"3. Feladat: Látottak százaléka: {latottakSzazalek.ToString("#.##")}%\n");
Console.WriteLine($"4. Feladat: Eltöltött idő: {elpazaroltIdoStat.Days} nap, {elpazaroltIdoStat.Hours} óra és {elpazaroltIdoStat.Minutes} perc");
Console.WriteLine("5. Feladat: Írj be 1 dátumot! (éééé.hh.nn)");

var bekertDatumStr = Console.ReadLine();
var bekertDatum = DateTime.ParseExact(bekertDatumStr, "yyyy.MM.dd", CultureInfo.InvariantCulture);

sorozatok.Where(k => k.adasbaKerulesiDatum != DateTime.MinValue && !k.lattaEMarAKeszito)
         .Where(k => k.adasbaKerulesiDatum < bekertDatum || k.adasbaKerulesiDatum == bekertDatum)
         .ToList()
         .ForEach(k => Console.WriteLine($"{k.evadokSzama}x{k.epizodokSzama}\t{k.cim}"));

Console.WriteLine("7. Feladat: Add meg 1 hét napját! (h, k, sze, cs, p, szo, v)");

var bekertNap = Console.ReadLine();
var bekertNapraEsok = sorozatok.Where(k => k.adasbaKerulesiDatum != DateTime.MinValue)
                               .Where(k => bekertNap == Hetnapja(k.adasbaKerulesiDatum.Year, k.adasbaKerulesiDatum.Month, k.adasbaKerulesiDatum.Day))
                               .Select(k => k.cim)
                               .Distinct()
                               .ToArray();

Console.WriteLine(bekertNapraEsok.Length == 0 ? "Az adott napon nem kerül adásba sorozat." : string.Join("\n", bekertNapraEsok));

var fileba = sorozatok.GroupBy(k => k.cim)
                      .Select(k => k.Key + " " + k.Select(l => l.epizodonkentiHossz).Sum() + " " + k.Count())
                      .ToArray();

File.WriteAllLines("summa.txt", fileba);

string Hetnapja(int ev, int ho, int nap) {
    ev = ho < 3 ? ev - 1 : ev;

    return napok[(ev + ev / 4 - ev / 100 + ev / 400 + honapok[ho - 1] + nap) % 7];
}