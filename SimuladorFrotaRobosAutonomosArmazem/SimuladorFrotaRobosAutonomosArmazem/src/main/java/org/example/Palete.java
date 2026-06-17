package org.example;

import java.util.Random;

public class Palete {
    private String id;
    private String carga;
    private String status;
    private String posicao;
    private String nivelPeso;

    private static final Random random = new Random();

    public Palete() {
    }

    public Palete(String carga, String status, String posicao, String nivelPeso) {
        this.id = "js" + random.nextInt(100,999);
        this.carga = carga;
        this.status = status;
        this.posicao = null;
        this.nivelPeso = nivelPeso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if(status.equalsIgnoreCase("alocado") || status.equalsIgnoreCase("doca")) {
            this.status = status;
        } else {
            System.err.println("Erro o status deve ser alocado ou doca");
        }
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String getNivelPeso() {
        return nivelPeso;
    }

    public void setNivelPeso(String nivelPeso) {
        if (nivelPeso.equalsIgnoreCase("leve") || nivelPeso.equalsIgnoreCase("media") || nivelPeso.equalsIgnoreCase("pesada")) {
            this.nivelPeso = nivelPeso;
        } else {
            System.err.println("Erro nivel de peso deve ser leve ou media ou pesada");
        }
    }

}
