using System;
using System.IO;
using System.Collections.Generic;

public class Cbradio {

    public static void Main(string[] args) {
        var lines = File.ReadAllLines("cb.txt");
        var bejegyzesek = new List<Bejegyzes>();

        for(var i = 1; i < lines.Length; i++) {
            bejegyzesek.Add(new Bejegyzes(lines[i]));
        }

        Console.WriteLine("3. Feladat: Bejegyzések száma: " + bejegyzesek.Count);

        var voltE4AdastIndito = false;
        foreach(var bejegyzes in bejegyzesek) {
            if(bejegyzes.adasok == 4) {
                voltE4AdastIndito = true;
                break;
            }
        }

        Console.WriteLine("4. Feladat: " + (voltE4AdastIndito ? "Volt" : "Nem volt") + " 4 adást indító sofőr");
        Console.WriteLine("5. Feladat: Írj be egy nevet");

        var bekertNev = Console.ReadLine();
        var bekertHasznalatok = 0;

        foreach(var bejegyzes in bejegyzesek) {
            if(bejegyzes.nev == bekertNev) {
                bekertHasznalatok += bejegyzes.adasok;
            }
        }

        if(bekertHasznalatok > 0) {
            Console.WriteLine(bekertNev + " " + bekertHasznalatok + "x használta a rádiót");
        }else{
            Console.WriteLine("Nincs ilyen nevű sofőr!");
        }

        using(var file = new StreamWriter("cb2.txt")){
            file.WriteLine("Kezdes;Nev;AdasDb");

            foreach(var bejegyzes in bejegyzesek) {
                file.WriteLine(atszamolPercre(bejegyzes.ora, bejegyzes.perc) + ";" + bejegyzes.nev + ";" + bejegyzes.adasok);
            }
        }

        var egyediSoforok = new HashSet<string>();
        foreach(var bejegyzes in bejegyzesek) {
            egyediSoforok.Add(bejegyzes.nev);
        }

        Console.WriteLine("8. Feladat: Sofőrök száma: " + egyediSoforok.Count);

        var soforokAdasszamokkal = new Dictionary<string, int>();
        foreach(var bejegyzes in bejegyzesek) {
            var soforNeve = bejegyzes.nev;
            var oldValue = 0;

            soforokAdasszamokkal.TryGetValue(soforNeve, out oldValue);
            soforokAdasszamokkal.Add(soforNeve, oldValue + bejegyzes.adasok);
        }

        var legtobbAdasBejegyzes = soforokAdasszamokkal.GetEnumerator().Current;
        foreach(var bejegyzes in soforokAdasszamokkal) {
            if(bejegyzes.Value > legtobbAdasBejegyzes.Value) {
                legtobbAdasBejegyzes = bejegyzes;
            }
        }

        Console.WriteLine("9. Feladat: Legtöbb adást indító sofőr: " + legtobbAdasBejegyzes.Key + ", adások: " + legtobbAdasBejegyzes.Value);
    }

    public static int atszamolPercre(int ora, int perc) {
        return ora * 60 + perc;
    }
}