with open('tomeg.txt') as tomegek_input:
    tomegek = [ int(k) for k in tomegek_input.readline().strip().split(', ') ]

ossztomeg = sum(tomegek)

print(f'2. Feladat: A tárgyak tömegének összege: {ossztomeg}kg')

dobozok = [ 0 ]
for tomeg in tomegek:
    utolso_doboz = dobozok[-1]
    uj_tomeg = utolso_doboz + tomeg

    if uj_tomeg <= 20:
        dobozok[-1] = uj_tomeg
    else:
        dobozok.append(tomeg)

kiirni_lista = ' '.join(str(k) for k in dobozok)

print(f'3. Feladat: A dobozok tartalmának tömege (kg): {kiirni_lista} \nSzükséges dobozok száma: {len(dobozok)}')
