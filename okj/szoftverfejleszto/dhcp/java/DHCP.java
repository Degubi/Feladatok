import java.io.*;
import java.nio.file.*;
import java.util.*;

public final class DHCP {
    
    public static void main(String[] args) throws IOException {
        var excluded = Files.readAllLines(Path.of("excluded.csv"));
        var reserved = readFileToMap(Path.of("reserved.csv"));
        var dhcp = readFileToMap(Path.of("dhcp.csv"));
        
        for(var line : Files.readAllLines(Path.of("test.csv"))) {
            var split = line.split(";");
            
            runTest(split[0], split[1], dhcp, excluded, reserved);
        }
        
        try(var file = new PrintWriter("dhcp_kesz.csv")) {
            for(var entry : dhcp.entrySet()) {
                file.println(entry.getKey() + ';' + entry.getValue());
            }
        }
    }
    
    public static Map<String, String> readFileToMap(Path file) throws IOException {
        var toReturn = new HashMap<String, String>();
        
        for(var line : Files.readAllLines(file)) {
            var split = line.split(";");
            
            toReturn.put(split[0], split[1]);
        }
        
        return toReturn;
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
        for(var i = 100; i <= 199; ++i) {
            var ip = "192.168.10." + i;
            
            if(!dhcp.containsValue(ip) && !excluded.contains(ip) && !reserved.containsValue(ip)) {
                return ip;
            }
        }
        
        throw new IllegalStateException("Unable to assign IP address to mac: " + macAddress);
    }
}