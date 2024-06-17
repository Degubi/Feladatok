from inspect import cleandoc

def domain(szint: int, domain: str):
    split = domain.split('.')
    utolsoIndex = len(split) - 1

    return 'nincs' if utolsoIndex < szint else split[utolsoIndex - szint]


with open('csudh.txt') as file:
    pairs = [ k.split(';') for k in file.readlines()[1:] ]

print(f'3. Feladat: Párok száma: {str(len(pairs))}')
print('5. Feladat:')

elsoDomain = pairs[0][0]
for i in range(0, 5):
    print(f'{i + 1}. szint: {domain(i, elsoDomain)}')

header = '''<table>
            <tr>
            <th style='text-align: left'>Sorszam</th>
            <th style='text-align: left'>Host domain neve</th>
            <th style='text-align: left'>Host IP címe</th>
            <th style='text-align: left'>1. szint</th>
            <th style='text-align: left'>2. szint</th>
            <th style='text-align: left'>3. szint</th>
            <th style='text-align: left'>4. szint</th>
            <th style='text-align: left'>5. szint</th>
            </tr>'''

with open('table.html', 'w+') as table:
    table.write(cleandoc(header))

    for sorszam in range(0, len(pairs)):
        jelendomain, jelenip = pairs[sorszam]

        table.write('<tr>')
        table.write(f"<th style='text-align: left'>{sorszam + 1}.</th>")
        table.write(f'<td>{jelendomain}</td>')
        table.write(f'<td>{jelenip}</td>')

        for k in range(0, 5):
            table.write(f'<td>{domain(k, jelendomain)}</td>')

        table.write('</tr>')

    table.write('</table>')