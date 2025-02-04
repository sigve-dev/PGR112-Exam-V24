package Database;

import Oppgave.*;
import com.mysql.cj.jdbc.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class database {
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Følgende kode  for å koble til databasen er hentet i store deler fra forelesning 20: https://github.com/kristiania/PGR112v24/blob/main/code/lectures/_20/Database.java

    private final Properties properties = new Properties();

    {
        this.properties.put("host", "localhost");
        this.properties.put("port", "3306");
    }

    public database(String filePath) {
        try {
            this.properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> keysNeeded = List.of("host", "port", "database", "username", "password");

        if (!this.properties.keySet().containsAll(keysNeeded)) {
            System.out.print("Keys that exist: ");
            System.out.println(this.properties.keySet());

            throw new RuntimeException("Missing information to connect to database!");
        }
    }

    public Connection getConnection() throws SQLException {
        // URI: jdbc:mysql://host:port/database
        return DriverManager.getConnection(
                STR."\{"jdbc:mysql://%s:%s/%s".formatted(
                        this.properties.getProperty("host"),
                        this.properties.getProperty("port"),
                        this.properties.getProperty("database")
                )}?allowPublicKeyRetrieval=true&useSSL=false",
                this.properties.getProperty("username"),
                this.properties.getProperty("password")
        );
    }

    public boolean dataEksisterer(String tableName, int id) throws SQLException {
        String query = String.format("SELECT COUNT(*) FROM %s WHERE id = ?", tableName);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultset = statement.executeQuery()) {
                if (resultset.next()) {
                    return resultset.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return false;
    }

    public void leggTilPerson(Person person) throws SQLException {
        if (dataEksisterer("Person", person.getId())) {
            return;
        }

        String query = "INSERT INTO person (Id, Navn, Tlf, E_post) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, person.getId());
            statement.setString(2, person.getNavn());
            statement.setString(3, person.getTlf());
            statement.setString(4, person.getEmail());
            statement.executeUpdate();
        }
    }

    public void leggTilMuseum(Museum museum) throws SQLException {
        if (dataEksisterer("Museum", museum.getId())) {
            return;
        }

        String query = "INSERT INTO museum (Id, Navn, Sted) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, museum.getId());
            statement.setString(2, museum.getNavn());
            statement.setString(3, museum.getSted());
            /*int raderPaavirket2 = */statement.executeUpdate();


/*            if (raderPaavirket2 > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }*/
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void leggTilFunn(Funn funn) throws SQLException {
        if (dataEksisterer(funn.getClass().getSimpleName(), funn.getId())) {
            return;
        }

        boolean erPaaMuseum = true;

        if (funn.getMuseumId() == 999) {
            erPaaMuseum = false;
        }

        if (funn instanceof Mynt) {
            Mynt mynt = (Mynt) funn;
            String query = "INSERT INTO mynt (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Diameter, Metall) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            if (!erPaaMuseum) {
                query = "INSERT INTO mynt (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Diameter, Metall) VALUES (?, ?, ?, ?, ?, ?, ?)";
            }
            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, mynt.getId());
                statement.setString(2, mynt.getFunnsted());
                statement.setInt(3, mynt.getFinnerId());
                statement.setString(4, mynt.getFunntidspunkt());
                statement.setInt(5, mynt.getAntattAarFunnet());
                if (!erPaaMuseum) {
                    statement.setInt(6, mynt.getDiameter());
                    statement.setString(7, mynt.getMetall());
                } else {
                    statement.setInt(6, mynt.getMuseumId());
                    statement.setInt(7, mynt.getDiameter());
                    statement.setString(8, mynt.getMetall());
                }
                /*int raderPaavirket3 = */statement.executeUpdate();

/*                if (raderPaavirket3 > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                }*/
            }
        } else if (funn instanceof Vaapen) {
            Vaapen vaapen = (Vaapen) funn;
            String query = "INSERT INTO vaapen (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, Materiale, Vekt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            if (!erPaaMuseum) {
                query = "INSERT INTO vaapen (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Type, Materiale, Vekt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }
            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, vaapen.getId());
                statement.setString(2, vaapen.getFunnsted());
                statement.setInt(3, vaapen.getFinnerId());
                statement.setString(4, vaapen.getFunntidspunkt());
                statement.setInt(5, vaapen.getAntattAarFunnet());
                if (!erPaaMuseum) {
                    statement.setString(6, vaapen.getType());
                    statement.setString(7, vaapen.getMateriale());
                    statement.setInt(8, vaapen.getVekt());
                } else {
                    statement.setInt(6, vaapen.getMuseumId());
                    statement.setString(7, vaapen.getType());
                    statement.setString(8, vaapen.getMateriale());
                    statement.setInt(9, vaapen.getVekt());
                }

                /*int raderPaavirket4 = */statement.executeUpdate();
/*
                if (raderPaavirket4 > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                }*/
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        } else if (funn instanceof Smykke) {
            Smykke smykke = (Smykke) funn;
            String query = "INSERT INTO smykke (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Museum_id, Type, Verdiestimat, filnavn) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            if (!erPaaMuseum) {
                query = "INSERT INTO smykke (Id, Funnsted, Finner_id, Funntidspunkt, Antatt_årstall, Type, Verdiestimat, filnavn) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            }
            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, smykke.getId());
                statement.setString(2, smykke.getFunnsted());
                statement.setInt(3, smykke.getFinnerId());
                statement.setString(4, smykke.getFunntidspunkt());
                statement.setInt(5, smykke.getAntattAarFunnet());
                if (!erPaaMuseum) {
                    statement.setString(6, smykke.getType());
                    statement.setInt(7, smykke.getVerdiEstimat());
                    statement.setString(8, smykke.getFilnavn());
                } else {
                    statement.setInt(6, smykke.getMuseumId());
                    statement.setString(7, smykke.getType());
                    statement.setInt(8, smykke.getVerdiEstimat());
                    statement.setString(9, smykke.getFilnavn());
                }
/*                int raderPaavirket4 =*/ statement.executeUpdate();


/*                if (raderPaavirket4 > 0) {
                    connection.commit();
                } else {
                    connection.rollback();
                }*/
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
