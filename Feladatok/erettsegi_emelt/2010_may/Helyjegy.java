import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Helyjegy {
	
	public static void main(String[] args) throws IOException {
		var file = Files.readAllLines(Paths.get("eladott.txt"));
		var firstSplit = file.get(0).split(" ");
		
		int eladottJegyek = Integer.parseInt(firstSplit[0]);
		int utHossz = Integer.parseInt(firstSplit[1]);
		int ar = Integer.parseInt(firstSplit[2]);  //10 km-ként
		var utasok = new ArrayList<Utas>();
		
		for(int k = 1; k < file.size(); ++k) {
			utasok.add(new Utas(file.get(k).split(" ")));
		}
		
		Utas utolso = utasok.get(utasok.size() - 1);
		System.out.println("2.Feladat: Utolsó utas ülése: " + utolso.ules + " utazott távolság: " + utolso.getTavolsag());
		
		System.out.println("3.Feladat");
		int osszesPenz = 0;
		for(Utas utas : utasok) {
			osszesPenz += utas.getAr(ar);
			if(utas.getTavolsag() == utHossz) {
				System.out.print(utas.sorszam + " ");
			}
		}
		System.out.println("\n4.Feladat: Összes bevétel: " + osszesPenz);
		
		int utolsoMegallo = 0;
		for(Utas utas : utasok) {
			if(utas.end > utolsoMegallo && utas.end != utHossz) {
				utolsoMegallo = utas.end;
			}
		}
		
		int felszallok = 0, leszallok = 0;
		for(Utas utas : utasok) {
			if(utas.start == utolsoMegallo) {
				++felszallok;
			}
			if(utas.end == utolsoMegallo) {
				++leszallok;
			}
		}
		System.out.println("5.Feladat: Utolsó megállónál felszállók: " + felszallok + ", leszállók: " + leszallok);
		
		HashSet<Integer> allomasok = new HashSet<>();
		for(Utas utas : utasok) {
			allomasok.add(utas.start);
			allomasok.add(utas.end);
		}
		System.out.println("6.Feladat: Megállók száma: " + (allomasok.size() - 2));
		
		try(var output = new PrintWriter("kihol.txt"); Scanner input = new Scanner(System.in)){
			System.out.println("Írj be 1 km számot!");
			int readTav = input.nextInt();
			
			for(int k = 1; k < 49; ++k) {
				Utas currentUtas = null;
				for(Utas utas : utasok) {
					if(utas.ules == k && (utas.start == readTav || utas.end == readTav)) {
						currentUtas = utas;
					}
				}
				output.println(k + ". ülés: " + (currentUtas == null ? "üres" : (currentUtas.sorszam + ". utas")));
			}
		}
	}
	
	static class Utas{
		int ules, start, end, sorszam;
		static int index = 0;
		
		public Utas(String[] data) {
			sorszam = ++index;
			ules = Integer.parseInt(data[0]);
			start = Integer.parseInt(data[1]);
			end = Integer.parseInt(data[2]);
		}
		
		public int getTavolsag() {
			return end - start;
		}
		
		public int getAr(int kmAr) {
			int tav = getTavolsag();
			int utolso = tav % 10;
			int tizesek = tav / 10;
			if(utolso == 3 || utolso == 4 || utolso == 8 || utolso == 9) {
				++tizesek;
			}
			return kmAr * tizesek;
		}
	}
}