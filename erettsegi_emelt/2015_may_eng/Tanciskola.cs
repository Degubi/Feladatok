using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Prog{
    class Tanciskola{
        static void Main(string[] args){
            string[] file = File.ReadAllLines("tancrend.txt");
		    List<Tanc> tancok = new List<Tanc>();
		
		    for(int k = 0; k < file.Length; k += 3) {
			    tancok.Add(new Tanc(file[k], file[k + 1], file[k + 2]));
		    }
		
		    Console.WriteLine("Első tánc neve: " + tancok[0].category + ", az utolsóé: " + tancok[tancok.Count - 1].category);
		
		    int szambaCounter = 0;
		    foreach(Tanc dansz in tancok) {
			    if(dansz.category.Equals("samba")) {
				    ++szambaCounter;
			    }
		    }
		    Console.WriteLine("Összesen " + szambaCounter + "-an szambásztak");
		
		    List<String> viliCat = new List<String>();
		    foreach(Tanc dansz in tancok) {
			    if(dansz.woman.Equals("Vilma") && !viliCat.Contains(dansz.category)) {
				    viliCat.Add(dansz.category);
			    }
		    }
		    Console.WriteLine("Vilma által táncolt kategóriák: " + viliCat.Aggregate((k, l) => k + ", " + l));
		    Console.WriteLine("Írj be 1 kategóriát!");
		
			String readCat = Console.ReadLine();
			bool hasPrinted = false;
			foreach(Tanc dansz in tancok) {
			    if(dansz.woman.Equals("Vilma") && dansz.category.Equals(readCat)) {
				    Console.WriteLine("Vilma a " + readCat + " kategóriában " + dansz.man + "-val táncolt");
				    hasPrinted = true;
				}
			 }
			 if(!hasPrinted) {
				 Console.WriteLine("Vilma a " + readCat + " kategóriában nem táncolt");
			 }
		    
		    HashSet<String> csajok = new HashSet<String>();
		    HashSet<String> skacok = new HashSet<String>();
		    List<Szereplo> lanyok = new List<Szereplo>();
		    List<Szereplo> fiuk = new List<Szereplo>();

		    StreamWriter output = new StreamWriter("szereplok.txt");
		    foreach(Tanc dans in tancok) {
			    csajok.Add(dans.woman);
			    skacok.Add(dans.man);
		    }
		    foreach(String cs in csajok) {
			    lanyok.Add(new Szereplo(cs));
		    }
		    foreach(String fik in skacok) {
			    fiuk.Add(new Szereplo(fik));
		    }
		    output.Write("Lányok: ");
		
		    for(int k = 0; k < lanyok.Count; ++k) {
			    if(k != lanyok.Count - 1) {
				    output.Write(lanyok[k] + ", ");
			    }else{
				    output.Write(lanyok[k]);
			    }
		    }
		    output.WriteLine();
		    output.Write("Fiúk: ");
		    for(int k = 0; k < fiuk.Count; ++k) {
			    if(k != fiuk.Count - 1) {
				    output.Write(fiuk[k] + ", ");
			    }else{
				    output.Write(fiuk[k]);
			    }
		    }
		    output.Close();
		
		    foreach(Tanc dan in tancok) {
			    foreach(Szereplo csaj in lanyok) {
				    if(dan.woman.Equals(csaj.name)) {
					    ++csaj.alkalmak;
				    }
			    }
			    foreach(Szereplo fiu in fiuk) {
				    if(dan.man.Equals(fiu.name)) {
					    ++fiu.alkalmak;
				    }
			    }
		    }

            lanyok = lanyok.OrderByDescending(k => k.alkalmak).ToList();
            fiuk = fiuk.OrderByDescending(k => k.alkalmak).ToList();
		
		    int lanyMax = lanyok[0].alkalmak;
		    int fiuMax = fiuk[0].alkalmak;
		
		    Console.Write("A legtöbbet táncolt lányok: ");
		    foreach(Szereplo la in lanyok) {
			    if(la.alkalmak == lanyMax) {
				    Console.Write(la.name + " ");
			    }
		    }
		    Console.WriteLine();
		    Console.Write("A legtöbbet táncolt fiúk: ");
		    foreach(Szereplo fi in fiuk) {
			    if(fi.alkalmak == fiuMax) {
				    Console.Write(fi.name + " ");
			    }
		    }
            Console.Read();
	    }
	    class Tanc{
		    public String category, woman, man;
		    public Tanc(String cat, String wom, String ma) {
			    category = cat;
			    woman = wom;
			    man = ma;
		    }
	    }
	    class Szereplo{
		    public String name;
		    public int alkalmak = 0;
		    public Szereplo(String neim) {
			    name = neim;
		    }
		    public override string ToString() {
			    return name;
		    }
		    public int getAlkalmak() {
			    return alkalmak;
		    }
	    }
    }
}