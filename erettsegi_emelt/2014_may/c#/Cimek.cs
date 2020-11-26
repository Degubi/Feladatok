using System;
using System.IO;

public class Cimek {

    public static void Main(string[] args) {
        var lines = File.ReadAllLines("ip.txt");
        Array.Sort(lines);

        Console.WriteLine("Adatsorok száma: " + lines.Length);
        Console.WriteLine("Legkisebb ip cím: " + lines[0]);
    
        var counters = new int[3];
        foreach(var ip in lines) {
            if(ip.StartsWith("2001:0db8")) {
                ++counters[0];
            }else if(ip.StartsWith("2001:0e")) {
                ++counters[1];
            }else {
                ++counters[2];
            }
        }

        Console.WriteLine("Dokumentációs címek: " + counters[0]);
        Console.WriteLine("Globális címek: " + counters[1]);
        Console.WriteLine("Helyi egyedi címek: " + counters[2]);

        using(var output = new StreamWriter("sok.txt")){
            foreach(var ip in lines) {
                var counter = 0;

                foreach(var karak in ip) {
                    if(karak == '0') {
                        ++counter;
                    }
                }

                if(counter > 17) {
                    output.WriteLine((Array.IndexOf(lines, ip) + 1) + " " + ip);
                }
            }
        }
        Console.WriteLine("Írj be 1 sorszámot!");

        var index = int.Parse(Console.ReadLine()) - 1;
        Console.WriteLine(lines[index] + " (Eredeti)");

        var roviditett = rov1(lines[index]);
        Console.WriteLine(roviditett + " (1. Rövidítés)");
        
        var roviditett2 = rov2(roviditett);
        Console.WriteLine(roviditett2 == roviditett ? "Nem lehet egyszerűsíteni" : roviditett2);
    }

    public static string rov1(string toRov) => toRov.Replace(":0", ":").Replace(":0", ":").Replace(":0", ":");
    public static string rov2(string toRov) => toRov.Replace(":0:0:0:", "::").Replace(":0:0:", "::");
}