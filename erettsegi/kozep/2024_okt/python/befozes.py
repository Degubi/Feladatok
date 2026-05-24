with open('uvegek.txt') as urtartalmak_input:
    urtartalmak = [ int(k) for k in urtartalmak_input.readline().strip().split(', ') ]

bekert_urtartalom = int(input('2. Feladat: Mari néni lekvárja (dl): '))
legmagasabb_urtartalom_i = max((i for i in range(0, len(urtartalmak))), key = lambda i: urtartalmak[i])

print(f'3. Feladat: A legnagyobb üveg: {urtartalmak[legmagasabb_urtartalom_i]} dl és {legmagasabb_urtartalom_i + 1} . a sorban')
print(f'4. Feladat: {"Elegendő üveg volt" if bekert_urtartalom <= sum(urtartalmak) else "Maradt lekvár"}')
