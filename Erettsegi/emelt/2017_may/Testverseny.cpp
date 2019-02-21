#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

using namespace std;  //Triggering people :D

struct Versenyzo{
	string id, answers;
	int points = 0;
	Versenyzo(string arg1, string arg2) : id(arg1), answers(arg2){}
};

int main(){
	ifstream input("valaszok.txt");
	vector<Versenyzo> versenyzok;
	versenyzok.reserve(500);
	
	string megoldas, arg1, arg2;
	input >> megoldas;
	while(input >> arg1 >> arg2){
		versenyzok.emplace_back(arg1, arg2);
	}
	input.close();
	
	const unsigned int resztvevok = versenyzok.size();
	cout << "Versenyzõk száma: " << resztvevok << endl;
	
	string readID;
	cout << "Írj be 1 ID-t!" << endl;
	cin >> readID;
	for(Versenyzo &versenyzo : versenyzok){
		if(versenyzo.id == readID){
			cout << versenyzo.answers << " (Felhasználó válaszai)"<< endl; 
			cout << megoldas << " (Helyes megoldások)" << endl;
			for(int l = 0; l < 14; ++l){
				if(versenyzo.answers[l] == megoldas[l]){
					cout << '+';
				}else{
					cout << ' ';
				}
			}
			cout << endl;
		}
	}
	
	int readInt;
	cout << "Írj be 1 feladat sorszámot!" << endl;
	cin >> readInt;
	
	int joMegoldas = 0;
	for(Versenyzo &versenyzo : versenyzok){
		if(versenyzo.answers[readInt - 1] == megoldas[readInt - 1]){
			++joMegoldas;
		}
	}
	
	cout.precision(4);
	cout << "Jó megoldások száma: " << joMegoldas << ". Ez az összesnek a " << 
			(float)joMegoldas / resztvevok * 100 << "%-a." << endl;
	
	ofstream output("pontok.txt");
	for(Versenyzo& versenyzo : versenyzok){
		for(int k = 0; k < 14; ++k){
			if(versenyzo.answers[k] == megoldas[k]) {
				if(k <= 4) {
					versenyzo.points += 3;
				}else if(k >= 5 && k <= 9) {
					versenyzo.points += 4;
				}else if(k >= 10 && k <= 12) {
					versenyzo.points += 5;
				}else{
					versenyzo.points += 6;
				}
			}
		}
		output << versenyzo.id << ' ' << versenyzo.points << endl;
	}
	
	output.close();
	
	sort(begin(versenyzok), end(versenyzok), [](Versenyzo& ver1, Versenyzo& ver2) {
		return ver1.points > ver2.points;
	});
	
	cout << "7. feladat: A verseny legjobbjai:" << endl;
	for(int k = 1, index = 0; k < 4; ++k, ++index) {
		cout << k << ". díj (" << versenyzok[index].points << " pont): " << versenyzok[index].id << endl;
		if(versenyzok[index].points == versenyzok[index + 1].points) {
			cout << k << ". díj (" << versenyzok[++index].points << " pont): " << versenyzok[index].id << endl;
		}
	}
	return 0;
}