using System;
using System.Collections.Generic;
using System.IO;

public class Metjelentes {

    public static void Main(string[] args) {
        var adatok = new List<IdojarasAdat>();
        foreach(var line in File.ReadAllLines("tavirathu13.txt")) {
            adatok.Add(new IdojarasAdat(line));
        }
        
        Console.WriteLine("2. Feladat: Írj be egy városkódot!");
        var bekertKod = Console.ReadLine();
        
        for(var i = adatok.Count - 1; i >= 0; --i) {
            var jelenlegi = adatok[i];
            
            if(jelenlegi.telepules == bekertKod) {
                Console.WriteLine("Utolsó mérés időpontja: " + jelenlegi.ido);
                break;
            }
        }
        
        Console.WriteLine("3. Feladat:");
        var minHomersekletes = adatok[0];
        var maxHomersekletes = adatok[0];
        
        foreach(var adat in adatok) {
            if(adat.homerseklet > maxHomersekletes.homerseklet) {
                maxHomersekletes = adat;
            }
            
            if(adat.homerseklet < minHomersekletes.homerseklet) {
                minHomersekletes = adat;
            }
        }
        
        Console.WriteLine("Legalacsonyabb hőmérséklet: "  + minHomersekletes.telepules + " " + minHomersekletes.ido + " " + minHomersekletes.homerseklet + " fok");
        Console.WriteLine("Legmagasabb hőmérséklet: " + maxHomersekletes.telepules + " " + maxHomersekletes.ido + " " + maxHomersekletes.homerseklet + " fok");
        Console.WriteLine("4. Feladat:");
        
        var szelcsendek = new List<IdojarasAdat>();
        foreach(var adat in adatok) {
            if(adat.szelIrany == "000" && adat.szelErosseg == 0) {
                szelcsendek.Add(adat);
            }
        }
        
        if(szelcsendek.Count == 0){
            Console.WriteLine("Nem volt szélcsend a mérések idején.");
        }else{
            foreach(var csendes in szelcsendek) {
                Console.WriteLine(csendes.telepules + ": " + csendes.ido);
            }
        }
        
        Console.WriteLine("5. Feladat:");
        
        var adatokTelepulesenkent = new Dictionary<string, List<IdojarasAdat>>();
        foreach(var adat in adatok) {
            var telepules = adat.telepules;
            var telepulesAdatai = adatokTelepulesenkent.ContainsKey(telepules) ? adatokTelepulesenkent[telepules] : null;
            
            if(telepulesAdatai == null) {
                telepulesAdatai = new List<IdojarasAdat>();
                adatokTelepulesenkent.Add(telepules, telepulesAdatai);
            }
            
            telepulesAdatai.Add(adat);
        }
        
        foreach(var (telepules, telepuleshezTartozoAdatok) in adatokTelepulesenkent) {
            var egyediAdattalRendelkezoOrak = new HashSet<int>();
            var legkisebbHomerseklet = 2000;
            var legnagyobbHomerseklet = -2000;
            var osszHomerseklet = 0;
            var homersekletekSzama = 0;
            
            foreach(var adat in telepuleshezTartozoAdatok) {
                var homerseklet = adat.homerseklet;
                
                if(homerseklet < legkisebbHomerseklet) {
                    legkisebbHomerseklet = homerseklet;
                }
                    
                if(homerseklet > legnagyobbHomerseklet) {
                    legnagyobbHomerseklet = homerseklet;
                }
                
                osszHomerseklet += homerseklet;
                ++homersekletekSzama;
                egyediAdattalRendelkezoOrak.Add(adat.ido.Hour);
            }
            
            var ingadozas = legnagyobbHomerseklet - legkisebbHomerseklet;
            
            if(egyediAdattalRendelkezoOrak.Count == 24) {
                var kozep = ((double) osszHomerseklet) / homersekletekSzama;
                var kerekitettKozep = (int) Math.Round(kozep);
                
                Console.WriteLine(telepules + ": Középhőmérséklet: " + kerekitettKozep + "; Ingadozás: " + ingadozas);
            }else{
                Console.WriteLine(telepules + ": NA; Ingadozás: " + ingadozas);
            }
        }
        
        Console.WriteLine("6. Feladat:");
        
        foreach(var (telepules, telepuleshezTartozoAdatok) in adatokTelepulesenkent) {
            using(var file = new StreamWriter(telepules + ".txt")) {
                file.WriteLine(telepules);
                
                foreach(var adat in telepuleshezTartozoAdatok) {
                    file.WriteLine(adat.ido + " " + new string('#', adat.szelErosseg));
                }
            }
        }
    }
}