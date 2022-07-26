from itertools import groupby

class Verseny:

    def __init__(self, line: str):
        split = line.split('\t')

        self.ev = int(split[0])
        self.indulasokSzama = int(split[1])
        self.nyeresekSzama = int(split[2])
        self.dobogosokSzama = int(split[3])
        self.elsoHelyrolIndulasokSzama = int(split[4])
        self.leggyorsabbKorokSzama = int(split[5])

with open('jackie.txt') as file:
    file.readline()

    versenyek = [ Verseny(k) for k in file.readlines() ]

print(f'3. Feladat: Adatsorok száma: {len(versenyek)}')

legtobb_indulasos_verseny = max(versenyek, key = lambda k: k.indulasokSzama)

print(f'4. Feladat: {legtobb_indulasos_verseny.ev}')
print('5. Feladat:')

versenyek.sort(key = lambda k: k.ev)

nyereseket_szamol = lambda m: sum(k.nyeresekSzama for k in m)
evtizedes_stat = dict((ev // 10 * 10 % 100, nyereseket_szamol(v)) for ev, v in groupby(versenyek, key = lambda m: m.ev))

for evtized, nyeresek_szama in evtizedes_stat.items():
    print(f'    {evtized}-es évek: {nyeresek_szama} db nyerés')


htmlHeader = """<!DOCTYPE html>
                <html>
                    <head>
                        <style>
                            td {
                                border: 1px solid black;
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Jackie Stewart</h1>
                        <table>
                        """

htmlFooter = """            </table>
                        </body>
                    </html>"""

with open('jackie.html', mode = 'w') as output:
    output.write(htmlHeader)

    for verseny in versenyek:
        output.write(f'{" " * 12}<tr><td>{verseny.ev}</td><td>{verseny.indulasokSzama}</td><td>{verseny.nyeresekSzama}</td></tr>\n')

    output.write(htmlFooter)