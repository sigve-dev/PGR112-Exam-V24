package Oppgave;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class MainDel2 {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        try {
            //Som i den andre main metoden, starter vi med å opprette et nytt objekt av denne gangen applikasjonsMeny klassen.
            applikasjonsMeny meny = new applikasjonsMeny();

            //Deretter kaller vi på startMeny klassen for å kjøre applikasjonen.
            meny.startMeny();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
