using System;
using System.IO;
 
namespace ConsoleApplication1{
    class Cimek{
        static void Main(string[] args){
            string[] lines = File.ReadAllLines("ip.txt");
            Array.Sort(lines);

            Console.WriteLine("Adatsorok száma: " + lines.Length);
            Console.WriteLine("Legkisebb ip cím: " + lines[0]);
        
            int[] counters = new int[3];
            foreach(string ip in lines) {
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

            using(StreamWriter output = new StreamWriter("sok.txt")){
                foreach(string ip in lines) {
                    int counter = 0;
                    foreach(char karak in ip) {
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