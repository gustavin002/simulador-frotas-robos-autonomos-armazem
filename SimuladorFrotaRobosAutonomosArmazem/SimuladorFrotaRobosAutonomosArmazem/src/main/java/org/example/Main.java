package org.example;

import java.util.ArrayList;
import java.util.Scanner;
 
public class Main {
 
    private static String[][] armazem = new String[4][12];
    private static ArrayList<Palete> paletes = new ArrayList<>();
    private static ArrayList<RoboAGV> frota = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
 
    public static void main(String[] args) {
        inicializarArmazem();
        inicializarFrota();
 
        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();
 
            switch (opcao) {
                case 1:
                    cadastrarNovaCarga();
                    break;
                case 2:
                    alocarPalete();
                    break;
                case 3:
                    realocarPalete();
                    break;
                case 4:
                    liberarPalete();
                    break;
                case 5:
                    visualizarMapa();
                    break;
                case 6:
                    avancarTurno(null);
                    break;
                case 7:
                    colocarRoboParaCarregar();
                    break;
                case 8:
                    rodando = false;
                    System.out.println("Saindo do simulador...");
                    break;
                default:
                    System.out.println("Opção invalida! opção deve ser de 1 a 8");
            }
        }
    }
 
    private static void inicializarArmazem() {
        for (int linha = 0; linha < 4; linha++) {
            for (int coluna = 0; coluna < 12; coluna++) {
                armazem[linha][coluna] = "0";
            }
        }
    }
 
    private static void inicializarFrota() {
        frota.add(new AGVTransportadorRapido("AGV-01", 100, 0, null));
        frota.add(new AGVEmpilhadeira("AGV-02", 100, 0, null));
        frota.add(new AGVCargaPesada("AGV-03", 100, 0, null));
    }
 
    private static void exibirMenu() {
        System.out.println("Menu");
        System.out.println("1 - Cadastrar Nova Carga (Doca)");
        System.out.println("2 - Alocar Palete no Armazém");
        System.out.println("3 - Realocar Palete de Posição");
        System.out.println("4 - Liberar Palete (Expedição)");
        System.out.println("5 - Visualizar Mapa Físico");
        System.out.println("6 - Avançar Ciclo do Turno");
        System.out.println("7 - Colocar Robo para Carregar");
        System.out.println("8 - Sair do Simulador");
    }
 
    private static void cadastrarNovaCarga() {
        System.out.println("Cadastrar Nova Carga");
 
        System.out.println("Descrição da carga: ");
        String carga = sc.nextLine();
 
        System.out.println("Nível de Peso:");
        System.out.println("1 - Leve (Ate 200 kg)");
        System.out.println("2 - Media (De 201 kg a 700 kg)");
        System.out.println("3 - Pesada (Acima de 700 kg)");
        System.out.println("Escolha o nível de peso: ");
        int peso = sc.nextInt();
        sc.nextLine();
        
        String nivelPeso = "";
 
        if(peso == 1){
            nivelPeso = "LEVE";
        }
        else if (peso == 2) {
            nivelPeso = "MEDIA";
        } else if (peso == 3) {
            nivelPeso = "PESADA";
        } else {
            System.out.println("Opção incorreta!! o nivel de peso deve ser de 1 a 3");
            return;
        }
 
        System.out.println("Quantidade de paletes recebidos: ");
        int qtd = sc.nextInt();
        sc.nextLine();
 
        for (int contador = 0; contador < qtd; contador++) {
            Palete palete = new Palete(carga, "DOCA", null, nivelPeso);
            paletes.add(palete);
            System.out.println("Palete criado na doca com ID: " + palete.getId());
        }
    }
 
    private static void alocarPalete() {
        System.out.println("Alocar palete no Armazém");
        boolean temDoca = false;
        for (Palete palete : paletes) {
            if (palete.getStatus().equalsIgnoreCase("DOCA")) {
                System.out.println("Id: " + palete.getId() + " | Carga: " + palete.getCarga() + " | Peso: " + palete.getNivelPeso());
                temDoca = true;
            }
        }
 
        if (!temDoca) {
            System.out.println("Nenhum palete aguardando na doca.");
            return;
        }
 
        System.out.println("Digite o id do palete para alocar: ");
        String id = sc.nextLine();
 
        Palete paleteEscolhido = buscarPaletePorId(id);
        if (paleteEscolhido == null || !paleteEscolhido.getStatus().equalsIgnoreCase("DOCA")) {
            System.out.println("Erro: id do palete invalido ou nao esta na doca.");
            return;
        }
 
        System.out.println("Posições disponíveis no armazém:");
        for (int linha = 0; linha < 4; linha++) {
            for (int coluna = 0; coluna < 12; coluna++) {
                if ("0".equals(armazem[linha][coluna])) {
                    System.out.println("[ Linha " + linha + " Coluna " + coluna + " ]");
                }
            }
        }
 
        System.out.println("Defina a nova coordenada:");
        System.out.println("Linha (0 a 3): ");
        int linha = sc.nextInt();
        System.out.println("Coluna (0 a 11): ");
        int coluna = sc.nextInt();
        sc.nextLine();
 
        if (linha >= 0 && linha < 4 && coluna >= 0 && coluna < 12 && "0".equals(armazem[linha][coluna])) {
            armazem[linha][coluna] = paleteEscolhido.getId();
            paleteEscolhido.setStatus("ALOCADO");
            paleteEscolhido.setPosicao("Linha " + linha + " Coluna " + coluna);
            System.out.println("Sucesso: palete " + id + " alocado na posição Linha " + linha + " Coluna " + coluna);
        } else {
            System.out.println("Erro: posição invalida ou já ocupada.");
        }
    }
 
    private static void realocarPalete() {
        System.out.println("Realocar palete");
        boolean temAlocado = false;
        for (Palete palete : paletes) {
            if ("ALOCADO".equalsIgnoreCase(palete.getStatus())) {
                System.out.println("Id: " + palete.getId() + " | Carga: " + palete.getCarga() + " | Posição atual: " + palete.getPosicao());
                temAlocado = true;
            }
        }
 
        if (!temAlocado) {
            System.out.println("Nenhum palete atualmente alocado no armazém.");
            return;
        }
 
        System.out.println("Digite o id do palete para realocar: ");
        String id = sc.nextLine();
 
        Palete paleteEscolhido = buscarPaletePorId(id);
        if (paleteEscolhido == null || !paleteEscolhido.getStatus().equalsIgnoreCase("ALOCADO")) {
            System.out.println("Erro: id do palete invalido ou nao esta alocado.");
            return;
        }
 
        for (int linha = 0; linha < 4; linha++) {
            for (int coluna = 0; coluna < 12; coluna++) {
                if (paleteEscolhido.getId().equals(armazem[linha][coluna])) {
                    armazem[linha][coluna] = "0";
                }
            }
        }
 
        System.out.println("Novas posições disponíveis:");
        for (int linha = 0; linha < 4; linha++) {
            for (int coluna = 0; coluna < 12; coluna++) {
                if ("0".equals(armazem[linha][coluna])) {
                    System.out.println("[ Linha " + linha + " Coluna " + coluna + " ]");
                }
            }
        }
 
        System.out.println("Defina a nova coordenada:");
        System.out.println("Nova linha (0 a 3): ");
        int linha = sc.nextInt();
        System.out.println("Nova coluna (0 a 11): ");
        int coluna = sc.nextInt();
        sc.nextLine();
 
        if (linha >= 0 && linha < 4 && coluna >= 0 && coluna < 12 && "0".equals(armazem[linha][coluna])) {
            armazem[linha][coluna] = paleteEscolhido.getId();
            paleteEscolhido.setPosicao("Linha " + linha + " Coluna " + coluna);
            System.out.println("Sucesso: palete realocado para Linha " + linha + " Coluna " + coluna);
        } else {
            System.out.println("Erro: posição inválida ou ocupada. Palete retornado para a DOCA.");
            paleteEscolhido.setStatus("DOCA");
            paleteEscolhido.setPosicao(null);
        }
    }
 
    private static void liberarPalete() {
        System.out.println("Liberar palete (expedição)");
        boolean temAlocado = false;
        for (Palete palete : paletes) {
            if ("ALOCADO".equalsIgnoreCase(palete.getStatus())) {
                System.out.println("ID: " + palete.getId() + " | Carga: " + palete.getCarga() + " | Posição: " + palete.getPosicao());
                temAlocado = true;
            }
        }
 
        if (!temAlocado) {
            System.out.println("Nenhum palete alocado no armazém.");
            return;
        }
 
        System.out.println("Digite o ID do palete para expedir: ");
        String id = sc.nextLine();
 
        Palete paleteEscolhido = buscarPaletePorId(id);
        if (paleteEscolhido == null || !paleteEscolhido.getStatus().equalsIgnoreCase("ALOCADO")) {
            System.out.println("Erro: id do palete invalido ou nao esta alocado.");
            return;
        }
 
        for (int linha = 0; linha < 4; linha++) {
            for (int coluna = 0; coluna < 12; coluna++) {
                if (paleteEscolhido.getId().equals(armazem[linha][coluna])) {
                    armazem[linha][coluna] = "0";
                }
            }
        }
 
        paletes.remove(paleteEscolhido);
        System.out.println("Carga [" + paleteEscolhido.getCarga() + "] de nível [" + paleteEscolhido.getNivelPeso() + "] em rota para entrega com Robô Autônomo!");
    }
 
    private static void visualizarMapa() {
        System.out.println("Mapa físico do armazém");
    for (int linha = 0; linha < 4; linha++) {
        System.out.print("Linha " + linha + " :");
        for (int coluna = 0; coluna < 12; coluna++) {
            System.out.print(armazem[linha][coluna] + " ");
        }
        System.out.println();
    }
}
 
    private static void colocarRoboParaCarregar() {
        System.out.println("Estação de recarga");
        for (RoboAGV robo : frota) {
            System.out.println("Id: " + robo.getIdentificador() + " | Bateria atual: " + robo.getPorcentagemBateria() + "%");
        }
 
        System.out.println("Digite o id do robô para recarga rápida: ");
        String id = sc.nextLine();
 
        RoboAGV roboEscolhido = null;
        for (RoboAGV robo : frota) {
            if (robo.getIdentificador().equalsIgnoreCase(id)) {
                roboEscolhido = robo;
                break;
            }
        }
 
        if (roboEscolhido != null) {
            roboEscolhido.setPorcentagemBateria(100.0);
            System.out.println("Sucesso: robô " + roboEscolhido.getIdentificador() + " acoplado na estação. Bateria restaurada para 100%.");
            System.out.println("Avançando ciclo do turno automaticamente...");
            avancarTurno(roboEscolhido);
        } else {
            System.out.println("Erro: id invalido! robô não encontrado.");
        }
    }
 
    private static void avancarTurno(RoboAGV roboCarregando) {
        for (RoboAGV robo : frota) {
            if (robo != roboCarregando) {
                robo.mover(1);
            }
        }
        exibirTelemetria();
    }
 
    private static void exibirTelemetria() {
        System.out.println("Painel de telemetria:");
        for (RoboAGV robo : frota) {
            String tipo = robo.getClass().getSimpleName();
            String bateria = robo.getPorcentagemBateria() + "%";
            String alerta = "";
            if (robo.getPorcentagemBateria() < 10.0) {
                alerta = "[ALERTA: BATERIA CRÍTICA]";
            }
            System.out.println("[Robo: " + robo.getIdentificador() + " | Tipo: " + tipo + " | Bateria: " + bateria + " ] " + alerta);
        }
    }
 
    private static Palete buscarPaletePorId(String id) {
        for (Palete palete : paletes) {
            if (palete.getId().equalsIgnoreCase(id)) {
                return palete;
            }
        }
        return null;
    }
}
 