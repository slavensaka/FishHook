FishNet
=======

Moj projektni rad za kolegij Razvoj Mobilnih Aplikacija etfos-a

FishNet je aplikacija za Android mobilne uređaje koja služi za ribiče profesionalce, amatere i entuzijaste. Aplikacija pomaže ribičima brzu izradu zabilješku ulovljenih riba, mjesta riboloca i ribolovno putovanja na lagan, brz i jednostavan način. FishNet je izrađen tako da se jednostavno dođe do željene opcije  i za što kraće vrijeme spreme određeni podaci, da se ne troši vrijeme za unos ovih podataka. Također ima mogućnost i kasnijeg unosa podataka, tako da korisnik ne mora prekinuti svoj ribolov. 
Prilikom pokretanja aplikacije korisniku je za sada omogućeno korištenje šest opcija. Mogu se dodavati lokacije za ribolov na google mapi, brisanje lokacija na google mapi, mogućnost slikanja ulovljene, viđene ili otpale ribe, unošenje  nekoliko osnovnih informacija o događaju ili događajima, unošenje na popis izgubljene, pokidane i ukradene opreme  koje se događaju na ribolovnim putovanjima, kategoriziranje opreme po kategorijama, omogućena je provjera prognoze vremena pomoću servisa na openweathermap.org web stranici i stvorena je mogućnost kamerom zabilježit  korisnikovo ribolovno iskustvo, zasad samo slikama kamere. 

Android aplikacija razvijana je u Eclipse Luna razvojnom okruženju s instaliranim Android SDK i Android AVD paketima te sa dodatnom bibliotekom Google play services. Aplikacija je razvijana u programskom jeziku Java i XML jeziku za prikaz dokumenata (eng. Markup language). 

Aplikacija se može instalirati na mobilne uređaje s izdanjem Android operacijskog sustava 4.1 , instalacije unazad na Android OS 2.0 do 4 nisu uključene zbog različith bugova koji će se tek ukloniti. Za instalaciju je potrebno prenijeti apk datoteku na mobilni uređaj. Za korištenje potrebno je omogućit pristup internetu i gps(poželjno) za google maps i pristup internetu  za prognozu. Za ostale potrebe nije potreban pristup internetu i gps. 
Potrebne su dodatne biblioteke  google play servisa google-play-services_lib i appcombat_v7. Dodati ove dvije biblioteke u projekt i postaviti u projektni build path. Potrebno u SDK manageru skinut google play services.  Uputa ima na internetu.
