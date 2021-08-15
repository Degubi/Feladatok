using System;
using System.Collections.Generic;
using System.IO;

var excluded = File.ReadAllLines("excluded.csv");
var reserved = ReadFileToMap("reserved.csv");
var dhcp = ReadFileToMap("dhcp.csv");

foreach(var line in File.ReadAllLines("test.csv")) {
    var split = line.Split(';');

    RunTest(split[0], split[1], dhcp, excluded, reserved);
}

using var file = new StreamWriter("dhcp_kesz.csv");

foreach(var entry in dhcp) {
    file.WriteLine(entry.Key + ';' + entry.Value);
}


Dictionary<string, string> ReadFileToMap(string file) {
    var toReturn = new Dictionary<string, string>();

    foreach(var line in File.ReadAllLines(file)) {
        var split = line.Split(';');

        toReturn.Add(split[0], split[1]);
    }

    return toReturn;
}

void RunTest(string type, string address, Dictionary<string, string> dhcp, string[] excluded, Dictionary<string, string> reserved) {
    if(type == "release"){                // Address is IP
        dhcp.Remove(dhcp[address]);
    }else{                                // Address is MAC
        if(!dhcp.ContainsKey(address)) {
            var optionalReservedIP = reserved[address];
            var ipAddress = optionalReservedIP != null ? optionalReservedIP : CreateIP(address, dhcp, excluded, reserved);

            if(!dhcp.ContainsValue(ipAddress)) {
                dhcp.Add(address, ipAddress);
            }
        }
    }
}

string CreateIP(string macAddress, Dictionary<string, string> dhcp, string[] excluded, Dictionary<string, string> reserved) {
    for(var i = 100; i <= 199; ++i) {
        var ip = "192.168.10." + i;

        if(!dhcp.ContainsValue(ip) && Array.IndexOf(excluded, ip) == -1 && !reserved.ContainsValue(ip)) {
            return ip;
        }
    }

    throw new Exception("Unable to assign IP address to mac: " + macAddress);
}