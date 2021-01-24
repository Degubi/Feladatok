using System;
using System.Collections.Generic;
using System.IO;

public class EUtazas {

    public static void Main(string[] args) {
        var lines = File.ReadAllLines("utasadat.txt");
        var utazasok = new List<Utazas>();

        foreach(var line in lines) {
            utazasok.Add(new Utazas(line));
        }

        Console.WriteLine($"2. Feladat: {utazasok.Count} db utas akart felszálni");

        var ervenytelenek = 0;
        foreach(var utazas in utazasok) {
            if(utazas.ErvenytelenE()) {
                ++ervenytelenek;
            }
        }

        Console.WriteLine($"3. Feladat: {ervenytelenek} utas nem szállhatott fel");

        var megalloStat = new Dictionary<int, int>();
        foreach(var utazas in utazasok) {
            var sorszam = utazas.megalloSorszama;
            megalloStat[sorszam] = megalloStat.GetValueOrDefault(sorszam, 0) + 1;
        }

        var legtobbMegalloEntry = megalloStat.GetEnumerator().Current;
        foreach(var entry in megalloStat) {
            if(legtobbMegalloEntry.Value < entry.Value) {
                legtobbMegalloEntry = entry;
            }
        }

        Console.WriteLine($"4. Feladat: legtöbb utas ({legtobbMegalloEntry.Value} fő) a {legtobbMegalloEntry.Key}-as megállóban próbált felszállni");

        var tipusStat = new Dictionary<string, int>();
        foreach(var utazas in utazasok) {
            if(utazas.ErvenyesE()) {
                var tipus = utazas.tipus;
                tipusStat[tipus] = tipusStat.GetValueOrDefault(tipus, 0) + 1;
            }
        }

        Console.WriteLine("5. Feladat");
        Console.WriteLine("Ingyenes utasok: " + (tipusStat["NYP"] + tipusStat["RVS"] + tipusStat["GYK"]));
        Console.WriteLine("Kedvezményes utasok: " + (tipusStat["TAB"] + tipusStat["NYB"]));

        using(var output = new StreamWriter("figyelmeztetes.txt")){
            foreach(var utazas in utazasok) {
                var napKulonbseg = (utazas.felszallas - utazas.ervenyesseg).Days;

                if(napKulonbseg >= 0 && napKulonbseg <= 3) {
                    output.WriteLine(utazas.kartyaAzonosito + " " + utazas.ervenyesseg);
                }
            }
        }
    }

    public static int Napokszama(int e1, int h1, int n1, int e2, int h2, int n2) {
        //Normál esetben nem módosítanák soha bemeneti paramétert, de a feladat kérte
        h1 = (h1 + 9) % 12;
        h2 = (h2 + 9) % 12;
        e1 = e1 - h1 / 10;
        e2 = e2 - h2 / 10;

        var d1 = 365 * e1 + e1 / 4 - e1 / 100 + e1 / 400 + (h1 * 306 + 5) / 10 + n1 - 1;
        var d2 = 365 * e2 + e2 / 4 - e2 / 100 + e2 / 400 + (h2 * 306 + 5) / 10 + n2 - 1;
        return d2 - d1;
    }
}