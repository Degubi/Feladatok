#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std; //Triggering people here :D

struct Vasarlas{
	vector<string> cuccok;
	Vasarlas(vector<string> process){
		cuccok = process;
	}
};

int ertek(int dbSzam) {
	if(dbSzam == 1) {
		return 500;
	}else if(dbSzam == 2) {
		return 950;
	}else if(dbSzam == 3) {
		return 1350;
	}
	return 1350 + (500 * (dbSzam - 1));
}

int main(){
	vector<Vasarlas> vasarlasok;
	
	ifstream input("penztar.txt");
	vector<string> buffer;
	string temp;
	while(getline(input, temp)){
		if(temp[0] == 'F'){
			vasarlasok.emplace_back(buffer);
			buffer.clear();
		}else{
			buffer.push_back(temp);
		}
	}
	input.close();
	const int vasarlasSize = vasarlasok.size();
	
	cout << "Fizetések száma: " << vasarlasSize << endl;
	cout << "Elsõ vásárló által vásárolt dolgok száma: " << vasarlasok[0].cuccok.size() << endl;
	cout << "Elsõ vásárló által vásárolt dolgok: " << endl;
	
	for(string& print : vasarlasok[0].cuccok){
		cout << print << ", ";
	}
	cout << endl;
	int sorszam, dbszam;
	string cikknev;
	cout << "Írj be 1 sorszámot, 1 cikknevet és 1 dbszámot!" << endl;
	cin >> sorszam >> cikknev >> dbszam;
	
	int first = 0, last = 0, cikkCounter = 0;
	for(int k = 0; k < vasarlasSize; ++k){
		Vasarlas& search = vasarlasok[k];
		for(auto& strings : search.cuccok){
			if(strings == cikknev){
				++cikkCounter;
				if(first == 0){
					first = k + 1;
				}
				last = k + 1;
			}
		}
	}
	
	cout << "Elõször ekkor vettek " << cikknev << "-t: " << first << ". vásárlás" << endl;
	cout << "Utoljára ekkor vettek " << cikknev << "-t: " << last << ". vásárlás" << endl;
	cout << "Összesen ennyiszer vettek " << cikknev << "-t: " << cikkCounter << endl;
	cout << dbszam << "db termék esetén a fizetendõ: " << ertek(dbszam) << endl;
	cout << vasarlasok[1].cuccok.size() << endl;
	
	for(string& print : vasarlasok[1].cuccok){
		cout << print << endl;
	}
	
	ofstream output("osszeg.txt");
	for(int k = 0; k < vasarlasSize; ++k){
		Vasarlas& vasarlas = vasarlasok[k];
		output << (k + 1) << ": " << ertek(vasarlas.cuccok.size()) << endl;
	}
	output.close();
	return 0;
}