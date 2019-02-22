using System;
using System.Linq;
using System.IO;
 
namespace ConsoleApplication1{
    class Vasmegye {

        public static void Main(String[] args) {
            var szuletesek = File.ReadLines("vas.txt")
                                 .Select(a => new Szuletes(a))
                                 .Where(a => a.CdvEll())   //Hibásakat sosem tároljuk el
                                 .ToArray();

            Console.WriteLine("5. Feladat");
            Console.WriteLine("Csecsemők száma: " + szuletesek.Length);
            Console.WriteLine("6. Feladat");
            Console.WriteLine("Fiú csecsemők száma: " + szuletesek.Where(k => k.szamjegyek[0] % 2 == 1).Count());
            Console.WriteLine("7. Feladat");
            Console.WriteLine("Vizsgált időszak: Kezdet: " + szuletesek.Select(k => k.datum.Year).Min() + ", vége: " + szuletesek.Select(k => k.datum.Year).Max());
            Console.WriteLine("8. Feladat");
            Console.WriteLine(szuletesek.Where(k => k.datum.Year % 4 == 0).Count() > 0 ? "Volt baba szökőévben" : "");
            Console.WriteLine("9. Feladat");

            var evek = szuletesek.Select(k => k.datum.Year).Distinct().ToArray();
            Array.ForEach(evek, ev => Console.WriteLine(ev + "-ben " + szuletesek.Where(k => k.datum.Year == ev).Count() + " baba született"));
            Console.Read();
        }

        struct Szuletes {
            public DateTime datum;
            public int[] szamjegyek;

            public Szuletes(String line) {
                var split = line.Split('-');

                szamjegyek = line.ToCharArray()
                                 .Where(kar => kar != '-')
                                 .Select(kar => (int) char.GetNumericValue(kar))  //Karakterek rendes számmá
                                 .ToArray();

                datum = new DateTime(int.Parse((szamjegyek[0] < 3 ? "19" : "20") + split[1].Substring(0, 2)), //Év az alapján h 3-nál kisebb v nagyobb e az első szám
                                     int.Parse(split[1].Substring(2, 2)), //Hónap
                                     int.Parse(split[1].Substring(4, 2)));  //Nap
            }

            public bool CdvEll() {
                var szamok = szamjegyek;  //Fordító hiba ellen, lambda nem tudja capturolni...
                return szamok[10] == Enumerable.Range(0, 10).Select(index => szamok[index] * (10 - index)).Sum() % 11;
            }
        }
    }
}