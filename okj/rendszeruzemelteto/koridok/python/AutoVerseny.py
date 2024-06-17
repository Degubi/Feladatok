from dataclasses import dataclass
from datetime import time

@dataclass
class Verseny:
    csapat: str
    versenyzo: str
    eletkor: int
    palya: str
    korido: time
    kor: int


with open('autoverseny.csv', encoding = 'utf-8') as input_file:
    create_verseny = lambda split: Verseny(split[0], split[1], int(split[2]), split[3], time.fromisoformat(split[4]), int(split[5]))
    versenyek = [ create_verseny(k.split(';')) for k in input_file.readlines()[1:] ]

print(f'3. Feladat: Adatsorok száma: {len(versenyek)}')

kivalasztott = next(k for k in versenyek if k.versenyzo == 'Fürge Ferenc' and k.palya == 'Gran Prix Circuit' and k.kor == 3).korido
print(f'4. Feladat: {(kivalasztott.hour * 60 + kivalasztott.minute) * 60 + kivalasztott.second} mp')

be_nev = input('5. Felatad: Írj be egy nevet: ')
be_versenyzo_korido = min((k.korido for k in versenyek if k.versenyzo == be_nev), default = None)

print(f'6. Feladat: {be_versenyzo_korido}' if be_versenyzo_korido != None else '6. Feladat: Nincs ilyen versenyző')