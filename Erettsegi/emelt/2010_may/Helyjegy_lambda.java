import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helyjegy_lambda {
	
	public static void main(String[] args) throws IOException {
		var file = Files.readAllLines(Paths.get("eladott.txt"));
		var firstSplit = file.get(0).split(" ");
		
		int eladottJegyek = Integer.parseInt(firstSplit[0]);
		int utHossz = Integer.parseInt(firstSplit[1]);
		int ar = Integer.parseInt(firstSplit[2]);  //10 km-ként
		
		Utas[] utasok = file.stream()
							.map(k -> k.split(" "))
							.map(Utas::new)
							.toArray(Utas[]::new);
		
		Utas utolso = utasok[utasok.length - 1];
		System.out.println("2.Feladat\nUtolsó utas ülése: " + utolso.ules + " utazott távolság: " + utolso.getTavolsag());
		
		System.out.println("3.Feladat");
		Stream.of(utasok).filter(k -> k.getTavolsag() == utHossz).forEach(k -> System.out.print(k.sorszam + " "));

		System.out.println("\n4.Feladat\nÖsszes bevétel: " + Stream.of(utasok).mapToInt(k -> k.getTavolsag()).sum());
		
		int uccso = Stream.of(utasok)
						  .filter(k -> k.end != utHossz)
						  .max(Comparator.comparingInt(k -> k.end))
						  .get().end;
		
		int felszallok = Stream.of(utasok)
							   .filter(k -> k.start == uccso)
							   .mapToInt(k -> 1)
							   .sum();
		
		int leszallok = Stream.of(utasok)
							  .filter(k -> k.end == uccso)
							  .mapToInt(k -> 1)
							  .sum();

		System.out.println("5.Feladat\nUtolsó megállónál felszállók: " + felszallok + ", leszállók: " + leszallok);
		
		int[] allomasok = IntStream.concat(Stream.of(utasok).mapToInt(k -> k.end).distinct(), 
				Stream.of(utasok).mapToInt(k -> k.start).distinct())
					  .distinct()
				 	  .toArray();
		
		System.out.println("6.Feladat\nMegállók száma: " + (allomasok.length - 2));
		
		try(PrintWriter output = new PrintWriter("kihol.txt"); Scanner input = new Scanner(System.in)){
			System.out.println("Írj be 1 km számot!");
			int readTav = input.nextInt();
			
			IntStream.rangeClosed(1, 48)
					 .forEach(index -> {
						 System.out.println(index + ". ülés");
						 Stream.of(utasok)
						 	   .filter(k -> k.ules == index)
						 	   .filter(k -> k.start == readTav || k.end == readTav)
						 	   .findFirst().ifPresentOrElse(utas -> System.out.println(utas.sorszam + ". utas"), () -> System.out.println("üres"));});
		}
	}
	
	static class Utas{
		int ules, start, end, sorszam;
		static int counter = 0;
		
		public Utas(String[] data) {
			sorszam = ++counter;
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