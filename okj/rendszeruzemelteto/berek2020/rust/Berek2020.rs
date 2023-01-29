use std::{ fs::File, io::BufReader, io::BufRead, io::stdin };

use itertools::Itertools;

fn main() {
    let input_file = File::open("berek2020.txt").unwrap();
    let dolgozok = BufReader::new(input_file)
                            .lines()
                            .skip(1)
                            .map(|k| create_dolgozo(&k.unwrap()))
                            .collect::<Vec<Dolgozo>>();

    println!("3. Feladat: Dolgozók száma: {}", dolgozok.len());

    let atlag_ber = dolgozok.iter()
                            .map(|k| k.munka_ber)
                            .sum::<i32>() as f32 / dolgozok.len() as f32 / 1000f32;

    println!("4. Feladat: Átlagbér: {:.2}", atlag_ber);
    println!("5. Feladat: Írjon be 1 részleg nevet!");

    let mut bekert_reszleg_str = String::new();
    stdin().read_line(&mut bekert_reszleg_str);

    let bekert_reszleg = bekert_reszleg_str.trim_end();
    let bekert_reszlegen_dolgozok = dolgozok.iter()
                                            .filter(|k| k.munka_reszleg == bekert_reszleg)
                                            .collect::<Vec<&Dolgozo>>();

    match bekert_reszlegen_dolgozok.iter().max_by_key(|k| k.munka_ber) {
        None => println!("A megadott részleg nem létezik a cégnél!"),
        Some(k) => println!("{} ({}) : {} FT", k.nev, k.munkaba_lepes_ev, k.munka_ber)
    };

    println!("7. Feladat:");

    dolgozok.iter()
            .sorted_by_key(|k| &k.munka_reszleg)
            .group_by(|k| &k.munka_reszleg)
            .into_iter()
            .for_each(|(reszleg, dolgozok_reszleghez)| println!("    {} - {} fő", reszleg, dolgozok_reszleghez.count()));
}

fn create_dolgozo(line: &String) -> Dolgozo {
    let split = line.split(';').collect::<Vec<&str>>();

    Dolgozo {
        nev: split[0].to_string(),
        nem: split[1].to_string(),
        munka_reszleg: split[2].to_string(),
        munkaba_lepes_ev: split[3].parse::<i32>().unwrap(),
        munka_ber: split[4].parse::<i32>().unwrap()
    }
}

struct Dolgozo {
    nev: String,
    nem: String,
    munka_reszleg: String,
    munkaba_lepes_ev: i32,
    munka_ber: i32
}