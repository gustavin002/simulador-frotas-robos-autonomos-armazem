package org.example;

public class AGVCargaPesada extends RoboAGV {

    public AGVCargaPesada() {
    }

    public AGVCargaPesada(String identificador, double porcentagemBateria, double pesoCargaAtual, String coordenadaDestino) {
        super(identificador, porcentagemBateria, pesoCargaAtual, coordenadaDestino);
    }

    @Override
    public void mover(int distancia){
        if(getPesoCargaAtual() > 700){
            setPorcentagemBateria(getPorcentagemBateria() - distancia * 3.0);
        } else {
            setPorcentagemBateria(getPorcentagemBateria() - distancia * 1.0);
        }
    }

}
