use std::{ fs::File, io::BufReader, io::BufRead };
use encoding_rs::WINDOWS_1252;
use encoding_rs_io::DecodeReaderBytesBuilder;
use chrono::NaiveDate;
use itertools::Itertools;

fn main() {
    let input_file = File::open("eredmenyek.csv").unwrap();
    let eredmenyek = BufReader::new(DecodeReaderBytesBuilder::new().encoding(Some(WINDOWS_1252)).build(input_file))
                              .lines()
                              .skip(1)
                              .map(|k| create_eredmeny(&k.unwrap()))
                              .collect::<Vec<_>>();

    let hazai_madrid_db = eredmenyek.iter()
                                    .filter(|k| k.hazai_csapat == "Real Madrid")
                                    .count();

    let idegen_madrid_db = eredmenyek.iter()
                                     .filter(|k| k.idegen_csapat == "Real Madrid")
                                     .count();

    println!("3. Feladat: Hazai: {}, idegen: {}", hazai_madrid_db, idegen_madrid_db);

    match eredmenyek.iter().any(|k| k.hazai_pont == k.idegen_pont) {
        false => println!("4. Feladat: Nem volt döntetlen"),
        true => println!("4. Feladat: Volt döntetlen")
    };

    match eredmenyek.iter().find(|k| k.hazai_csapat.contains("Barcelona") || k.idegen_csapat.contains("Barcelona")) {
        None => {},
        Some(k) => println!("5. Feladat: Barcelona csapat neve: {}", if k.hazai_csapat.contains("Barcelona") { &k.hazai_csapat } else { &k.idegen_csapat })
    }

    let hatos_feladat_idopont = NaiveDate::from_ymd_opt(2004, 11, 21).unwrap();

    println!("6. Feladat:");

    eredmenyek.iter()
              .filter(|k| k.idopont == hatos_feladat_idopont)
              .for_each(|k| println!("    {} - {} ({}:{})", k.hazai_csapat, k.idegen_csapat, k.hazai_pont, k.idegen_pont));

    println!("7. Feladat:");

    eredmenyek.iter()
              .sorted_by_key(|k| &k.helyszin)
              .group_by(|k| &k.helyszin)
              .into_iter()
              .map(|(helyszin, eredmenyek_helyszinhez)| (helyszin, eredmenyek_helyszinhez.count()))
              .filter(|(_, db)| *db > 20)
              .for_each(|(helyszin, db)| println!("    {}: {}", helyszin, db));
}

fn create_eredmeny(line: &String) -> Eredmeny {
    let split = line.split(';').collect::<Vec<_>>();

    Eredmeny {
        hazai_csapat: split[0].to_string(),
        idegen_csapat: split[1].to_string(),
        hazai_pont: split[2].parse::<i32>().unwrap(),
        idegen_pont: split[3].parse::<i32>().unwrap(),
        helyszin: split[4].to_string(),
        idopont: NaiveDate::parse_from_str(split[5], "%Y-%m-%d").unwrap()
    }
}

struct Eredmeny {
    hazai_csapat: String,
    idegen_csapat: String,
    hazai_pont: i32,
    idegen_pont: i32,
    helyszin: String,
    idopont: NaiveDate
}