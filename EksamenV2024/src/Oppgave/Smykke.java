package Oppgave;

public class Smykke extends Funn
{
    private String type;
    private int verdiEstimat;
    private String filNavn;

    public Smykke(int id, String funnsted, int finnerId, String funntidspunkt, int antattAarFunnet, int museumId, String type, int verdiEstimat, String filnavn) {
        super(id, funnsted, finnerId, funntidspunkt, antattAarFunnet, museumId);
        this.type = type;
        this.verdiEstimat = verdiEstimat;
        this.filNavn = filnavn;
    }

    //Gettere og Settere
    //Settere er opprettet men endt med å ikke bli brukt i denne besvarelsen.

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public int getVerdiEstimat() {
        return verdiEstimat;
    }
    public void setVerdiEstimat(int verdiEstimat) {
        this.verdiEstimat = verdiEstimat;
    }

    public String getFilnavn() {
        return filNavn;
    }
    public void setFilnavn(String filnavn) {
        this.filNavn = filnavn;
    }

}
