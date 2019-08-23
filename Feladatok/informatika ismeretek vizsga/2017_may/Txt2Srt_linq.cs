using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
 
namespace Txt2Str_2017_emelt{
    class Txt2Srt_linq{
        static void Main(string[] args){
            var feliratok = new List<IdozitettFelirat>();
            var adatok = File.ReadAllLines("feliratok.txt");

            for(int i = 0; i < adatok.Length; i += 2){
                feliratok.Add(new IdozitettFelirat(adatok[i], adatok[i + 1]));
            }

            Console.WriteLine("5. feladat: A feliratok száma: " + feliratok.Count);

            Console.WriteLine("7. feladat: A legtöbb szóból álló felirat:\n" 
                                + feliratok.First(f => f.SzavakSzama() == feliratok.Max(k => k.SzavakSzama())).felirat);
            
            using(StreamWriter output = new StreamWriter("felirat.srt")) {
                for(int i = 0; i < feliratok.Count; ++i){
                    output.WriteLine(i + 1);
                    output.WriteLine(feliratok[i].StrIdozites());
                    output.WriteLine(feliratok[i].felirat);
                    output.WriteLine();
                }
            }
            Console.ReadKey();
        }
    }

    class IdozitettFelirat{
        public string idozites;
        public string felirat;

        public IdozitettFelirat(string idoz, string felir){
            idozites = idoz;
            felirat = felir;
        }

        public int SzavakSzama(){
             return felirat.Where(k => k == ' ').Count() + 1;
        }
        
        private string Str_idoforma(string ido){
            var perc_mp = ido.Split(':');
            int pars = int.Parse(perc_mp[0]);
            return string.Format("{0:00}:{1:00}", pars / 60, pars % 60) + ":" + perc_mp[1];
        }

        public string StrIdozites(){
             return Str_idoforma(idozites.Split('-')[0]) + "--> " + Str_idoforma(idozites.Split('-')[1]);
        }
    }
}