kerekparos_ertek_osszeadashoz = lambda ertek: 0 if ertek == -1 else ertek

with open('meres.txt') as meresek_input:
    meresek = [ int(k) for k in meresek_input.readline().strip().split(', ') ]

osszes_kerekparos = sum(kerekparos_ertek_osszeadashoz(k) for k in meresek)

print(f'2. Feladat: Összesen {osszes_kerekparos} kerékpárost számoltak')
print(f'3. Feladat: Óránkénti mérések:')

orankent_athaladokat_general = ((
    kerekparos_ertek_osszeadashoz(meresek[i]),
    kerekparos_ertek_osszeadashoz(meresek[i + 1]),
    kerekparos_ertek_osszeadashoz(meresek[i + 2]),
    kerekparos_ertek_osszeadashoz(meresek[i + 3]))
for i in range(0, len(meresek), 4))

orankenti_athaladok = [ sum(k) for k in orankent_athaladokat_general ]

for i in range(0, len(orankenti_athaladok)):
    print(f'{i + 6} órától {orankenti_athaladok[i]} kerékpáros')

legmagasabb_meres_i = max((i for i in range(0, len(meresek))), key = lambda i: meresek[i])
percek = 6 * 60 + 15 + (legmagasabb_meres_i * 15);

print(f'Az áthaladók maximális száma: {meresek[legmagasabb_meres_i]}, időpontja: {percek // 60}:{percek % 60}')
