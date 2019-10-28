using System;
using System.Linq;
using System.IO;
 
namespace ConsoleApplication1{
    class Cimek_linq{
        static void Main(string[] args){
            var lines = File.ReadAllLines("ip.txt");
        
            Console.WriteLine("Adatsorok száma: " + lines.Length);
            Console.WriteLine("Legkisebb ip cím: " + lines.Min(k => k));
            Console.WriteLine("Dokumentációs címek: " + lines.Where(k => k.StartsWith("2001:0db8")).Count());
            Console.WriteLine("Globális címek: " + lines.Where(k => k.StartsWith("2001:0e")).Count());
            Console.WriteLine("Helyi egyedi címek: " + lines.Where(k => k.StartsWith("fc") || k.StartsWith("fd")).Count());
        
            File.WriteAllLines("sok.txt", lines.Where(ip => ip.Where(l => l == '0').Count() > 17)
                                               .Select(ip => Array.IndexOf(lines, ip) + 1 + " " + ip)
                                               .ToArray());

            int index = int.Parse(Console.ReadLine()) - 1;
            Console.WriteLine(lines[index] + " (Eredeti)");
            string roviditett = rov1(lines[index]);
            Console.WriteLine(roviditett + " (1. Rövidítés)");
            string roviditett2 = rov2(roviditett);
            Console.WriteLine(roviditett2 == roviditett ? "Nem lehet egyszerűsíteni" : roviditett2);

            Console.Read();
        }

        public static string rov1(string toRov) => toRov.Replace(":0", ":").Replace(":0", ":").Replace(":0", ":");
        public static string rov2(string toRov) => toRov.Replace(":0:0:0:", "::").Replace(":0:0:", "::");
    }
}