import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Telefon_lambda {

	public static void main(String[] args) throws IOException {
		var hivasok = Files.lines(Path.of("hivas.txt")).map(Hivas::new).toArray(Hivas[]::new);
		
		System.out.println("3. Feladat");
		Arrays.stream(hivasok)
			  .map(k -> k.kezdet.getHour())
			  .collect(Collectors.groupingBy(k -> k, TreeMap::new, Collectors.counting()))
			  .forEach((ora, hivasszam) -> System.out.println(ora + " ora " + hivasszam + " hivas"));
		
		System.out.println("4. Feladat");
		Arrays.stream(hivasok)
			  .max(Comparator.comparing(k -> Duration.between(k.kezdet, k.veg)))
			  .ifPresent(k -> System.out.println("Leghosszabb hívás sorszáma: " + k.sorszam + ", hossza: " + Duration.between(k.kezdet, k.veg).toSeconds() + " mp"));
		
		try(var input = new Scanner(System.in)){
			System.out.println("5. Feladat: Adjon meg egy idopontot");
			
			var bekert = LocalTime.of(input.nextInt(), input.nextInt(), input.nextInt());
			var koztesHivasok = Arrays.stream(hivasok)
									  .filter(k -> bekert.isAfter(k.kezdet) && bekert.isBefore(k.veg))
									  .toArray(Hivas[]::new);
			
			if(koztesHivasok.length == 0) {
				System.out.println("Nem volt beszélõ");
			}else{
				System.out.println("Várakozók száma: " + (koztesHivasok.length - 1) + ", a beszélõ sorszáma: " + koztesHivasok[0].sorszam);
			}
		}
		
		System.out.println("7. Feladat");
		var del = LocalTime.of(12, 0);
		
		Arrays.stream(hivasok)
			  .filter(k -> k.kezdet.isBefore(del))
			  .reduce((l, r) -> r)
			  .ifPresent(k -> System.out.println("Utolsó beszélõ sorszáma: " + k.sorszam + ", ideje: " + Duration.between(k.kezdet, k.veg).toSeconds() + " mp"));
		
		//TODO 8-as feladat
	}
	
	public static int mpbe(int ora, int perc, int mp) {
		return mp + (perc * 60) + (ora * 3600);
	}
	
	static class Hivas{
		static int sorszamSzamlalo = 0;
		
		LocalTime kezdet;
		LocalTime veg;
		int sorszam;
		
		public Hivas(String sor) {
			var split = sor.split(" ");
			
			kezdet = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
			veg = LocalTime.of(Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]));
			sorszam = ++sorszamSzamlalo;
		}
	}
}