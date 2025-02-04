package Oppgave;

import Database.MuseumFunn;
import Database.database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class applikasjonsMeny
{
    private MuseumFunn museumFunn = new MuseumFunn();

    public applikasjonsMeny() throws SQLException, FileNotFoundException {
    }

    //For inspirasjon til denne koden, hjalp den siste repitisjonsforelesningen til Stefan meg igjennom å sette opp en meny
    //for en applikasjon i denne type oppgave, der man jobber med en database som har hentet data fra en tekstfil.
    //jeg vedlegger derfor forelesningen som en relevant kilde til oppsettet: https://kristiania.cloud.panopto.eu/Panopto/Pages/Viewer.aspx?id=d2bc1b80-e127-4934-9571-b17000aecd6e

    public void startMeny() throws SQLException, FileNotFoundException {

        String tekstInput;
        int tallInput;
        Scanner scanner = new Scanner(System.in);
        int input;
        System.out.println("---------------------");
        System.out.println("Velkommen til min applikasjon!\nBli kjent med historiske objekter ved å velge ett av alternativene under!");

        do {
            System.out.println("---------------------");
            System.out.println("1. Se informasjon om alle funngjenstander!\n2. Se informasjon om alle funngjenstander eldre enn " +
                    "et år du velger selv!\n3. Få informasjon om antall funngjenstander registrert!\n4. Avslutt program.");
            System.out.println("---------------------");
            System.out.println("Skriv inn her: ");
            input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
                case 1:
                    visAlleFunn();
                    break;
                case 2:
                    visEldreEnnFunn(scanner);
                    break;
                case 3:
                    visAntallFunn();
                    break;
                default:
                    System.out.println("---------------------");
                    System.out.println("Avslutter programmet, takk for å prøvekjøre den! ");
                    System.out.println("---------------------");
            }
        } while (input != 4);
    }

    private void visAlleFunn() throws SQLException {
        System.out.println("---------------------");
        System.out.println("Alle funngjenstander:");
        System.out.println("---------------------");
        ArrayList<Funn> alleFunn = museumFunn.getAlleFunn();
        for (Funn funn1 : alleFunn) {
            skrivUtFunn(funn1);
        }
    }

    public void visEldreEnnFunn(Scanner scanner) throws SQLException {
        System.out.println("Vennligst skriv et årstall:");
        int aarstall = scanner.nextInt();
        scanner.nextLine();

        System.out.println("---------------------");
        System.out.println("Gjenstander funnet som er eldre enn " + aarstall);
        System.out.println("---------------------");
        ArrayList<Funn> eldreEnnFunn = museumFunn.getEldreEnnFunn(aarstall);
        for (Funn funn : eldreEnnFunn) {
            skrivUtFunn(funn);
        }
    }

    public void visAntallFunn() throws SQLException {
        int antall = museumFunn.getSpesifikkeFunn();
        System.out.println("---------------------");
        System.out.println("Antall registrerte funnet gjenstander er som følger: " + antall);
        System.out.println("---------------------");
    }


    private void skrivUtFunn(Funn funn) {
        if  (funn instanceof Vaapen) {
            Vaapen vaapen = (Vaapen) funn;
            System.out.println("Vaapen:");
            System.out.println("Funnsted " + vaapen.getFunnsted());
            System.out.println("Antatt årstall funnet: " + vaapen.getAntattAarFunnet());
            System.out.println("Type: " + vaapen.getType());
            System.out.println("Materiale: " + vaapen.getMateriale());
            System.out.println("Vekt: " + vaapen.getVekt());
            System.out.println("----------------------");
        } else if (funn instanceof Smykke) {
            Smykke smykke = (Smykke) funn;
            System.out.println("Smykke:");
            System.out.println("Funnsted: " + smykke.getFunnsted());
            System.out.println("Antatt årstall funnet: " + smykke.getAntattAarFunnet());
            System.out.println("Type: " + smykke.getType());
            System.out.println("Verdiestimat: " + smykke.getVerdiEstimat());
            System.out.println("Bildefilnavn: " + smykke.getFilnavn());
            System.out.println("----------------------");
        } else if (funn instanceof Mynt) {
            Mynt mynt = (Mynt) funn;
            System.out.println("Mynt:");
            System.out.println("Funnsted: " + mynt.getFunnsted());
            System.out.println("Antatt årstall funnet: " + mynt.getAntattAarFunnet());
            System.out.println("Diameter: " + mynt.getDiameter());
            System.out.println("Type metall: " + mynt.getMetall());
            System.out.println("----------------------");
        }
    }
}

