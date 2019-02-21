#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

struct Feladat{
	string question, category;
	int point, answer;
	Feladat(string quest, int ans, int poi, string cat){
		question = quest; answer = ans; point = poi; category = cat;
	}
};

int main(){
	vector<Feladat> feladatok;
	ifstream input("felszam.txt");
	string currentLine, cat;
	int ev, point;
	while(getline(input, currentLine)){
		input >> ev >> point >> cat;
		feladatok.emplace_back(currentLine, ev, point, cat);
		getline(input, currentLine);
	}
	input.close();
	
	cout << "Feladatok száma: " << feladatok.size() << endl;
	
	//[0]: matek, [1]: 1 pont, [2]: 2 pont, [3]: 3 pont, [4]: min, [5]: max
	int counters[] {0, 0, 0, 0, feladatok[0].answer, feladatok[0].answer};
	set<string> categories;
	
	for(Feladat &feladat : feladatok){
		categories.insert(feladat.category);
		if(feladat.point < counters[4]){
			counters[4] = feladat.point;
		}
		if(feladat.point > counters[5]){
			counters[5] = feladat.point;
		}
		if(feladat.category == "matematika"){
			++counters[0];
			if(feladat.point == 1){
				++counters[1];
			}else if(feladat.point == 2){
				++counters[2];
			}else{
				++counters[3];
			}
		}
	}
	
	cout << "Matek feladatok száma: " << counters[0] << ", 1 pontos feladatok: " << counters[1]
		 << ", 2 pontos feladatok: " << counters[2] << ", 3 pontosak: " << counters[3] << endl;
	
	cout << "Legkisebb feladat megoldás: " << counters[4] << ", legnagyobb: " << counters[5] << endl;
	
	for(string print : categories){
		cout << print << ' ';
	}
	cout << endl;
	
	string temakor;
	int valasz;
	cout << "Írj be 1 témakört!" << endl;
	cin >> temakor;
	
	Feladat fel = feladatok[rand() % feladatok.size()];
	for(; fel.category != temakor; fel = feladatok[rand() % feladatok.size()]);
	
	cout << fel.question << endl;
	cin >> valasz;
	
	if(valasz == fel.answer){
		cout << "Ügyi vagy! Itt 1 keksz és " << fel.answer << " pont!" << endl; 
	}else{
		cout << "Rossz válasz! 0 pont, helyes válasz: " << fel.answer << endl;
	}
	
	set<int> generalt;
	for(int r = rand() % feladatok.size(); generalt.size() != 10; r = rand() % feladatok.size()){
		generalt.insert(r);
	}
	
	ofstream output("tesztfel.txt");
	for(int kek : generalt){
		Feladat print = feladatok[kek];
		output << print.point << ' ' << print.answer << ' ' << print.question << endl;
	}
	output.close();
	return 0;
}