import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public final class DHCP_stream {
    
    public static void main(String[] args) throws IOException {
        var excluded = Files.readAllLines(Path.of("excluded.csv"));
        var reserved = readFileToMap(Path.of("reserved.csv"));
        var dhcp = readFileToMap(Path.of("dhcp.csv"));
        
        Files.lines(Path.of("test.csv"))
             .map(k -> k.split(";"))
             .forEach(k -> runTest(k[0], k[1], dhcp, excluded, reserved));
        
        var fileba = dhcp.entrySet().stream()
                         .map(e -> e.getKey() + ';' + e.getValue())
                         .collect(Collectors.toList());
        
        Files.write(Path.of("dhcp_kesz.csv"), fileba);
    }
    
    public static Map<String, String> readFileToMap(Path file) throws IOException {
        return Files.lines(file)
                    .map(k -> k.split(";"))
                    .collect(Collectors.toMap(k -> k[0], k -> k[1]));
    }
    
    public static void runTest(String type, String address, Map<String, String> dhcp, List<String> excluded, Map<String, String> reserved) {
        if(type.equals("release")){           // Address is IP
            dhcp.values().remove(address);
        }else{                                // Address is MAC
            if(!dhcp.containsKey(address)) {
                var optionalReservedIP = reserved.get(address);
                var ipAddress = optionalReservedIP != null ? optionalReservedIP : createIP(address, dhcp, excluded, reserved);
                
                if(!dhcp.containsValue(ipAddress)) {
                    dhcp.put(address, ipAddress);
                }
            }
        }
    }
    
    public static String createIP(String macAddress, Map<String, String> dhcp, List<String> excluded, Map<String, String> reserved) {
        return IntStream.rangeClosed(100, 199)
                        .mapToObj(i -> "192.168.10." + i)
                        .filter(k -> !dhcp.containsValue(k))
                        .filter(k -> !excluded.contains(k))
                        .filter(k -> !reserved.containsValue(k))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Unable to assign IP address to mac: " + macAddress));
    }
}