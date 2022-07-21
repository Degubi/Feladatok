def get_step_attempt_result_message(value: int, row_index: int, column_index: int, game_state: list[list[int]]):
    if game_state[row_index][column_index] != 0: return "A helyet már kitöltötték"
    if any(k for k in game_state[row_index] if k == value): return "Az adott sorban már szerepel a szám"
    if any(k for k in game_state if k[column_index] == value): return "Az adott oszlopban már szerepel a szám"

    begin_row = (row_index // 3) * 3
    end_row = begin_row + 3
    begin_column = (column_index // 3) * 3
    end_column = begin_column + 3

    for numbers_for_line in game_state[begin_row : end_row]:
        for num in numbers_for_line[begin_column : end_column]:
            if num == value:
                return "Az adott résztáblában már szerepel a szám"

    return 'A lépés megtehető'


print('1. Feladat: Írd be 1 bemeneti fájl nevét, 1 sor és 1 oszlop számát!')

input_file_name = input()
input_row_index = int(input()) - 1
input_column_index = int(input()) - 1

with open(input_file_name) as input_file:
    lines = input_file.readlines()

    parse_line = lambda split_line: [ int(n) for n in split_line ]
    numbers_per_line = [ parse_line(k.split(' ')) for k in lines ]

game_state = numbers_per_line[0 : 9]

value_at_input_slot = game_state[input_row_index][input_column_index]
table_number = 3 * (input_row_index // 3) + (input_column_index // 3) + 1
value_to_print = 'Az adott helyet még nem töltötték ki!' if value_at_input_slot == 0 else (f'Adott helyen szereplő szám: {value_at_input_slot}')

print(f'3. Feladat: {value_to_print}, résztáblázat száma: {table_number}')

unfilled_slot_count = 0
for numbers_in_line in game_state:
    for num in numbers_in_line:
        if num == 0:
            unfilled_slot_count += 1

print(f'4. Feladat: Üres helyek aránya: {(unfilled_slot_count / 81 * 100):.1f}')
print('5. Feladat:')

for numbers_in_line in numbers_per_line[9 :]:
    print(f'Sor: {numbers_in_line[1]}, oszlop: {numbers_in_line[2]}, érték: {numbers_in_line[0]}: {get_step_attempt_result_message(numbers_in_line[0], numbers_in_line[1] - 1, numbers_in_line[2] - 1, game_state)}')