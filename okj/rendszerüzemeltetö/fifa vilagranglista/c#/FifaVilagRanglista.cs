using System;
using System.IO;
using System.Collections.Generic;

public class FifaVilagRanglista {
    
    public static void Main(string[] args) {
        var lines = File.ReadAllLines("fifa.txt");
        var eredmenyek = new List<Eredmeny>();
        
        for(var i = 1; i < lines.Length; ++i) {
            eredmenyek.Add(new Eredmeny(lines[i]));
        }
        
        Console.WriteLine("3. Feladat: Csapatok száma: " + eredmenyek.Count);
        
        var eredmenyTotal = 0F;
        foreach(var eredmeny in eredmenyek) {
            eredmenyTotal += eredmeny.pontszam;
        }
        
        Console.WriteLine("4. Feladat: Átlagpontszám: " + (eredmenyTotal / eredmenyek.Count).ToString("#.##"));
        
        var legtobbetJavito = eredmenyek[0];
        foreach(var eredmeny in eredmenyek) {
            if(eredmeny.valtozas > legtobbetJavito.valtozas) {
                legtobbetJavito = eredmeny;
            }
        }
        
        Console.WriteLine("5. Feladat: Legtöbbet javító csapat: " + legtobbetJavito.csapat +
                          ", helyezés: " + legtobbetJavito.helyezes + ", pontszam: " + legtobbetJavito.pontszam);
        
        var volteMo = false;
        foreach(var eredmeny in eredmenyek) {
            if(eredmeny.csapat == "Magyarország") {
                volteMo = true;
                break;
            }
        }
        
        if(volteMo) {
            Console.WriteLine("6. Feladat: Csapatok között van Magyarország");
        }else{
            Console.WriteLine("6. Feladat: Csapatok között nincs Magyarország");
        }
                               
        Console.WriteLine("7. Feladat:");
        
        var stat = new Dictionary<int, int>();
        foreach(var eredmeny in eredmenyek) {
            var key = eredmeny.valtozas;

            if(stat.ContainsKey(key)) {
                stat[key] = stat[key] + 1;
            }else{
                stat.Add(key, 1);
            }
        }
        
        foreach(var entry in stat) {
            if(entry.Value > 1) {
                Console.WriteLine("    " + entry.Key + " helyet változott: " + entry.Value + " csapat");
            }
        }
    }
}