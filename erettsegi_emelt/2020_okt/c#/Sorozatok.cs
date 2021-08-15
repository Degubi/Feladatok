using System;
using System.Collections.Generic;
using System.IO;
using System.Globalization;

var napok = new []{ "v", "h", "k", "sz", "cs", "p", "szo" };
var honapok = new []{ 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };

var lines = File.ReadAllLines("lista.txt");
var sorozatok = new List<Sorozat>();

for(var i = 0; i < lines.Length; i += 5) {
    sorozatok.Add(new Sorozat(lines, i));
}

var ismertDatumuakSzama = 0;
var keszitoAltalLatottakSzama = 0;
var osszesElpazaroltPerc = 0;

foreach(var sorozat in sorozatok) {
    if(sorozat.adasbaKerulesiDatum != DateTime.MinValue) {
        ++ismertDatumuakSzama;
    }

    if(sorozat.lattaEMarAKeszito) {
        ++keszitoAltalLatottakSzama;
        osszesElpazaroltPerc += sorozat.epizodonkentiHossz;
    }
}

var latottakSzazalek = ((float) keszitoAltalLatottakSzama / sorozatok.Count * 100);
var elpazaroltIdoStat = TimeSpan.FromMinutes(osszesElpazaroltPerc);

Console.WriteLine($"2. Feladat: {ismertDatumuakSzama} db ismert dátumú epizód van");
Console.WriteLine($"3. Feladat: Látottak százaléka: {latottakSzazalek.ToString("#.##")}%\n");
Console.WriteLine($"4. Feladat: Eltöltött idő: {elpazaroltIdoStat.Days} nap, {elpazaroltIdoStat.Hours} óra és {elpazaroltIdoStat.Minutes} perc");
Console.WriteLine("5. Feladat: Írj be 1 dátumot! (éééé.hh.nn)");

var bekertDatumStr = Console.ReadLine();
var bekertDatum = DateTime.ParseExact(bekertDatumStr, "yyyy.MM.dd", CultureInfo.InvariantCulture);

foreach(var sorozat in sorozatok) {
    if(sorozat.adasbaKerulesiDatum != DateTime.MinValue && !sorozat.lattaEMarAKeszito) {
        if(sorozat.adasbaKerulesiDatum < bekertDatum || sorozat.adasbaKerulesiDatum == bekertDatum) {
            Console.WriteLine($"{sorozat.evadokSzama}x{sorozat.epizodokSzama}\t{sorozat.cim}");
        }
    }
}

Console.WriteLine("7. Feladat: Add meg 1 hét napját! (h, k, sze, cs, p, szo, v)");

var bekertNap = Console.ReadLine();
var bekertNapraEsok = new List<string>();

foreach(var sorozat in sorozatok) {
    if(sorozat.adasbaKerulesiDatum != DateTime.MinValue) {
        var adasbaKerulesNapja = Hetnapja(sorozat.adasbaKerulesiDatum.Year, sorozat.adasbaKerulesiDatum.Month, sorozat.adasbaKerulesiDatum.Day);

        if(bekertNap == adasbaKerulesNapja && !bekertNapraEsok.Contains(sorozat.cim)) {
            bekertNapraEsok.Add(sorozat.cim);
        }
    }
}

Console.WriteLine(bekertNapraEsok.Count == 0 ? "Az adott napon nem kerül adásba sorozat." : string.Join("\n", bekertNapraEsok));

var vetitesiIdoSorozatonkent = new Dictionary<string, int>();
var epizodokSzamaSorozatonkent = new Dictionary<string, int>();

foreach(var sorozat in sorozatok) {
    var cim = sorozat.cim;
    vetitesiIdoSorozatonkent.TryGetValue(cim, out int eddigiIdo);
    epizodokSzamaSorozatonkent.TryGetValue(cim, out int eddigiEpizodokSzama);

    vetitesiIdoSorozatonkent[cim] = eddigiIdo + sorozat.epizodonkentiHossz;
    epizodokSzamaSorozatonkent[cim] = eddigiEpizodokSzama + 1;
}

using var output = new StreamWriter("summa.txt");
foreach(var sorozatCimek in vetitesiIdoSorozatonkent.Keys) {
    output.WriteLine(sorozatCimek + " " + vetitesiIdoSorozatonkent[sorozatCimek] + " " + epizodokSzamaSorozatonkent[sorozatCimek]);
}

string Hetnapja(int ev, int ho, int nap) {
    ev = ho < 3 ? ev - 1 : ev;

    return napok[(ev + ev / 4 - ev / 100 + ev / 400 + honapok[ho - 1] + nap) % 7];
}