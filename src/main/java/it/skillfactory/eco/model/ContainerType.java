package it.skillfactory.eco.model;

public enum ContainerType {
    CONTAINER("Boxed (Larghezza Contenuta - 'container')"),
    CONTAINER_FLUID("Full Width (Larghezza Schermo - 'container-fluid')");

    private final String cssClass;

    ContainerType(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}