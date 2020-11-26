using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

public class Metjelentes_linq {

    public static void Main(string[] args) {
        var adatok = File.ReadLines("tavirathu13.txt")
                         .Select(k => new IdojarasAdat(k))
                         .ToArray();
        
        Console.WriteLine("2. Feladat: Írj be egy városkódot!");

        var bekertKod = Console.ReadLine();
        var utolsoMeres = adatok.Where(k => k.telepules == bekertKod)
                                .Last();

        Console.WriteLine("Utolsó mérés időpontja: " + utolsoMeres.ido);
        Console.WriteLine("3. Feladat:");

        var rendezettAdatok = adatok.OrderBy(k => k.homerseklet)
                                    .ToArray();

        var legalacsonyabbAdat = rendezettAdatok.First();
        var legmagasabbAdat = rendezettAdatok.Last();

        Console.WriteLine("Legalacsonyabb hőmérséklet: " + legalacsonyabbAdat.telepules + " " + legalacsonyabbAdat.ido + " " + legalacsonyabbAdat.homerseklet + " fok");
        Console.WriteLine("Legmagasabb hőmérséklet: " + legmagasabbAdat.telepules + " " + legmagasabbAdat.ido + " " + legmagasabbAdat.homerseklet + " fok");
        Console.WriteLine("4. Feladat:");

        var szelcsendek = adatok.Where(k => k.szelIrany == "000" && k.szelErosseg == 0)
                                .ToArray();

        if(szelcsendek.Length == 0){
            Console.WriteLine("Nem volt szélcsend a mérések idején.");
        }else{
            Array.ForEach(szelcsendek, k => Console.WriteLine(k.telepules + ": " + k.ido));
        }

        Console.WriteLine("5. Feladat:");

        var adatokVarosonkent = adatok.GroupBy(k => k.telepules)
                                      .ToArray();

        // TODO: 5. feladat

        Array.ForEach(adatokVarosonkent, k => telepulesAdataiFajlba(k.Key, k));
    }


    public static void telepulesAdataiFajlba(string telepules, IGrouping<string, IdojarasAdat> adatok) {
        var adatSorok = adatok.Select(k => k.ido + " " + (new String('#', k.szelErosseg)))
                              .Aggregate((l, r) => l + "\n" + r);

        File.WriteAllText(telepules + ".txt", telepules + '\n' + adatSorok);
    }
}