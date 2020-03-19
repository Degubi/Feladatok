using System;

namespace Erettsegi {
    public struct Keres {
        public readonly string cim;
        public readonly string datumIdo;
        public readonly string keres;
        public readonly string httpKod;
        public readonly string meret;
    
        public Keres(String line) {
            var firstSplit = line.Split('*');
            var utolsoSzokozIndex = firstSplit[3].IndexOf(' ');
        
            cim = firstSplit[0];
            datumIdo = firstSplit[1];
            keres = firstSplit[2];
            httpKod = firstSplit[3].Substring(0, utolsoSzokozIndex);
            meret = firstSplit[3].Substring(utolsoSzokozIndex + 1);
        }
    
        public int ByteMeret() {
            return meret == "-" ? 0 : int.Parse(meret);
        }
    
        public bool Domain() {
            return char.IsLetter(cim[cim.Length - 1]);
        }
    }
}