import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Fuvar_stream {
	public static void main(String[] args) throws IOException {
		var fuvarok = Files.lines(Path.of("fuvar.csv"))
						   .skip(1)
						   .map(FuvarAdat::new)
						   .toArray(FuvarAdat[]::new);
		
		System.out.println("3. Feladat: Fuvarok száma: " + fuvarok.length);
		
		var szurt = Arrays.stream(fuvarok)
						  .filter(k -> k.azonosito == 6185)
						  .toArray(FuvarAdat[]::new);
		
		System.out.println("4. Feladat: " + szurt.length + " db fuvarra: " + Arrays.stream(szurt).mapToDouble(k -> k.dij).sum());
		System.out.println("5. Feladat:");
		
		Arrays.stream(fuvarok)
			  .collect(Collectors.groupingBy(k -> k.fizetesMod, Collectors.counting()))
			  .forEach((mod, db) -> System.out.println(mod + ": " + db + " db"));
		
		System.out.printf("6. Feladat: %.2f km\n", Arrays.stream(fuvarok).mapToDouble(k -> k.tavolsag).sum() * 1.6D);
		Arrays.stream(fuvarok)
			  .max(Comparator.comparingInt(k -> k.idotartam))
			  .ifPresent(k -> System.out.printf("7. Feladat: %d mp, azonosito: %d, távolság: %.2f km, díj: %.2f$\n", k.idotartam, k.azonosito, k.tavolsag, k.dij));
	
		var header = "taxi_id;indulas;idotartam;tavolsag;viteldij;borravalo;fizetes_modja\n";
		var hibasAdatok = Arrays.stream(fuvarok)
								.filter(k -> k.idotartam > 0 && k.dij > 0F && k.tavolsag == 0F)
								.sorted(Comparator.comparing(k -> k.indulas))
								.map(k -> k.azonosito + ";" + k.indulas + ";" + k.idotartam + ";" + k.tavolsag + ";" + k.dij + ";" + k.borravalo + ";" + k.fizetesMod)
								.collect(Collectors.joining("\n"));
		
		Files.writeString(Path.of("hibak.txt"), header + hibasAdatok);
	}
	
	public static class FuvarAdat{
		public final int azonosito;
		public final LocalDateTime indulas;
		public final int idotartam;
		public final float tavolsag;
		public final float dij;
		public final float borravalo;
		public final String fizetesMod;
		
		public FuvarAdat(String line) {
			var split = line.split(";");
			
			azonosito = Integer.parseInt(split[0]);
			indulas = LocalDateTime.parse(split[1].replace(' ', 'T'));
			idotartam = Integer.parseInt(split[2]);
			tavolsag = Float.parseFloat(split[3].replace(',', '.'));
			dij = Float.parseFloat(split[4].replace(',', '.'));
			borravalo = Float.parseFloat(split[5].replace(',', '.'));
			fizetesMod = split[6];
		}
	}
}