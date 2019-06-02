import static java.nio.file.StandardOpenOption.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Hianyzasok_lambda {

	public static void main(String[] args) throws IOException {
		var hianyzasok = Files.lines(Path.of("szeptember.csv"))
							  .skip(1)
							  .map(Hianyzas::new)
							  .toArray(Hianyzas[]::new);
		
		System.out.println("2. Feladat: Hianyzott orak: " + Arrays.stream(hianyzasok).mapToInt(k -> k.mulasztottOrak).sum());
		System.out.println("3. Feladat: Írj be egy napot(1-30) és egy nevet!");
		
		try(var input = new Scanner(System.in)){
			var bekertNap = input.nextInt();
			input.nextLine();
			var bekertNev = input.nextLine();
			
			System.out.println("4. Feladat");
			Arrays.stream(hianyzasok)
				  .filter(k -> k.nev.equals(bekertNev))
				  .findFirst()
				  .ifPresentOrElse(k -> System.out.println(bekertNev + " hianyzott"), 
						  		  () -> System.out.println(bekertNev + " nem hiányzott"));
			
			System.out.println("5. Feladat");
			var azonANaponHianyoztak = Arrays.stream(hianyzasok)
											 .filter(k -> bekertNap >= k.elsoNap && bekertNap <= k.utolsoNap)
											 .toArray(Hianyzas[]::new);
			
			if(azonANaponHianyoztak.length == 0) {
				System.out.println("Nem volt hiányzó");
			}else {
				Arrays.stream(azonANaponHianyoztak).forEach(hiany -> System.out.println(hiany.nev + " " + hiany.osztaly));
			}
		}
		
		var hianyzasokStat = Arrays.stream(hianyzasok)
								   .collect(Collectors.groupingBy(k -> k.osztaly, Collectors.summingInt(k -> k.mulasztottOrak)))
								   .entrySet().stream()
								   .map(k -> k.getKey() + ";" + k.getValue())
								   .collect(Collectors.joining("\n"));
		
		Files.writeString(Path.of("osszesites.csv"), hianyzasokStat, WRITE, CREATE, TRUNCATE_EXISTING);
	}
	
	static class Hianyzas{
		String nev;
		String osztaly;
		int elsoNap;
		int utolsoNap;
		int mulasztottOrak;
		
		public Hianyzas(String sor) {
			var split = sor.split(";");
			
			this.nev = split[0];
			this.osztaly = split[1];
			this.elsoNap = Integer.parseInt(split[2]);
			this.utolsoNap = Integer.parseInt(split[3]);
			this.mulasztottOrak = Integer.parseInt(split[4]);
		}
	}
}