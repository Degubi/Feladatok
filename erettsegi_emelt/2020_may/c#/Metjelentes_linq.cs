using System;
using System.IO;
using System.Linq;

var adatok = File.ReadLines("tavirathu13.txt")
                 .Select(k => new IdojarasAdat(k))
                 .ToArray();

Console.WriteLine("2. Feladat: Írj be egy városkódot!");

var bekertKod = Console.ReadLine();
var utolsoMeres = adatok.Where(k => k.telepules == bekertKod)
                        .Last();

Console.WriteLine($"Utolsó mérés időpontja: {utolsoMeres.ido}");
Console.WriteLine("3. Feladat:");

var rendezettAdatok = adatok.OrderBy(k => k.homerseklet)
                            .ToArray();

var legalacsonyabbAdat = rendezettAdatok.First();
var legmagasabbAdat = rendezettAdatok.Last();

Console.WriteLine($"Legalacsonyabb hőmérséklet: {legalacsonyabbAdat.telepules} {legalacsonyabbAdat.ido.ToShortTimeString()} {legalacsonyabbAdat.homerseklet} fok");
Console.WriteLine($"Legmagasabb hőmérséklet: {legmagasabbAdat.telepules} {legmagasabbAdat.ido.ToShortTimeString()} {legmagasabbAdat.homerseklet} fok");
Console.WriteLine("4. Feladat:");

var szelcsendek = adatok.Where(k => k.szelIrany == "000" && k.szelErosseg == 0)
                        .ToArray();

if(szelcsendek.Length == 0){
    Console.WriteLine("Nem volt szélcsend a mérések idején.");
}else{
    Array.ForEach(szelcsendek, k => Console.WriteLine($"{k.telepules}: {k.ido.ToShortTimeString()}"));
}

Console.WriteLine("5. Feladat:");

var adatokVarosonkent = adatok.GroupBy(k => k.telepules)
                              .ToArray();

adatokVarosonkent.Select(k => TelepuleshezTartozoKiiratandoHomersekletAdat(k.Key, k))
                 .ToList()
                 .ForEach(Console.WriteLine);

Array.ForEach(adatokVarosonkent, k => TelepulesAdataiFajlba(k.Key, k));


void TelepulesAdataiFajlba(string telepules, IGrouping<string, IdojarasAdat> adatok) {
    var adatSorok = adatok.Select(k => k.ido.ToShortTimeString() + " " + (new String('#', k.szelErosseg)))
                          .Aggregate((l, r) => l + "\n" + r);

    File.WriteAllText($"{telepules}.txt", telepules + '\n' + adatSorok);
}

string TelepuleshezTartozoKiiratandoHomersekletAdat(string telepules, IGrouping<string, IdojarasAdat> adatok) {
    var ingadozas = adatok.Max(k => k.homerseklet) - adatok.Min(k => k.homerseklet);
    var mindenOraraVanAdat = adatok.Select(k => k.ido.Hour)
                                   .Distinct()
                                   .Count() == 24;
    if(mindenOraraVanAdat) {
        var atlagHomerseklet = adatok.Average(k => k.homerseklet);
        var kerekitettKozep = (int) Math.Ceiling(atlagHomerseklet);

        return $"{telepules}: Középhőmérséklet: {kerekitettKozep}; Ingadozás: {(int) ingadozas}";
    }

    return $"{telepules}: NA; Ingadozás: {(int) ingadozas}";
}