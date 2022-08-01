use std::fs::File;
use std::io::{ BufReader, BufRead, stdin };

fn get_step_attempt_result_message(value: i32, row_index: usize, column_index: usize, game_state: &[Vec<i32>]) -> &str {
    if game_state[row_index][column_index] != 0 { return "A helyet már kitöltötték"; }
    if game_state[row_index].iter().any(|k| *k == value) { return "Az adott sorban már szerepel a szám"; }
    if game_state.iter().any(|k| k[column_index] == value) { return "Az adott oszlopban már szerepel a szám"; }

    let begin_row = (row_index / 3) * 3;
    let end_row = begin_row + 3;
    let begin_column = (column_index / 3) * 3;
    let end_column = begin_column + 3;

    if game_state[begin_row .. end_row].iter().any(|k| k[begin_column .. end_column].iter().any(|m| *m == value)) {
        return "Az adott résztáblában már szerepel a szám";
    }

    return "A lépés megtehető";
}

fn main() {
    println!("1. Feladat: Írd be 1 meneti fájl nevét, 1 sor és 1 oszlop számát!");

    let mut input_file_name_str = String::new();
    let mut input_row_index_str = String::new();
    let mut input_column_index_str = String::new();
    stdin().read_line(&mut input_file_name_str);
    stdin().read_line(&mut input_row_index_str);
    stdin().read_line(&mut input_column_index_str);

    let input_row_index = input_row_index_str.trim_end().parse::<usize>().unwrap() - 1;
    let input_column_index = input_column_index_str.trim_end().parse::<usize>().unwrap() - 1;

    let numbers_per_line = BufReader::new(File::open(input_file_name_str.trim_end()).unwrap())
                                    .lines()
                                    .map(|k| k.unwrap().split(' ').map(|n| n.parse::<i32>().unwrap()).collect::<Vec<i32>>())
                                    .collect::<Vec<Vec<i32>>>();

    let game_state = &numbers_per_line[0 .. 9];
    let value_at_input_slot = game_state[input_row_index][input_column_index];
    let table_number = 3 * (input_row_index / 3) + (input_column_index / 3) + 1;

    println!("3. Feladat: {}, résztáblázat száma: {}", if value_at_input_slot == 0 { "Az adott helyet még nem töltötték ki!".to_string() }
                                                       else { format!("Adott helyen szereplő szám: {}", value_at_input_slot) }, table_number);
    let unfilled_slot_count = game_state.iter()
                                        .flat_map(|k| k)
                                        .filter(|k| **k == 0)
                                        .count();

    println!("4. Feladat: Üres helyek aránya: {:.1}", unfilled_slot_count as f32 / 81.0 * 100.0);
    println!("5. Feladat:");

    numbers_per_line[9 .. numbers_per_line.len()].iter()
                                                 .for_each(|k| println!("Sor: {}, oszlop: {}, érték: {}: {}", k[1], k[2], k[0],
                                                                        get_step_attempt_result_message(k[0], k[1] as usize - 1, k[2] as usize - 1, game_state)));
}