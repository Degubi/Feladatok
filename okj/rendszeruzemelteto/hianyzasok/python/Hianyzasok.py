from dataclasses import dataclass
from itertools import groupby

@dataclass
class Hianyzas:
    nev: str
    osztaly: str
    elsoNap: int
    utolsoNap: int
    mulasztottOrak: int


with open('szeptember.csv', encoding = 'utf-8') as file:
    create_hianyzas = lambda split: Hianyzas(split[0], split[1], int(split[2], int(split[3], int(split[4]))))
    hianyzasok = [ create_hianyzas(k.split(';')) for k in file.readlines()[1:] ]

totalHianyzottOrak = sum(k.mulasztottOrak for k in hianyzasok)

print(f'2. Feladat: Hianyzott orak: {totalHianyzottOrak}')

bekertNap = int(input('3. Feladat: Írj be egy napot(1-30)!'))
bekertNev = input('Írj be 1 nevet!')
bekert_hianyzott_e = any(True for k in hianyzasok if k.nev == bekertNev)

print(f'4. Feladat: {bekertNev} {"hiányzott" if bekert_hianyzott_e else "nem hiányzott"}')
print('5. Feladat')

azonANaponHianyoztak = [ k for k in hianyzasok if bekertNap >= k.elsoNap and bekertNap <= k.utolsoNap ]
if len(azonANaponHianyoztak) == 0:
    print('Nem volt hiányzó')
else:
    for hiany in azonANaponHianyoztak:
        print(f'{hiany.nev} {hiany.osztaly}')

hianyzasok.sort(key = lambda k: k.osztaly)

orakatSzamol = lambda m: sum(k.mulasztottOrak for k in m)
hianyzasokStat = dict((k, orakatSzamol(v)) for k, v in groupby(hianyzasok, key = lambda m: m.osztaly))

with open('osszesites.csv', 'w') as outFile:
    for osztaly, totalMulasztottOrak in hianyzasokStat.items():
        outFile.write(f'{osztaly};{totalMulasztottOrak}\n')