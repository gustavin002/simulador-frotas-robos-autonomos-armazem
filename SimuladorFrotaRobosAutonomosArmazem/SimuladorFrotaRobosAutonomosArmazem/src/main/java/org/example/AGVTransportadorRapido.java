package org.example;

public class AGVTransportadorRapido extends RoboAGV {

    public AGVTransportadorRapido() {
    }

    public AGVTransportadorRapido(String identificador, double porcentagemBateria, double pesoCargaAtual, String coordenadaDestino) {
        super(identificador, porcentagemBateria, pesoCargaAtual, coordenadaDestino);
    }

    @Override
    public void mover(int distancia){
        setPorcentagemBateria(getPorcentagemBateria() - distancia * 1.5);
    }

}
