import module java.base;

public class Szallitas_stream {

    public static void main(String[] args) throws Exception {
        var tomegek = Arrays.stream(Files.readString(Path.of("tomeg.txt")).trim().split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        var ossztomeg = Arrays.stream(tomegek).sum();

        System.out.println("2. Feladat: A tárgyak tömegének összege: " + ossztomeg + "kg");

        var dobozok = Arrays.stream(tomegek).collect(() -> new ArrayList<>(List.of(0)), (dobozokResult, tomeg) -> {
            var utolsoDoboz = dobozokResult.getLast();
            var ujTomeg = utolsoDoboz + tomeg;

            if(ujTomeg <= 20) {
                dobozokResult.set(dobozokResult.size() - 1, ujTomeg);
            }else{
                dobozokResult.add(tomeg);
            }
        }, ArrayList::addAll);

        var kiirniLista = dobozok.stream()
                                 .map(k -> k.toString())
                                 .collect(Collectors.joining(" "));

        System.out.println("3. Feladat: A dobozok tartalmának tömege (kg): " + kiirniLista + "\nSzükséges dobozok száma: " + dobozok.size());
    }
}