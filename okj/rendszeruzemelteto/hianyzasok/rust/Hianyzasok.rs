use std::fs::File;
use std::io::{ BufReader, BufRead, Write, stdin };

use itertools::Itertools;

struct Hianyzas {
    nev: String,
    osztaly: String,
    elso_nap: i32,
    utolso_nap: i32,
    mulasztott_orak: i32
}

fn create_hianyzas(line: &String) -> Hianyzas {
    let split = line.split(';').collect::<Vec<&str>>();

    Hianyzas {
        nev: split[0].to_string(),
        osztaly: split[1].to_string(),
        elso_nap: split[2].parse::<i32>().unwrap(),
        utolso_nap: split[3].parse::<i32>().unwrap(),
        mulasztott_orak: split[4].parse::<i32>().unwrap()
    }
}

fn main() {
    let input_file = File::open("szeptember.csv").unwrap();
    let hianyzasok = BufReader::new(input_file)
                              .lines()
                              .skip(1)
                              .map(|k| create_hianyzas(&k.unwrap()))
                              .collect::<Vec<Hianyzas>>();

    let total_hianyzott_orak = hianyzasok.iter()
                                         .map(|k| k.mulasztott_orak)
                                         .sum::<i32>();

    println!("2. Feladat: Hiányzott órák: {}", total_hianyzott_orak);
    println!("{}", "3. Feladat: Írj be egy napot(1-30) és egy nevet!");

    let mut bekert_nap_str = String::new();
    let mut bekert_nev_str = String::new();
    stdin().read_line(&mut bekert_nap_str);
    stdin().read_line(&mut bekert_nev_str);

    let bekert_nap = bekert_nap_str.trim_end().parse::<i32>().unwrap();
    let bekert_nev = bekert_nev_str.trim_end();

    println!("{}", "4. Feladat:");

    let bekert_hianyzott_e = hianyzasok.iter()
                                       .any(|k| k.nev == bekert_nev);

    println!("4. Feladat: {} {}", bekert_nev, if bekert_hianyzott_e { "hiányzott" } else { "nem hiányzott" });
    println!("5. Feladat:");

    let azon_a_napon_hianyoztak = hianyzasok.iter()
                                            .filter(|k| bekert_nap >= k.elso_nap && bekert_nap <= k.utolso_nap)
                                            .collect::<Vec<&Hianyzas>>();

    if azon_a_napon_hianyoztak.len() == 0 {
        println!("Nem volt hiányzó");
    }else{
        azon_a_napon_hianyoztak.iter().for_each(|k| println!("{} {}", k.nev, k.osztaly));
    }

    let to_file = hianyzasok.iter()
                            .sorted_by_key(|k| &k.osztaly)
                            .group_by(|k| &k.osztaly)
                            .into_iter()
                            .map(|(osztaly, items)| format!("{};{}", osztaly, items.map(|k| k.mulasztott_orak).sum::<i32>()))
                            .join("\n");

    let mut output = File::create("osszesites.csv").unwrap();
    write!(output, "{}", to_file);
}