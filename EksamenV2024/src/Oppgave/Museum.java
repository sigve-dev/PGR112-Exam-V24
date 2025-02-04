package Oppgave;

public class Museum
{
    private int id;
    private String navn;
    private String sted;

    public Museum(int id, String navn, String sted) {
        this.id = id;
        this.navn = navn;
        this.sted = sted;
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
    public void setNavn() {
        this.navn = navn;
    }

    public String getSted() {
        return sted;
    }
    public void setSted() {
        this.sted = sted;
    }
}
