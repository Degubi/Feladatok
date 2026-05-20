import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sebesseg {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("ut.txt"));
        var totalRoadMeters = Integer.parseInt(lines.getFirst());
        var roadData = new ArrayList<RoadData>();

        for(var line : lines.subList(1, lines.size())) {
            var split = line.split(" ");

            roadData.add(new RoadData(Integer.parseInt(split[0]), split[1]));
        }

        var citiesData = new ArrayList<CityData>();
        var currentCityName = (String) null;

        for(int i = 0, currentCityStartI = 0; i < roadData.size(); ++i) {
            var data = roadData.get(i);

            if(data.value.length() >= 4) {
                currentCityName = data.value;
                currentCityStartI = i;
            }

            if(data.value.charAt(0) == ']') {
                citiesData.add(new CityData(currentCityName, roadData.subList(currentCityStartI, i + 1)));
            }
        }

        System.out.println("2. Feladat:");

        for(var city : citiesData) {
            System.out.println(city.name);
        }

        var inputDistance = ((int) Float.parseFloat(System.console().readLine("3. Feladat: Adja meg a vizsgált szakasz hosszát km-ben! ")) * 1000);
        var lowestSpeedLimit = 90;

        for(var e : roadData) {
            lowestSpeedLimit = Math.min(lowestSpeedLimit, Integer.parseInt(e.value));

            if(e.distance >= inputDistance) {
                System.out.println("Ezen a távon belül a legalacsonyabb megendetett sebesség " + lowestSpeedLimit + "km/óra volt");
                break;
            }
        }

        var totalDistanceInCities = 0;
        for(var city : citiesData) {
            totalDistanceInCities += calculateCityDistance(city.roadData);
        }

        System.out.printf("4. Feladat: Az út %.2f százaléka vezetett településen belül\n", (float) totalDistanceInCities / totalRoadMeters * 100);

        var inputCityName = System.console().readLine("5. Feladat: Adja meg 1 település nevét! ");
        var inputCityIndex = 0;

        for(var i = 0; i < citiesData.size(); ++i) {
            if(citiesData.get(i).name.equals(inputCityName)) {
                inputCityIndex = i;
                break;
            }
        }

        var inputCityRoadData = citiesData.get(inputCityIndex).roadData;
        var speedLimitingTableCount = 0;

        for(var e : inputCityRoadData) {
            if(e.value.length() == 2) {
                ++speedLimitingTableCount;
            }
        }

        System.out.println("Sebességkorlátozások száma: " + speedLimitingTableCount + ", út teljes hossza: " + calculateCityDistance(inputCityRoadData));

        var previousCity = inputCityIndex == 0 ? null : citiesData.get(inputCityIndex - 1);
        var nextCity = inputCityIndex == (citiesData.size() - 1) ? null : citiesData.get(inputCityIndex + 1);
        var previousCityDistance = previousCity == null ? Integer.MAX_VALUE : inputCityRoadData.getFirst().distance - previousCity.roadData.getLast().distance;
        var nextCityDistance = nextCity == null ? Integer.MAX_VALUE : nextCity.roadData.getFirst().distance - inputCityRoadData.getLast().distance;
        var minDistance = Math.min(previousCityDistance, nextCityDistance);

        System.out.println("6. Feladat: A legközelebbi település: " + (minDistance == previousCityDistance ? previousCity.name : nextCity.name));
    }

    static int calculateCityDistance(List<RoadData> cityData) {
        return cityData.getLast().distance - cityData.getFirst().distance;
    }

    record RoadData(int distance, String value) {}
    record CityData(String name, List<RoadData> roadData) {}
}
