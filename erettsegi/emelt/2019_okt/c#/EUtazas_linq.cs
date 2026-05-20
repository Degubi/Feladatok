using System;
using System.IO;
using System.Linq;

var utazasok = File.ReadLines("utasadat.txt")
                   .Select(k => new Utazas(k))
                   .ToArray();

Console.WriteLine($"2. Feladat: {utazasok.Length} db utas akart felszálni");

var ervenytelenek = utazasok.Where(k => k.ErvenytelenE())
                            .Count();

Console.WriteLine($"3. Feladat: {ervenytelenek} utas nem szállhatott fel");

var legtobbUtasos = utazasok.GroupBy(k => k.megalloSorszama)
                            .Select(k => new{ Megallo = k.Key, DarabSzam = k.Count() })
                            .OrderByDescending(k => k.DarabSzam)
                            .First();

Console.WriteLine($"4. Feladat: legtöbb utas ({legtobbUtasos.DarabSzam} fő) a {legtobbUtasos.Megallo}-as megállóban próbált felszállni");

var tipusStat = utazasok.Where(k => k.ErvenyesE())
                        .GroupBy(k => k.tipus)
                        .ToDictionary(k => k.Key, k => k.Count());

Console.WriteLine("5. Feladat");
Console.WriteLine("Ingyenes utasok: " + (tipusStat["NYP"] + tipusStat["RVS"] + tipusStat["GYK"]));
Console.WriteLine("Kedvezményes utasok: " + (tipusStat["TAB"] + tipusStat["NYB"]));

var haromnaposok = utazasok.Where(k => {
                                var napKulonbseg = (k.felszallas - k.ervenyesseg).Days;
                                return napKulonbseg >= 0 && napKulonbseg <= 3;
                           })
                           .Select(k => k.kartyaAzonosito + " " + k.ervenyesseg);

File.WriteAllLines("figyelmeztetes.txt", haromnaposok);


int Napokszama(int e1, int h1, int n1, int e2, int h2, int n2) {
    h1 = (h1 + 9) % 12;
    h2 = (h2 + 9) % 12;
    e1 = e1 - h1 / 10;
    e2 = e2 - h2 / 10;

    var d1 = 365 * e1 + e1 / 4 - e1 / 100 + e1 / 400 + (h1 * 306 + 5) / 10 + n1 - 1;
    var d2 = 365 * e2 + e2 / 4 - e2 / 100 + e2 / 400 + (h2 * 306 + 5) / 10 + n2 - 1;
    return d2 - d1;
}