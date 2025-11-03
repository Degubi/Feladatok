import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Sebesseg_Stream {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("ut.txt"));
        var totalRoadMeters = Integer.parseInt(lines.getFirst());
        var roadData = lines.stream()
                            .skip(1)
                            .map(k -> k.split(" "))
                            .map(k -> new RoadData(Integer.parseInt(k[0]), k[1]))
                            .toArray(RoadData[]::new);

        var cityStartingOrEndingIndices = IntStream.range(0, roadData.length)
                                                   .filter(i -> roadData[i].value.length() >= 4 || roadData[i].value.charAt(0) == ']')
                                                   .toArray();

        var citiesData = IntStream.iterate(0, i -> i < cityStartingOrEndingIndices.length, i -> i + 2)
                                  .mapToObj(i -> new CityData(roadData[cityStartingOrEndingIndices[i]].value, Arrays.copyOfRange(roadData, cityStartingOrEndingIndices[i], cityStartingOrEndingIndices[i + 1] + 1)))
                                  .toArray(CityData[]::new);

        System.out.println("2. Feladat:");

        Arrays.stream(citiesData)
              .forEach(k -> System.out.println(k.name));

        var inputDistance = ((int) Float.parseFloat(System.console().readLine("3. Feladat: Adja meg a vizsgált szakasz hosszát km-ben! ")) * 1000);
        var speedLimitMin = Arrays.stream(roadData)
                                  .takeWhile(k -> k.distance <= inputDistance)
                                  .filter(k -> k.value.length() == 2)
                                  .mapToInt(k -> Integer.parseInt(k.value))
                                  .min()
                                  .orElse(90);

        System.out.println("Ezen a távon belül a legalacsonyabb megendetett sebesség " + speedLimitMin + "km/óra volt");

        var totalDistanceInCities = Arrays.stream(citiesData)
                                          .mapToInt(k -> calculateCityDistance(k.roadData))
                                          .sum();

        System.out.printf("4. Feladat: Az út %.2f százaléka vezetett településen belül\n", (float) totalDistanceInCities / totalRoadMeters * 100);

        var inputCityName = System.console().readLine("5. Feladat: Adja meg 1 település nevét! ");
        var inputCityIndex = IntStream.range(0, citiesData.length)
                                      .filter(i -> citiesData[i].name.equals(inputCityName))
                                      .findFirst()
                                      .orElseThrow();

        var inputCityRoadData = citiesData[inputCityIndex].roadData;
        var speedLimitingTableCount = Arrays.stream(inputCityRoadData)
                                            .filter(k -> k.value.length() == 2)
                                            .count();

        System.out.println("Sebességkorlátozások száma: " + speedLimitingTableCount + ", út teljes hossza: " + calculateCityDistance(inputCityRoadData));

        var previousCity = inputCityIndex == 0 ? null : citiesData[inputCityIndex - 1];
        var nextCity = inputCityIndex == (citiesData.length - 1) ? null : citiesData[inputCityIndex + 1];
        var previousCityDistance = previousCity == null ? Integer.MAX_VALUE : inputCityRoadData[0].distance - previousCity.roadData[previousCity.roadData.length - 1].distance;
        var nextCityDistance = nextCity == null ? Integer.MAX_VALUE : nextCity.roadData[0].distance - inputCityRoadData[inputCityRoadData.length - 1].distance;
        var minDistance = Math.min(previousCityDistance, nextCityDistance);

        System.out.println("6. Feladat: A legközelebbi település: " + (minDistance == previousCityDistance ? previousCity.name : nextCity.name));
    }

    static int calculateCityDistance(RoadData[] cityData) {
        return cityData[cityData.length - 1].distance - cityData[0].distance;
    }

    record RoadData(int distance, String value) {}
    record CityData(String name, RoadData[] roadData) {}
}
