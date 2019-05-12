import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Hianyzasok_lambda {

	public static void main(String[] args) throws IOException {
		var honapNapSzamlalok = new int[2];
		var hianyzasok = Files.lines(Path.of("naplo.txt"))
							  .map(k -> k.split(" "))
							  .filter(split -> {
								  if(split[0].equals("#")) {
									  honapNapSzamlalok[0] = Integer.parseInt(split[1]);
									  honapNapSzamlalok[1] = Integer.parseInt(split[2]);
									  return false;
								  }
								  return true;
							  })
							  .map(split -> new Hianyzas(split[0] + ' ' +  split[1], split[2], honapNapSzamlalok[0], honapNapSzamlalok[1]))
							  .toArray(Hianyzas[]::new);
		
		System.out.println("2. Feladat: Hiányzások száma: " + hianyzasok.length);
		
		var igazoltak = Arrays.stream(hianyzasok)
							  .map(k -> k.orak)
							  .mapToLong(k -> k.chars().filter(l -> l == 'X').count())
							  .sum();
		
		var igazolatlanok = Arrays.stream(hianyzasok)
			   	  				  .map(k -> k.orak)
			   	  				  .mapToLong(k -> k.chars().filter(l -> l == 'I').count())
			   	  				  .sum();
		
		System.out.println("3. Feladat: Igazolt hiányzások: " + igazoltak + ", igazolatlanok: " + igazolatlanok);
		
		try(var input = new Scanner(System.in)){
			System.out.println("5. Feladat: Írjon be egy hónapot és egy napot");
			
			var beHonap = input.nextInt();
			var beNap = input.nextInt();
			
			System.out.println("Azon a napon: " + hetnapja(beHonap, beNap) + " volt");
			System.out.println("6. Feladat: Írja be 1 nap nevét és 1 óraszámot");
			
			var beTanNap = input.next();
			var beOraszam = input.nextInt() - 1;
			
			var szam = Arrays.stream(hianyzasok)
							 .filter(k -> beTanNap.equals(hetnapja(k.honap, k.nap)))
							 .map(k -> k.orak)
							 .mapToInt(k -> k.charAt(beOraszam))
							 .filter(k -> k == 'X' || k == 'I')
							 .count();
			
			System.out.println("Ekkor " + szam + "-an hiányoztak");
		}
		
		System.out.println("7. Feladat: ");
		
		var hianyzasMap = Arrays.stream(hianyzasok)
								.collect(Collectors.groupingBy(k -> k.nev, 
										 Collectors.summingLong(k -> k.orak.chars().filter(l -> l == 'X' || l == 'I').count())));
		
		var legtobbHianyzas = hianyzasMap.values()
										 .stream()
										 .mapToLong(k -> k)
										 .max()
										 .orElseThrow();
		
		hianyzasMap.entrySet().stream()
				   .filter(k -> k.getValue() == legtobbHianyzas)
				   .forEach(k -> System.out.print(k.getKey() + ' '));
	}
	
	public static String hetnapja(int honap, int nap) {
		var napnev = new String[] {"vasarnap", "hetfo", "kedd", "szerda", "csutortok", "pentek", "szombat"};
		var napszam = new int[] {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 335};
		var napsorszam = (napszam[honap - 1] + nap) % 7;
		
		return napnev[napsorszam];
	}
	
	static class Hianyzas{
		int honap;
		int nap;
		String nev;
		String orak;
		
		public Hianyzas(String nev, String orak, int honap, int nap) {
			this.nev = nev;
			this.orak = orak;
			this.honap = honap;
			this.nap = nap;
		}
	}
}