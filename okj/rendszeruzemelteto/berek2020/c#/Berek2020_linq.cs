using System;
using System.IO;
using System.Linq;


var dolgozok = File.ReadLines("berek2020.txt")
                   .Skip(1)
                   .Select(k => new Dolgozo(k))
                   .ToArray();

var atlagBer = dolgozok.Average(k => k.munkaBer);

Console.WriteLine($"3. Feladat: Dolgozók száma: {dolgozok.Length}");
Console.WriteLine($"4. Feladat: Átlagbér: {(atlagBer / 1000).ToString("0.00")}");
Console.WriteLine("5. Feladat: Írjon be 1 részleg nevet!");
Console.Write("6. Feladat: ");

var bekertReszleg = Console.ReadLine();
var bekertReszlegbenDolgozok = dolgozok.Where(k => k.munkaReszleg == bekertReszleg).ToArray();
var legtobbMunkaber = bekertReszlegbenDolgozok.Length == 0 ? 0 : bekertReszlegbenDolgozok.Max(k => k.munkaBer);
var legtobbBeresDolgozo = bekertReszlegbenDolgozok.Length == 0 ? null : bekertReszlegbenDolgozok.Where(k => k.munkaBer == legtobbMunkaber).First();

if(legtobbBeresDolgozo == null) {
    Console.WriteLine("A megadott részleg nem létezik a cégnél!");
}else{
    Console.WriteLine($"{legtobbBeresDolgozo.nev} ({legtobbBeresDolgozo.munkabaLepesEv}) : {legtobbBeresDolgozo.munkaBer} FT");
}

Console.WriteLine("7. Feladat:");

dolgozok.GroupBy(k => k.munkaReszleg)
        .Select(k => new {
            Reszleg = k.Key,
            DbSzam = k.Count()
        })
        .ToList()
        .ForEach(k => Console.WriteLine($"    {k.Reszleg} - {k.DbSzam} fő"));