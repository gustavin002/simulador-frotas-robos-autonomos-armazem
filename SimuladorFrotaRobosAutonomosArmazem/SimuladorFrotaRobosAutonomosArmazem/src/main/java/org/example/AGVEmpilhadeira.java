package org.example;

public class AGVEmpilhadeira extends RoboAGV {

    public AGVEmpilhadeira() {
    }

    public AGVEmpilhadeira(String identificador, double porcentagemBateria, double pesoCargaAtual, String coordenadaDestino) {
        super(identificador, porcentagemBateria, pesoCargaAtual, coordenadaDestino);
    }

    @Override
    public void mover(int distancia){
        setPorcentagemBateria(getPorcentagemBateria() - distancia * 2.0);
    }

}
