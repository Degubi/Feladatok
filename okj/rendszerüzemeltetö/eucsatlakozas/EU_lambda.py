# -*- coding: UTF-8 -*-
import dateutil.parser as dateparser

def csatlakozassa(line):
    split = line.split(";")
    return (split[0], dateparser.parse(split[1]))

def findvalue(testfunction, data):
    for key, value in data.items():
        if(testfunction(key, value)):
            return value

def countif(testfunction, data):
    count = 0;
    for key, value in data.items():
        if(testfunction(key, value)):
            count = count + 1;
    
    return count

def groupbycount(extractor, data):
    returndata = {}
    
    for key, value in data.items():
        extracted = extractor(key, value)
        
        if extracted in returndata:
            returndata[extracted] = returndata[extracted] + 1
        else:
            returndata[extracted] = 1
    
    return returndata


with open("EUcsatlakozas.txt") as file: lines = file.read().splitlines()
csatlakozasok = dict(map(csatlakozassa, lines))

print(f"3. Feladat: 2018-ig EU államok száma: {len(csatlakozasok)}");

ketezerhetben = countif(lambda orszag, datum: datum.year == 2007, csatlakozasok)
print(f"4. Feladat: 2007-ben csatlakozott országok száma: {ketezerhetben}")

magyarorszag = findvalue(lambda orszag, datum: orszag == "Magyarország", csatlakozasok)
print(f"5. Feladat: Magyarország csatlakozása: {magyarorszag.date()}")

if countif(lambda orszag, datum: datum.month == 5, csatlakozasok) > 0:
    print("6. Feladat: Volt májusban csatlakozás")
else:
    print("6. Feladat: Nem volt májusban csatlakozás")

sorbarendezett = sorted(csatlakozasok.items(), key = lambda kv: kv[1])
utolso = sorbarendezett[-1][0]
print(f"7. Feladat: Utoljára csatlakozott: {utolso}")

evcsoportositas = groupbycount(lambda orszag, datum: datum.year, csatlakozasok)
print("8. Feladat:")
for ev, db in evcsoportositas.items(): print(f"{ev} - {db} ország")