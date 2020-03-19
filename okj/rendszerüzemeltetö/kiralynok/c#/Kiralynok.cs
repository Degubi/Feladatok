using System;
using System.IO;

namespace Erettsegi {
    public class EUTazas {

        public static void Main(string[] args) {
            var tabla = new Tabla('#');
        
            Console.WriteLine("4. Feladat: Az üres tábla");
            tabla.megjelenit(Console.Out);
        
            Console.WriteLine("6. Feladat: A feltöltött tábla");
            tabla.elhelyez(8);
            tabla.megjelenit(Console.Out);
        
            Console.WriteLine("9. Feladat: Üres sorok és oszlopok száma");
            Console.WriteLine("Oszlopok: " + tabla.uresOszlopokSzama());
            Console.WriteLine("Sorok: " + tabla.uresSorokSzama());

            using(var file = new StreamWriter("tablak64.txt")){
                for(var i = 1; i < 65; ++i) {
                    var fileTabla = new Tabla('*');
                    fileTabla.elhelyez(i);
                
                    fileTabla.megjelenit(file);
                    file.Write('\n');
                }
            }
        }

        public class Tabla {
        private static readonly Random rand = new Random();
        
        private char[][] t;
        private char uresCella;
        
        public Tabla(char uresCella) {
            this.t = new char[8][];
            this.uresCella = uresCella;
            
            for(var x = 0; x < 8; ++x) {
                for(var y = 0; y < t[x].Length; ++y) {
                    t[x] = new char[8];
                    t[x][y] = uresCella;
                }
            }
        }
        
        public void megjelenit(TextWriter output) {
            for(var x = 0; x < 8; ++x) {
                for(var y = 0; y < 8; ++y) {
                    output.Write(t[x][y]);
                }
                output.Write('\n');
            }
        }
        
        public void elhelyez(int n) {
            for(var i = 0; i < n; ++i) {
                var randX = rand.Next(8);
                var randY = rand.Next(8);
                
                if(t[randX][randY] == uresCella) {
                    t[randX][randY] = 'K';
                }else{
                    --i;
                }
            }
        }
        
        public bool uresSor(int sor) {
            var indexeltSor = t[sor];
            
            for(var i = 0; i < 8; ++i) {
                if(indexeltSor[i] == 'K') {
                    return false;
                }
            }
            return true;
        }
        
        public bool uresOszlop(int oszlop) {
            for(var i = 0; i < 8; ++i) {
                if(t[i][oszlop] == 'K') {
                    return false;
                }
            }
            return true;
        }
        
        public int uresSorokSzama() {
            var ures = 0;
            
            for(var i = 0; i < 8; ++i) {
                if(uresSor(i)) {
                    ++ures;
                }
            }
            
            return ures;
        }
        
        public int uresOszlopokSzama() {
            var ures = 0;
            
            for(var i = 0; i < 8; ++i) {
                if(uresOszlop(i)) {
                    ++ures;
                }
            }
            
            return ures;
        }
    }
    }
}