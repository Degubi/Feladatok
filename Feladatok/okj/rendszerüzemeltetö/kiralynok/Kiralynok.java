import static java.nio.file.StandardOpenOption.*;

import java.io.*;
import java.nio.file.*;

public class Kiralynok {

	public static void main(String[] args) throws IOException {
		var tabla = new Tabla('#');
		
		System.out.println("4. Feladat: Az üres tábla");
		tabla.megjelenit(System.out);
		
		System.out.println("6. Feladat: A feltöltött tábla");
		tabla.elhelyez(8);
		tabla.megjelenit(System.out);
		
		System.out.println("9. Feladat: Üres sorok és oszlopok száma");
		System.out.println("Oszlopok: " + tabla.uresOszlopokSzama());
		System.out.println("Sorok: " + tabla.uresSorokSzama());
		
		try(var file = Files.newOutputStream(Path.of("tablak64.txt"), WRITE, CREATE, TRUNCATE_EXISTING)){
			for(var i = 1; i < 65; ++i) {
				var fileTabla = new Tabla('*');
				fileTabla.elhelyez(i);
				
				fileTabla.megjelenit(file);
				file.write('\n');
			}
		}
	}
}