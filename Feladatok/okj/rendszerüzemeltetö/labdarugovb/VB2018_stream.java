import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class VB2018_stream {

	public static void main(String[] args) throws IOException {
		var helyszinek = Files.lines(Path.of("vb2018.txt"), StandardCharsets.ISO_8859_1)
							  .skip(1)
							  .map(Helyszin::new)
							  .toArray(Helyszin[]::new);
		
		System.out.println("3. Feladat");
		System.out.println("Stadionok száma: " + helyszinek.length);
		System.out.println("4. Feladat");
		Arrays.stream(helyszinek).min(Comparator.comparingInt(k -> k.ferohely)).ifPresent(System.out::println);
		
		System.out.println("5. Feladat");
		Arrays.stream(helyszinek)
			  .mapToInt(k -> k.ferohely)
			  .average()
			  .ifPresent(atlag -> System.out.printf("Ferohelyek atlaga: %.1f\n", atlag));
		
		System.out.println("6. Feladat");
		System.out.println("Alternativ neves stadionok: " + Arrays.stream(helyszinek).filter(k -> !k.nev2.equals("n.a.")).count());
		System.out.println("7. Feladat\nKérem 1 város nevét!");
		
		try(var input = new Scanner(System.in)){
			var olvasottNev = Stream.generate(input::nextLine)
								    .peek(line -> System.out.println("Kérem 1 város nevét!"))
								    .dropWhile(k -> k.length() < 3)
								    .findFirst()
								    .get();
			
			System.out.println("8. Feladat");
			Arrays.stream(helyszinek)
				  .filter(k -> k.varos.equalsIgnoreCase(olvasottNev))
				  .findFirst()
				  .ifPresentOrElse(k -> System.out.println("Volt " + olvasottNev + "-ban merkozes"), 
						  		  () -> System.out.println("Nem volt " + olvasottNev + "-ban merkozes"));
		}
		
		System.out.println("9. Feladat");
		System.out.println("Varosok szama: " + Arrays.stream(helyszinek).map(k -> k.varos).distinct().count());
	}
	
	public static class Helyszin{
		public final String varos;
		public final String nev1;
		public final String nev2;
		public final int ferohely;
		
		public Helyszin(String line) {
			var split = line.split(";");
			
			this.varos = split[0];
			this.nev1 = split[1];
			this.nev2 = split[2];
			this.ferohely = Integer.parseInt(split[3]);
		}
		
		@Override
		public String toString() {
			return "Varos: " + varos + ", nev1: " + nev1 + ", nev2: " + nev2 + ", ferohely: " + ferohely;
		}
	}
}