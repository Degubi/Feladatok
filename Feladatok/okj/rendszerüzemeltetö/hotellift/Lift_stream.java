import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Lift_stream {
	public static void main(String[] args) throws IOException {
		var hasznalatok = Files.lines(Path.of("lift.txt")).map(Hasznalat::new).toArray(Hasznalat[]::new);
		
		System.out.println("3. Feladat: Lift alkalmak száma: " + hasznalatok.length);
		System.out.println("4. Feladat: A korszak " + hasznalatok[0].idopont + " tõl " + hasznalatok[hasznalatok.length - 1].idopont + "-ig tartott");
		
		Arrays.stream(hasznalatok)
			  .max(Comparator.comparingInt(k -> k.kartyaSorszam))
			  .ifPresent(k -> System.out.println("5. Feladat: Max kártya: " + k.kartyaSorszam + ", celszint: " + k.celSzint));
		
		System.out.println("6. Feladat: Írj be egy kártyaszámot és egy célszintet");
		try(var input = new Scanner(System.in)){
			var beKartya = parseOrDefault(input.nextLine(), 5);
			var beCelszint = parseOrDefault(input.nextLine(), 5);
			
			var kieg = Arrays.stream(hasznalatok)
						     .filter(k -> k.kartyaSorszam == beKartya)
						     .filter(k -> k.celSzint == beCelszint)
						     .findFirst()
						     .map(k -> "")
						     .orElse("nem");
			
			System.out.println("7. Feladat: A " + beKartya + " kártyával " + kieg + " utaztak a " + beCelszint + ". emeletre");
		}
		
		System.out.println("8. Feladat");
		Arrays.stream(hasznalatok)
			  .collect(Collectors.groupingBy(k -> k.idopont, Collectors.counting()))
			  .forEach((ido, db) -> System.out.println(ido + " - " + db + "x"));
	}
	
	static int parseOrDefault(String num, int defaultVal) {
		try {
			return Integer.parseInt(num);
		}catch(NumberFormatException e) {
			return defaultVal;
		}
	}
	
	public static class Hasznalat{
		public final LocalDate idopont;
		public final int kartyaSorszam;
		public final int induloSzint;
		public final int celSzint;
		
		public Hasznalat(String sor) {
			var split = sor.split(" ");
			
			idopont = LocalDate.parse(split[0].replace('.', '-').substring(0, split[0].length() - 1));
			kartyaSorszam = Integer.parseInt(split[1]);
			induloSzint = Integer.parseInt(split[2]);
			celSzint = Integer.parseInt(split[3]);
		}
	}
}