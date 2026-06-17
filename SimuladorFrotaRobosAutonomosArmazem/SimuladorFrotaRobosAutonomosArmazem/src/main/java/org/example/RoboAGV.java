package org.example;

public class RoboAGV {

    private String identificador;
    private double porcentagemBateria;
    private double pesoCargaAtual;
    private String coordenadaDestino;



    public RoboAGV() {
    }

    public RoboAGV(String identificador, double porcentagemBateria, double pesoCargaAtual, String coordenadaDestino) {
        this.identificador = identificador;
        this.porcentagemBateria = 100;
        this.pesoCargaAtual = pesoCargaAtual;
        this.coordenadaDestino = coordenadaDestino;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public double getPorcentagemBateria() {
        return porcentagemBateria;
    }

    public void setPorcentagemBateria(double porcentagemBateria) {
        this.porcentagemBateria = porcentagemBateria;
    }

    public double getPesoCargaAtual() {
        return pesoCargaAtual;
    }

    public void setPesoCargaAtual(double pesoCargaAtual) {
        this.pesoCargaAtual = pesoCargaAtual;
    }

    public String getCoordenadaDestino() {
        return coordenadaDestino;
    }

    public void setCoordenadaDestino(String coordenadaDestino) {
        this.coordenadaDestino = coordenadaDestino;
    }
    public void mover(int distancia){

    }

}
