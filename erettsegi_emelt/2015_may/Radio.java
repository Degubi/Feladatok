import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Radio {
	
	public static void main(String[] args) throws IOException {
		var file = Files.readAllLines(Paths.get("veetel.txt"));
		var feljegyzesek = new ArrayList<Feljegyzes>();
		
		for(int k = 0; k < file.size(); k += 2) {
			feljegyzesek.add(new Feljegyzes(file.get(k).split(" "), file.get(k + 1)));
		}
		
		System.out.println("2.Feladat\nElsõ feljegyzõ: " + feljegyzesek.get(0).radioAmator + ", utolsó: " + feljegyzesek.get(feljegyzesek.size() - 1).radioAmator);
		System.out.println("3.Feladat");
		
		for(var feljegyzes : feljegyzesek) {
			if(feljegyzes.adat.contains("farkas")) {
				System.out.println("Nap: " + feljegyzes.nap + ", feljegyzo: " + feljegyzes.radioAmator);
			}
		}
		
		System.out.println("4.Feladat");
		for(var k = 1; k < 12; ++k) {
			var napiSzam = 0;
			
			for(var feljegyzes : feljegyzesek) {
				if(feljegyzes.nap == k) {
					++napiSzam;
				}
			}
			System.out.println(k + ". napon levõ feljegyzések száma: " + napiSzam);
		}
		
		try(var output = new PrintWriter("adaas.txt")){
			for(var k = 1; k < 12; ++k) {
				char[] felj = null;
				
				for(var feljegyzes : feljegyzesek) {
					if(feljegyzes.nap == k) {
						if(felj == null) {
							felj = feljegyzes.adat.toCharArray();
						}else {
							for(int charIndex = 0; charIndex < felj.length; ++charIndex) {
								var jelenlegiChar = feljegyzes.adat.charAt(charIndex);
								
								if(felj[charIndex] == '#' && Character.isLetter(jelenlegiChar)) {
									felj[charIndex] = jelenlegiChar;
								}
							}
						}
					}
				}
				output.println(new String(felj));
			}
		}
		
		System.out.println("7.Feladat\nÍrj be 1 napot (1-11) és 1 megfigyelõ sorszámát!");
		try(var input = new Scanner(System.in)){
			var readNap = input.nextInt();
			var readMegfigyelo = input.nextInt();
			var egyedszam = 0;
			var voltIlyen = false;
			
			for(var feljegyzes : feljegyzesek) {
				if(feljegyzes.nap == readNap && feljegyzes.radioAmator == readMegfigyelo) {
					voltIlyen = true;
					egyedszam += feljegyzes.gyerekekSzama;
					egyedszam += feljegyzes.szulokSzama;
				}
			}
			
			if(voltIlyen) {
				if(egyedszam == 0) {
					System.out.println("Nem határozható meg");
				}else {
					System.out.println(egyedszam);
				}
			}else {
				System.out.println("Nem volt ilyen feljegyzés");
			}
		}
	}
	
	public static boolean szame(String szo) {
		var aze = true;
		
		for(int i = 1; i < szo.length(); ++i) {
			var jelenlegi = szo.charAt(i);
			
			if(jelenlegi < '0' || jelenlegi > '9') {
				aze = false;
			}
		}
		
		return aze;
	}
	
	public static class Feljegyzes{
		public final int nap;
		public final int radioAmator;
		public int szulokSzama;
		public int gyerekekSzama;
		public final String adat;
		
		public Feljegyzes(String[] data1, String data2) {
			nap = Integer.parseInt(data1[0]);
			radioAmator = Integer.parseInt(data1[1]);
			
			if(data2.contains("/")){
				var first = data2.charAt(0);
				var second = data2.charAt(2);
				
				if(first != '#') {
					szulokSzama = Character.getNumericValue(first);
				}
				if(second != '#') {
					Character.getNumericValue(second);  //TODO: Erre nem teljesen emlékszem... nemhiszem hogy jó
				}
				
				adat = data2.substring(3);
			}else {
				adat = data2;
				gyerekekSzama = 0;
				szulokSzama = 0;
			}
		}
	}
}