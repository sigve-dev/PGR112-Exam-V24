package Oppgave;

public class Mynt extends Funn
{
    private int diameter;
    private String metall;

    public Mynt(int id, String funnsted, int finnerId, String funnrtidspunkt, int antattAarFunnet, int museumId, int diameter, String metall) {
        super(id, funnsted, finnerId, funnrtidspunkt, antattAarFunnet, museumId);
        this.diameter = diameter;
        this.metall = metall;
    }

    //Gettere og Settere
    //Settere er opprettet men endt med å ikke bli brukt i denne besvarelsen.

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public String getMetall() {
        return metall;
    }

    public void setMetall(String metall) {
        this.metall = metall;
    }
}
