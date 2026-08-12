package it.skillfactory.eco.model;

public enum BlockType {
    JUMBOTRON("Jumbotron Generico"),
    CARDS("Griglia Card"),
    CAROUSEL("Carosello Dynamic"),
    JUMBO_DEMO_1("Jumbo Demo 1 (Testo Sinistra + Immagine Destra)"),
    JUMBO_DEMO_2("Jumbo Demo 2 (Immagine Sinistra + Testo Destra)"),
    JUMBO_2_COL("Jumbo Demo 2 Colonne (Testo + Testo/Offerta)");

    private final String label;

    BlockType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}