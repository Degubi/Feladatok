import java.io.*;
import java.util.*;

public class Szavak {

    public static void main(String[] args) throws IOException{
        var bekertSzo = System.console().readLine("Írj be 1 szót: ").toLowerCase();
        if(bekertSzo.contains("a") || bekertSzo.contains("e") || bekertSzo.contains("i") || bekertSzo.contains("o") || bekertSzo.contains("u")) {
            System.out.println("Van benne magánhangzó");
        }else{
            System.out.println("Nincs benne magánhangzó");
        }

        var otbetus = new ArrayList<String>();
        var longest = "";
        var wordCounter = 0;
        var lineCounter = 0;

        try(var input = new BufferedReader(new FileReader("szoveg.txt"))) {
            for(var line = input.readLine(); line != null; line = input.readLine(), ++lineCounter) {
                var lineLenght = line.length();
                if(lineLenght > longest.length()) {
                    longest = line;
                }

                if(lineLenght == 5) {
                    otbetus.add(line);
                }

                var mghCounter = 0;
                for(var i = 0; i < lineLenght; ++i) {
                    var betu = line.charAt(i);
                    if(betu == 'a' || betu == 'e' || betu == 'i' || betu == 'o' || betu == 'u') {
                        ++mghCounter;
                    }
                }

                if(lineLenght / 2 < mghCounter) {
                    ++wordCounter;
                    System.out.print(line + " ");
                }
            }
        }

        System.out.println();
        System.out.println("A leghoszabb szó: " + longest + ", hossza: " + longest.length());
        System.out.println("Mgh-s szavak száma: " + wordCounter);
        System.out.println("Összes szó: " + lineCounter);
        System.out.printf(wordCounter + "/" + lineCounter + ", ez százalékban %.2f\n", (float) wordCounter / lineCounter * 100);

        var bekertSzoreszlet = System.console().readLine("Írj be 1 szórészletet: ");
        for(var check : otbetus) {
            if(check.contains(bekertSzoreszlet)) {
                System.out.print(check + " ");
            }
        }

        System.out.println();

        try(var output = new PrintWriter("letra.txt")) {
            var used = new ArrayList<String>();
            var buffer = new ArrayList<String>();

            for(var check : otbetus) {
                var threeLetters = check.substring(1, 4);

                if(!used.contains(threeLetters)) {
                    used.add(threeLetters);

                    for(var sajt : otbetus) {
                        if(sajt.regionMatches(1, threeLetters, 0, 3)) {
                            buffer.add(sajt);
                        }
                    }

                    if(buffer.size() > 1) {
                        for(var print : buffer) {
                            output.println(print);
                        }
                        output.println();
                    }
                    buffer.clear();
                }
            }
        }
    }
}