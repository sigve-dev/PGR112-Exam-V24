package Oppgave;

public class Person
{
    private int id;
    private String navn;
    private String tlf;
    private String email;

    public Person(int id, String navn, String tlf, String email) {
        this.id = id;
        this.navn = navn;
        this.tlf = tlf;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
