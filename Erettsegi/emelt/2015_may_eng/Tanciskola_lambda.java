import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Tanciskola_lambda {
	
	public static void main(String... args) throws IOException {
		var file = Files.readAllLines(Paths.get("tancrend.txt"));
		var tancok = IntStream.iterate(0, k -> k + 3)
							  .limit(file.size() / 3)
							  .mapToObj(k -> new Tanc(file.get(k), file.get(k + 1), file.get(k + 2)))
							  .toArray(Tanc[]::new);
		
		System.out.println("Els� t�nc neve: " + tancok[0].category + ", az utols��: " + tancok[tancok.length - 1].category);
		
		System.out.println("�sszesen " + Stream.of(tancok)
										       .filter(k -> k.category.equals("samba"))
										       .count() + "-an szamb�sztak");
		
		System.out.println("Vilma �ltal t�ncolt kateg�ri�k: " + Stream.of(tancok)
							.filter(k -> k.woman.equals("Vilma"))
							.map(k -> k.category)
							.distinct()
							.collect(Collectors.toList()));
		
		System.out.println("�rj be 1 kateg�ri�t!");
		try(Scanner input = new Scanner(System.in)){
			String readCat = input.nextLine();
		
			Stream.of(tancok)
				  .filter(k -> k.woman.equals("Vilma"))
				  .filter(k -> k.category.equals(readCat))
				  .findFirst()
				  .ifPresentOrElse(k -> System.out.println("Vilma vele t�ncolt " + readCat + "-t: " + k.man + "-val"),
						  () -> System.out.println("Vilma senkivel sem t�ncolt " + readCat + "-t"));
		}
		
		var lanyok = Stream.of(tancok)
						   .map(k -> k.woman)
						   .distinct()
						   .map(name -> new Szereplo(name, tancok, a -> a.woman.equals(name)))
						   .sorted(Szereplo.byAlkalmak)
						   .toArray(Szereplo[]::new);
		
		var fiuk = Stream.of(tancok)
				   		 .map(k -> k.man)
				   		 .distinct()
				   		 .map(name -> new Szereplo(name, tancok, a -> a.man.equals(name)))
				   		 .sorted(Szereplo.byAlkalmak)
				   		 .toArray(Szereplo[]::new);
		
		try(PrintWriter output = new PrintWriter("szereplok.txt")){
			output.print("L�nyok: ");
			output.print(Stream.of(lanyok)
							   .map(k -> k.name)
							   .collect(Collectors.joining(", ")));
			
			output.print("\nFi�k: ");
			output.print(Stream.of(fiuk)
							   .map(k -> k.name)
							   .collect(Collectors.joining(", ")));
		}
		System.out.print("A legt�bbet t�ncolt l�nyok: " + Stream.of(lanyok)
					  			  .filter(k -> k.alkalmak == lanyok[0].alkalmak)
					  			  .map(k -> k.name)
					  			  .collect(Collectors.joining(" ")));
		
		System.out.println();
		System.out.print("A legt�bbet t�ncolt fi�k: " + Stream.of(fiuk)
								  .filter(k -> k.alkalmak == fiuk[0].alkalmak)
							      .map(k -> k.name)
								  .collect(Collectors.joining(" ")));
	}
	static class Tanc{
		String category, woman, man;
		
		public Tanc(String cat, String wom, String ma) {
			category = cat;
			woman = wom;
			man = ma;
		}
	}
	
	static class Szereplo{
		static final Comparator<Szereplo> byAlkalmak = Comparator.<Szereplo>comparingInt(k -> k.alkalmak).reversed();
		String name;
		int alkalmak;
		
		public Szereplo(String neim, Tanc[] tancok, Predicate<Tanc> filter) {
			name = neim;
			alkalmak = Stream.of(tancok).filter(filter).mapToInt(k -> 1).sum();
		}
	}
}