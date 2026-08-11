package it.skillfactory.eco.model;

public enum BlockType {
    JUMBOTRON("Jumbotron / Sezione Testo"),
    CARDS("Griglia Card (2 o 3 Colonne)"),
    CAROUSEL("Carosello di Immagini / Slider");

    private final String label;

    BlockType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}