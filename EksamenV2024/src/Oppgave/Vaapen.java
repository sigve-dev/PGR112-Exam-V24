package Oppgave;

public class Vaapen extends Funn
{
    private String type;
    private String materiale;
    private int vekt;

    public Vaapen(int id, String funnsted, int finnerId, String funntidspunkt, int antattAarFunnet, int museumId, String type, String materiale, int vekt) {
        super(id, funnsted, finnerId, funntidspunkt, antattAarFunnet, museumId);
        this.type = type;
        this.materiale = materiale;
        this.vekt = vekt;
    }

    //Gettere og Settere
    //Settere er opprettet men endt med å ikke bli brukt i denne besvarelsen.

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getMateriale() {
        return materiale;
    }
    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public int getVekt() {
        return vekt;
    }
    public void setVekt(int vekt) {
        this.vekt = vekt;
    }

}
