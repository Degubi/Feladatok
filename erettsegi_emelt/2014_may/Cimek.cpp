#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;

int main(){
    ifstream input("ip.txt");
    vector<string> ipk;
    
    string temp;
    while(input >> temp){
        ipk.push_back(temp);
    }
    input.close();
    const unsigned int ipCount = ipk.size();
    
    cout << "Adatsorok száma " << ipCount << endl;
    
    sort(ipk.begin(), ipk.end(), [](string& s1, string& s2){
        return s1 < s2;
    });
    
    cout << "Legkisebb ip: " << ipk[0] << endl;
    
    int counters[] {0, 0, 0};
    for(string& ip : ipk){
        if(ip.compare(0, 9, "2001:0db8") == 0){
            ++counters[0];
        }else if(ip.compare(0, 7, "2001:0e") == 0){
            ++counters[1];
        }else{
            ++counters[2];
        }
    }
    cout << "Dokumentációs címek: " << counters[0] << ", globális címek: " << counters[1] << ", helyi egyedi: " << counters[2] << endl;
    
    ofstream output("sok.txt");
    for(unsigned int k = 0; k < ipCount; ++k){
        int zeroCounter = 0;
        for(char& kar : ipk[k]){
            if(kar == '0'){
                ++zeroCounter;
            }
        }
        if(zeroCounter > 17){
            output << (k + 1) << ' ' << ipk[k] << endl;
        }
    }
    
    int readInt;
    cout << "Írj be 1 sorszámot!" << endl;
    cin >> readInt;
    
    //TODO formázós részt nemtom :/
    
    output.close();
    return 0;
}