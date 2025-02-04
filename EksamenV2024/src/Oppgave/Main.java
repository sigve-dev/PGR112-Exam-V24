package Oppgave;

import Database.MuseumFunn;
import Database.database;

public class Main
{
    public static void main(String[] args) {
        try {
            //Vi starter med å opprette et nytt objekt av MuseumFunn klassen.
            MuseumFunn museumfunn = new MuseumFunn();

            //Deretter kaller vi på følgende metode for å parsere data fra tekstfilen funn.txt og setter dette inn i databasen FUNN.
            museumfunn.lasteFilerFraTekstTilDatabase();

            //Har med en printout statement, for å bekrefte at prosessen er fullført.
            System.out.println("Dataen fra funn.txt filen har blitt lastet inn i databasen!");

            //Her printes den statiske metoden for å skrive ut statistikk om dataene i databasen. Dette også for å bekrefte at registreringen er fullført. Ettersom vi vet fra tekstfilen verdiene skal være 49, og 10.
            MuseumFunn.printDatabaseStats();

           //I tillegg har vi alt inne i en try-catch-blokk, som håndterer eventuelle unntak som kan oppstå. Dersom et unntak oppstår
            // blir 'e.printStackTrace()' kalt, som skriver ut informasjon om unntaket til konsollen.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
