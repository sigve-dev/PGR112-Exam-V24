package Oppgave;

public abstract class Funn
{
    private int id;
    private String funnsted;
    private int finnerId;
    private String funntidspunkt;
    private int antattAarFunnet;

    // jeg satte følgende til en ikke-primitiv type for å kunne tillatte å gjøre verdien til null dersom den mangler.
    // Dette var nødvendig for å legge inn funn der museum_id ikke var tilgjengelig.

    private Integer museumId;

    public Funn(int id, String funnsted, int finnerId, String funntidspunkt, int antattAarFunnet, int museumId) {
        this.id = id;
        this.funnsted = funnsted;
        this.finnerId = finnerId;
        this.funntidspunkt = funntidspunkt;
        this.antattAarFunnet = antattAarFunnet;
        if (museumId == 0) {
            this.museumId = null;
        }
        this.museumId = museumId;
    }

    //Gettere og Settere
    //Settere er opprettet men endt med å ikke bli brukt i denne besvarelsen.

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFunnsted() {
        return funnsted;
    }
    public void setFunnsted(String funnsted) {
        this.funnsted = funnsted;
    }

    public int getFinnerId() {
        return finnerId;
    }
    public void setFinnerId(int finnerId) {
        this.finnerId = finnerId;
    }

    public String getFunntidspunkt() {
        return funntidspunkt;
    }
    public void setFunntidspunkt(String funntidspunkt) {
        this.funntidspunkt = funntidspunkt;
    }

    public int getAntattAarFunnet() {
        return antattAarFunnet;
    }
    public void setAntattAarFunnet(int antattAarFunnet) {
        this.antattAarFunnet = antattAarFunnet;
    }

    public int getMuseumId() {
        return museumId;
    }
    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }
}
