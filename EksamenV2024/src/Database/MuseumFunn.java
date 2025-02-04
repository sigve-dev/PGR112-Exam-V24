package Database;

import Oppgave.*;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.xml.transform.Result;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MuseumFunn {

    //Her initialiserer vi en statisk privat instans av database klassen.
    static private final database databaseInstance = new database("src/Properties/database.properties");

    //Initialiserer filstien til tekstfilen, og oppretter en scanner for å lese innholdet.
    public void lasteFilerFraTekstTilDatabase() throws SQLException, FileNotFoundException {
        var FilePath = "resources/funn.txt";
        var file = new File(FilePath);
        Scanner avleser = new Scanner(file);

        //Oppretter Hashmap for å holde på person og museum objekter, der ID blir brukt som nøkkel.
        var personer = new HashMap<Integer, Person>();
        var museumer = new HashMap<Integer, Museum>();

        // Her var planen å bruke settere til å lagre alle funnene i en arrayliste, men jeg endte med å ikke bruke dette i applikasjonen.
        // Ville likevel ha det inkludert ettersom jeg brukte lang tid på dette, og ville vise jeg første flere løsningsmetoder selv om de kanskje ikke fungerte som jeg ville.
/*        ArrayList<Vaapen> totalVaapen = new ArrayList<>();
        ArrayList<Smykke> totalSmykker = new ArrayList<>();
        ArrayList<Mynt> totalMynt = new ArrayList<>();*/

        //så lenge det er en ny linje, vil denne while loopen fortsette å kjøre
        while (avleser.hasNextLine()) {
            String line = avleser.nextLine();

            //Her kjøres tekstfilen linje for linje, og avsnitt blir identifisert ved å sjekke om linjene starter med 'Personer:', 'Museer:' etc.
            if (line.startsWith("Personer:")) {
                /*int totalePersoner = Integer.parseInt(line.split(": ")[1].trim());*/
                int totalePersoner = Integer.parseInt(avleser.nextLine());
                for (int i = 0; i < totalePersoner; i++) {
                    int id1 = Integer.parseInt(avleser.nextLine());
                    String navn1 = avleser.nextLine();
                    String tlf = avleser.nextLine();
                    String email = avleser.nextLine();
                    Person person = new Person(id1, navn1, tlf, email);
                    personer.put(person.getId(), person);
                    if (!databaseInstance.dataEksisterer("Person", person.getId())) {
                        databaseInstance.leggTilPerson(person);
                    }
                }

            } else if (line.startsWith("Museer:")) {
                int totaleMuseumer = Integer.parseInt(avleser.nextLine());
                for (int i = 0; i < totaleMuseumer; i++) {
                    int id2 = Integer.parseInt(avleser.nextLine());
                    String navn2 = avleser.nextLine();
                    String sted = avleser.nextLine();
                    Museum museum = new Museum(id2, navn2, sted);
                    museumer.put(museum.getId(), museum);
                    if (!databaseInstance.dataEksisterer("Museum", museum.getId())) {
                        databaseInstance.leggTilMuseum(museum);
                    }
                }

                //Denne delen var utfordrende, ettersom enkelte funn ikke hadde museumsID som gjorde parseringen utrolig vanskelig.
                //Min løsning til dette problemet var derfor å oprette en temp, eller temporary string som ville huse dataen,
                //og deretter sette museumID til et fiktivt nummer som 999. Dermed hadde jeg en if statement som så etter dersom
                //Temp stringen ikke var tom, og dersom den ikke var det ville den lese av relevant data og gjøre den om til en Integer.

            } else if (line.startsWith("Funn:")) {
                while (avleser.hasNextLine()) {
                    line = avleser.nextLine();
                    if (line.startsWith("-------")) {
                        continue;
                    }
                    int id = Integer.parseInt(line);
                    String[] koordinater = avleser.nextLine().split(",");
                    String funnsted = STR."\{koordinater[0]},\{koordinater[1]}";
                    int finnId = Integer.parseInt(avleser.nextLine());
                    String tidspunkt = avleser.nextLine();
                    int antattAarstallfunnet = Integer.parseInt(avleser.nextLine());
                    String temp = avleser.nextLine();
                    int museumId = 999;
                    if (temp != "") {
                        museumId = Integer.parseInt(temp);
                    }
                    String type = avleser.nextLine();
                    if (type.equals("Våpen")) {
                        String vaapenType = avleser.nextLine();
                        String materiale = avleser.nextLine();
                        int vekt = Integer.parseInt(avleser.nextLine());
                        Vaapen vaapen = new Vaapen(id, funnsted, finnId, tidspunkt, antattAarstallfunnet, museumId, vaapenType, materiale, vekt);
/*                        vaapen.setId(id);
                        vaapen.setFunnsted(funnsted);
                        vaapen.setFinnerId(finnId);
                        vaapen.setFunntidspunkt(tidspunkt);
                        vaapen.setAntattAarFunnet(antattAarstallfunnet);
                        vaapen.setMuseumId(museumId);
                        vaapen.setType(vaapenType);
                        vaapen.setMateriale(materiale);
                        vaapen.setVekt(vekt);
                        totalVaapen.add(vaapen);*/
                        databaseInstance.leggTilFunn(vaapen);
                    } else if (type.equals("Smykke")) {
                        String smykkeType = avleser.nextLine();
                        int verdiestimat = Integer.parseInt(avleser.nextLine());
                        String filnavn = avleser.nextLine();
                        Smykke smykke = new Smykke(id, funnsted, finnId, tidspunkt, antattAarstallfunnet, museumId, smykkeType, verdiestimat, filnavn);
/*                        smykke.setId(id);
                        smykke.setFunnsted(funnsted);
                        smykke.setFinnerId(finnId);
                        smykke.setFunntidspunkt(tidspunkt);
                        smykke.setAntattAarFunnet(antattAarstallfunnet);
                        smykke.setMuseumId(museumId);
                        smykke.setType(smykkeType);
                        smykke.setVerdiEstimat(verdiestimat);
                        smykke.setFilnavn(filnavn);
                        totalSmykker.add(smykke);*/
                        databaseInstance.leggTilFunn(smykke);
                    } else if (type.equals("Mynt")) {
                        int diameter = Integer.parseInt(avleser.nextLine());
                        String metall = avleser.nextLine();
                        Mynt mynt = new Mynt(id, funnsted, finnId, tidspunkt, antattAarstallfunnet, museumId, diameter, metall);
/*                        mynt.setId(id);
                        mynt.setFunnsted(funnsted);
                        mynt.setFinnerId(finnId);
                        mynt.setFunntidspunkt(tidspunkt);
                        mynt.setAntattAarFunnet(antattAarstallfunnet);
                        mynt.setMuseumId(museumId);
                        mynt.setDiameter(diameter);
                        mynt.setMetall(metall);
                        totalMynt.add(mynt);*/
                        databaseInstance.leggTilFunn(mynt);
                    }
                }
            }
        }
    }

    public static void printDatabaseStats() throws SQLException {
        try (Connection connection = databaseInstance.getConnection();
             Statement statement = connection.createStatement()) {

            // Count number of persons
            ResultSet personResult = statement.executeQuery("SELECT COUNT(*) FROM person");
            if (personResult.next()) {
                int personCount = personResult.getInt(1);
                System.out.println("Totalt antall personer i databasen: " + personCount);
            }

            // Count number of museums
            ResultSet museumResult = statement.executeQuery("SELECT COUNT(*) FROM museum");
            if (museumResult.next()) {
                int museumCount = museumResult.getInt(1);
                System.out.println("totalt antall museumer i databasen: " + museumCount);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    // For følgende del av oppgaven ble det mye repeterende kode, men jeg var ikke helt sikker på hvordan
    // jeg skulle løse dette på en mer effektiv måte, basert på tiden vi fikk med oppgaven. Dere vil derfor finne mye repitisjon,
    // og ingen direkte 'clean' kode, men det endte med å fungere og alt fungerte som det skulle.

    //Her hentes alle funn fra tabellene, og oppretter objekter for hvert funn og legger dem til i en liste som returneres.

    //Hente ut alle funn:
    public ArrayList<Funn> getAlleFunn() throws SQLException {
        ArrayList<Funn> funnliste = new ArrayList<>();
        try (Connection connection = databaseInstance.getConnection();
            Statement statement = connection.createStatement()) {

            ResultSet result = statement.executeQuery("SELECT * FROM Vaapen");
            while (result.next()) {
                Funn vaapen = new Vaapen(
                        result.getInt("Id"),
                        result.getString("Funnsted"),
                        result.getInt("Finner_id"),
                        result.getString("Funntidspunkt"),
                        result.getInt("Antatt_årstall"),
                        result.getInt("Museum_id"),
                        result.getString("Type"),
                        result.getString("Materiale"),
                        result.getInt("Vekt")
                );
                funnliste.add(vaapen);
            }

            result = statement.executeQuery("SELECT * FROM Mynt");
            while (result.next()) {
                Funn mynt = new Mynt(
                        result.getInt("Id"),
                        result.getString("Funnsted"),
                        result.getInt("Finner_id"),
                        result.getString("Funntidspunkt"),
                        result.getInt("Antatt_årstall"),
                        result.getInt("Museum_id"),
                        result.getInt("Diameter"),
                        result.getString("Metall")
                );
                funnliste.add(mynt);
            }

            result = statement.executeQuery("SELECT * FROM Smykke");
            while (result.next()) {
                Funn smykke = new Smykke(
                        result.getInt("Id"),
                        result.getString("Funnsted"),
                        result.getInt("Finner_id"),
                        result.getString("Funntidspunkt"),
                        result.getInt("Antatt_årstall"),
                        result.getInt("Museum_id"),
                        result.getString("Type"),
                        result.getInt("Verdiestimat"),
                        result.getString("filnavn")
                );
                funnliste.add(smykke);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

    return funnliste;
    }

    //Her hentes kun funn som er eldre enn et gitt årstall fra tabellene. Her også opprettes objekter for hvert funn og legger dem til i en liste som returneres.

    public ArrayList<Funn> getEldreEnnFunn(int aarstall) throws SQLException {
        ArrayList<Funn> funnListe = new ArrayList<>();
        try (Connection connection = databaseInstance.getConnection();
            PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM Vaapen WHERE Antatt_årstall < ?")) {
            statement1.setInt(1, aarstall);
            ResultSet result = statement1.executeQuery();
            while (result.next()) {
                Funn vaapen = new Vaapen(
                        result.getInt("Id"),
                        result.getString("Funnsted"),
                        result.getInt("Finner_id"),
                        result.getString("Funntidspunkt"),
                        result.getInt("Antatt_årstall"),
                        result.getInt("Museum_id"),
                        result.getString("Type"),
                        result.getString("Materiale"),
                        result.getInt("Vekt")
                );
                funnListe.add(vaapen);
            }

            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM Mynt WHERE Antatt_Årstall < ?");
            statement2.setInt(1, aarstall);
            result = statement2.executeQuery();
            while (result.next()) {
                Funn mynt = new Mynt(
                        result.getInt("Id"),
                        result.getString("Funnsted"),
                        result.getInt("Finner_id"),
                        result.getString("Funntidspunkt"),
                        result.getInt("Antatt_årstall"),
                        result.getInt("Museum_id"),
                        result.getInt("Diameter"),
                        result.getString("Metall")
                );
                funnListe.add(mynt);
            }


            PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM Smykke WHERE Antatt_Årstall < ?");
            statement3.setInt(1, aarstall);
            result = statement3.executeQuery();
            while (result.next()) {
                Funn smykke = new Smykke(
                        result.getInt("Id"),
                        result.getString("Funnsted"),
                        result.getInt("Finner_id"),
                        result.getString("Funntidspunkt"),
                        result.getInt("Antatt_årstall"),
                        result.getInt("Museum_id"),
                        result.getString("Type"),
                        result.getInt("Verdiestimat"),
                        result.getString("filnavn")
                );
                funnListe.add(smykke);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return funnListe;
    }

    //Her hentes totalt antall funn fra tabellene, der de summeres og returnerer dermed totalen.

    public int getSpesifikkeFunn() throws SQLException {
        int antall = 0;
        try (Connection connection = databaseInstance.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultS = statement.executeQuery("SELECT COUNT(*) AS total FROM Vaapen");
            if (resultS.next()) {
                antall += resultS.getInt("total");
            }

            resultS = statement.executeQuery("SELECT COUNT(*) AS total FROM Smykke");
            if (resultS.next()) {
                antall += resultS.getInt("total");
            }

            resultS = statement.executeQuery("SELECT COUNT(*) AS total FROM Mynt");
            if (resultS.next()) {
                antall += resultS.getInt("total");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return antall;
    }
}