from itertools import groupby

class Hianyzas:

    def __init__(self, line):
        split = line.split(';')

        self.nev = split[0]
        self.osztaly = split[1]
        self.elsoNap = int(split[2])
        self.utolsoNap = int(split[3])
        self.mulasztottOrak = int(split[4])

with open('szeptember.csv', encoding = "utf-8") as file:
    lines = file.readlines()
    hianyzasok = [ Hianyzas(lines[i]) for i in range(1, len(lines)) ]

totalHianyzottOrak = sum(k.mulasztottOrak for k in hianyzasok)

print(f'2. Feladat: Hianyzott orak: {totalHianyzottOrak}')

bekertNap = int(input('3. Feladat: Írj be egy napot(1-30)!'))
bekertNev = input('Írj be 1 nevet!')
bekertHianyzott = any(k for k in hianyzasok if k.nev == bekertNev)

print(f'4. Feladat: {bekertNev} {"hiányzott" if bekertHianyzott else "nem hiányzott"}')
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