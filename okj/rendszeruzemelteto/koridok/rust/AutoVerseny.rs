use std::{ fs::File, io::BufReader, io::{ BufRead, stdin } };
use chrono::{ NaiveTime, Timelike };
use itertools::Itertools;

fn main() {
    let input_file = File::open("autoverseny.csv").unwrap();
    let versenyek = BufReader::new(input_file)
                             .lines()
                             .skip(1)
                             .map(|k| create_verseny(&k.unwrap()))
                             .collect::<Vec<_>>();

    println!("3. Feladat: Adatsorok száma: {}", versenyek.len());

    match versenyek.iter().find(|k| k.versenyzo == "Fürge Ferenc" && k.palya == "Gran Prix Circuit" && k.kor == 3) {
        None => {},
        Some(k) => println!("4. Feladat: {} mp", k.korido.num_seconds_from_midnight())
    }

    println!("5. Felatad: Írj be egy nevet!");

    let mut be_nev_str = String::new();
    stdin().read_line(&mut be_nev_str);

    let be_nev = be_nev_str.trim_end();

    match versenyek.iter().filter(|k| k.versenyzo == be_nev).min_by_key(|k| k.korido) {
        None => println!("6. Feladat: Nincs ilyen versenyző"),
        Some(k) => println!("6. Feladat: {}", k.korido)
    }
}

fn create_verseny(line: &String) -> Verseny {
    let split = line.split(';').collect::<Vec<_>>();

    Verseny {
        csapat: split[0].to_string(),
        versenyzo: split[1].to_string(),
        eletkor: split[2].parse::<i32>().unwrap(),
        palya: split[3].to_string(),
        korido: NaiveTime::parse_from_str(split[4], "%H:%M:%S").unwrap(),
        kor: split[5].parse::<i32>().unwrap()
    }
}

struct Verseny {
    csapat: String,
    versenyzo: String,
    eletkor: i32,
    palya: String,
    korido: NaiveTime,
    kor: i32
}