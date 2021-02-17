from datetime import date, time, timedelta
from itertools import groupby

class Sorozat:
    def __init__(self, lines, index):
        datumStr = lines[index].strip()
        epizodInfoSplit = lines[index + 2].split('x')

        self.adasbaKerulesiDatum = date.min if datumStr == 'NI' else date.fromisoformat(datumStr.replace('.', '-'))
        self.cim = lines[index + 1].strip()
        self.evadokSzama = int(epizodInfoSplit[0])
        self.epizodokSzama = int(epizodInfoSplit[1])
        self.epizodonkentiHossz = int(lines[index + 3])
        self.lattaEMarAKeszito = int(lines[index + 4]) == 1

napok = [ "v", "h", "k", "sz", "cs", "p", "szo" ]
honapok = [ 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 ]

def hetnapja(ev, ho, nap):
    ev = ev - 1 if ho < 3 else ev

    return napok[(ev + ev // 4 - ev // 100 + ev // 400 + honapok[ho - 1] + nap) % 7]


with open('lista.txt', 'r') as file:
    lines = file.readlines()
    sorozatok = [ Sorozat(lines, i) for i in range(0, len(lines), 5) ]

ismertDatumuakSzama = sum(1 for k in sorozatok if k.adasbaKerulesiDatum != date.min)
keszitoAltalLatottakSzama = sum(1 for k in sorozatok if k.lattaEMarAKeszito)

print(f'2. Feladat: {ismertDatumuakSzama} db ismert dátumú epizód van')
print('3. Feladat: Látottak százaléka: %.2f%%' % (keszitoAltalLatottakSzama / len(sorozatok) * 100))

osszesElpazaroltPerc = sum(k.epizodonkentiHossz for k in sorozatok if k.lattaEMarAKeszito)
elpazaroltIdo = timedelta(minutes = osszesElpazaroltPerc)
orak, maradek = divmod(elpazaroltIdo.seconds, 3600)
percek, _ = divmod(maradek, 60)

print(f'4. Feladat: Eltöltött idő: {elpazaroltIdo.days} nap, {orak} óra és {percek} perc')

bekertDatumStr = input('5. Feladat: Írj be 1 dátumot! (éééé.hh.nn) ')
bekertDatum = date.fromisoformat(bekertDatumStr.replace('.', '-'))

for sorozat in sorozatok:
    if sorozat.adasbaKerulesiDatum != date.min and sorozat.adasbaKerulesiDatum <= bekertDatum and not sorozat.lattaEMarAKeszito:
        print(f'{sorozat.evadokSzama}x{sorozat.epizodokSzama}\t{sorozat.cim}')

bekertNap = input('7. Feladat: Add meg 1 hét napját! (h, k, sze, cs, p, szo, v)' )
bekertNapraEsok = set(k.cim for k in sorozatok if k.adasbaKerulesiDatum != date.min and bekertNap == hetnapja(k.adasbaKerulesiDatum.year, k.adasbaKerulesiDatum.month, k.adasbaKerulesiDatum.day))

print('Az adott napon nem kerül adásba sorozat.' if len(bekertNapraEsok) == 0 else '\n'.join(bekertNapraEsok))

cimenkentiStatolo = lambda sorozatokCimhez: ( sum(k.epizodonkentiHossz for k in sorozatokCimhez), len(sorozatokCimhez) )
stat = dict((cim, cimenkentiStatolo(list(sorozatokCimhez))) for cim, sorozatokCimhez in groupby(sorozatok, key = lambda k: k.cim))

with open('summa.txt', 'w') as output:
    for cim, stat in stat.items():
        output.write(f'{cim} {stat[0]} {stat[1]}\n')