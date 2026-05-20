from datetime import time
from math import ceil
from itertools import groupby
from statistics import mean

class IdojarasAdat:

    def __init__(self, line: str):
        split = line.split(' ')
        teljesIdo = int(split[1])

        self.telepules = split[0]
        self.ido = time(teljesIdo // 100, teljesIdo % 100)
        self.szelIrany = split[2][0:3]
        self.szelErosseg = int(split[2][3:])
        self.homerseklet = int(split[3])

with open('tavirathu13.txt') as file:
    lines = file.readlines()
    adatok = [ IdojarasAdat(k) for k in lines ]

bekertKod = input('2. Feladat: Írj be egy városkódot! ')
*_, utolsoMeres = (k for k in adatok if k.telepules == bekertKod)

print(f'Utolsó mérés időpontja: {utolsoMeres.ido}')
print('3. Feladat:')

legalacsonyabbAdat, *_, legmagasabbAdat = sorted(adatok, key = lambda k: k.homerseklet)

print(f'Legalacsonyabb hőmérséklet: {legalacsonyabbAdat.telepules} {legalacsonyabbAdat.ido} {legalacsonyabbAdat.homerseklet} fok')
print(f'Legmagasabb hőmérséklet: {legmagasabbAdat.telepules} {legmagasabbAdat.ido} {legmagasabbAdat.homerseklet} fok')
print('4. Feladat:')

szelcsendek = [ k for k in adatok if k.szelIrany == '000' and k.szelErosseg == 0 ]

if len(szelcsendek) == 0:
    print('Nem volt szélcsend a mérések idején.')
else:
    for k in szelcsendek:
        print(f'{k.telepules}: {k.ido}')

print('5. Feladat:')

adatok.sort(key = lambda k: k.telepules)
adatokTelepulesenkent = dict((k, list(v)) for k, v in groupby(adatok, key = lambda m: m.telepules))

for telepules, telepulesAdatai in adatokTelepulesenkent.items():
    homersekletAlapjanRendezett = sorted(telepulesAdatai, key = lambda k: k.homerseklet)

    ingadozas = homersekletAlapjanRendezett[-1].homerseklet - homersekletAlapjanRendezett[0].homerseklet
    orakAmikhezVanAdat = set(k.ido.hour for k in telepulesAdatai)

    if len(orakAmikhezVanAdat) == 24:
        atlagHomerseklet = mean(k.homerseklet for k in telepulesAdatai)
        kerekitettKozep = ceil(atlagHomerseklet)

        print(f'{telepules}: Középhőmérséklet: {kerekitettKozep}; Ingadozás: {ingadozas}')
    else:
        print(f'{telepules}: NA; Ingadozás: {ingadozas}')

    adatSorok = '\n'.join(f'{k.ido} {"#" * k.szelErosseg}' for k in telepulesAdatai)

    with open(f'{telepules}.txt', 'w') as outFile:
        outFile.write(telepules + '\n' + adatSorok)