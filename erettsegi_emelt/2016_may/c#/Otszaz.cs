using System;
using System.Collections.Generic;
using System.IO;

var lines = File.ReadAllLines("penztar.txt");
var vasarlasok = new List<Vasarlas>();
var toAdd = new List<string>();

foreach(var sor in lines) {
    if(sor == "F") {
        vasarlasok.Add(new Vasarlas(toAdd));
        toAdd.Clear();
    }else{
        toAdd.Add(sor);
    }
}

Console.WriteLine("Vásárlások száma: " + vasarlasok.Count);
Console.WriteLine("Elsö vásárlásnál vett dolgok száma: " + vasarlasok[0].dolgok.Count);

Console.WriteLine("Írj be 1 sorszámot");
var sorszam = int.Parse(Console.ReadLine());

Console.WriteLine("Írj be 1 árut");
var aru = Console.ReadLine();

Console.WriteLine("Írj be 1 mennyiséget");
var dbszam = int.Parse(Console.ReadLine());

var amount = 0;
var utolso = 0;
for(var k = 0; k < vasarlasok.Count; ++k) {
    foreach(var entries in vasarlasok[k].dolgok.Keys){
        if(entries == aru) {
            ++amount;
            utolso = k;

            if(amount == 1) {
                Console.WriteLine("Először a " + ++k + ". vásárlásnál vettek " + aru + "-t");
            }
        }
    }
}

Console.WriteLine("Utoljára a " + ++utolso + ". vásárlásnál vettek " + aru + "-t");
Console.WriteLine("Összesen " + amount + "-szor vettek " + aru + "-t");
Console.WriteLine(dbszam + " db esetén a fizetendő: " + ertek(dbszam));
Console.WriteLine("A " + sorszam + ". vásárláskor vásárolt dolgok: " + vasarlasok[sorszam - 1].dolgok.ToString());

using var output = new StreamWriter("osszeg.txt");

for(int k = 0; k < vasarlasok.Count; ++k) {
    var printMount = 0;

    foreach(var entries in vasarlasok[k].dolgok.Values) {
        printMount += ertek(entries);
    }
    output.WriteLine((k + 1).ToString() + ":" + printMount);
}

int ertek(int dbSzam) => dbSzam == 1 ? 500 : dbSzam == 2 ? 950 : 1350 + (400 * (dbSzam - 3));