import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Vasmegye_lambda {

	public static void main(String[] args) throws IOException {
		var szuletesek = Files.lines(Path.of("vas.txt"))
							  .map(Szuletes::new)
							  .filter(Szuletes::cdvEll)   //Hibásakat sosem tároljuk el
							  .toArray(Szuletes[]::new);
		
		System.out.println("5. Feladat");
		System.out.println("Csecsemõk száma: " + szuletesek.length);
		System.out.println("6. Feladat");
		System.out.println("Fiú csecsemõk száma: " + Arrays.stream(szuletesek).filter(k -> k.szamjegyek[0] % 2 == 1).count());
		System.out.println("7. Feladat");
		
		var stat = Arrays.stream(szuletesek).mapToInt(k -> k.datum.getYear()).summaryStatistics();
		System.out.println("Vizsgált idõszak: Kezdet: " + stat.getMin() + ", vége: " + stat.getMax());
		
		System.out.println("8. Feladat");
		Arrays.stream(szuletesek)
			  .filter(k -> k.datum.getYear() % 4 == 0)
			  .findFirst()
			  .ifPresent(k -> System.out.println("Volt baba szökõévben"));
		
		System.out.println("9. Feladat");
		Arrays.stream(szuletesek)
			  .collect(Collectors.groupingBy(k -> k.datum.getYear(), LinkedHashMap::new, Collectors.counting())) //LinkedHashMap Supplier, különben az évek random sorrendben lesznek
			  .forEach((ev, babak) -> System.out.println(ev + "-ben " + babak + " baba szuletett"));
	}
	
	static class Szuletes{
		LocalDate datum;
		int[] szamjegyek;
		
		public Szuletes(String line) {
			var split = line.split("-");
			
			szamjegyek = line.chars()
							 .filter(kar -> kar != '-')
							 .map(Character::getNumericValue)  //Kell, mert a 'chars' stream az karakterkód stream
							 .toArray();
			
			datum = LocalDate.of(Integer.parseInt((szamjegyek[0] < 3 ? "19" : "20") + split[1].substring(0, 2)), //Év az alapján h 3-nál kisebb v nagyobb e az elsõ szám
					 Integer.parseInt(split[1].substring(2, 4)), //Hónap
					 Integer.parseInt(split[1].substring(4, 6)));  //Nap
		}
		
		public boolean cdvEll() {
			return szamjegyek[10] == IntStream.rangeClosed(0, 9).map(index -> szamjegyek[index] * (10 - index)).sum() % 11;
		}
	}
}