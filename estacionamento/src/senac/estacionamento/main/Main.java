package senac.estacionamento.main;

import senac.estacionamento.modules.TipoVeiculo;
import senac.estacionamento.modules.Vaga;
import senac.estacionamento.servico.Estacionamento;
import senac.estacionamento.util.LeitorConsole;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LeitorConsole leitor = new LeitorConsole(scanner);

        Estacionamento estacionamento = new Estacionamento(
                "Estacionamento Senac",
                "Rua do Pombal, 57 - Santo Amaro, Recife/PE"
        );

        estacionamento.cadastrarVagas(10);
        executarMenu(leitor, estacionamento, scanner);
    }

    private static void executarMenu(LeitorConsole leitor, Estacionamento estacionamento, Scanner scanner) {
        exibirMenu();
        int opcao = leitor.lerInteiro("Escolha uma opção: ");

        switch (opcao) {
            case 1 -> estacionamento.exibirInformacoesGerais();
            case 2 -> cadastrarNovaVaga(leitor, estacionamento);
            case 3 -> estacionamento.listarVagas();
            case 4 -> registrarEntrada(leitor, estacionamento);
            case 5 -> registrarSaida(leitor, estacionamento);
            case 0 -> {
                System.out.println("Encerrando o sistema, volte logo!");
                scanner.close();
                return;
            }
            default -> System.out.println("Opção inválida, por favor, tente novamente.");
        }

        System.out.println();
        executarMenu(leitor, estacionamento, scanner);
    }

    private static void exibirMenu() {
        System.out.println("========= SISTEMA DE ESTACIONAMENTO =========");
        System.out.println("1 - Exibir informações gerais");
        System.out.println("2 - Cadastrar novas vagas");
        System.out.println("3 - Listar vagas");
        System.out.println("4 - Registrar entrada de veículo");
        System.out.println("5 - Registrar saída de veículo");
        System.out.println("0 - Sair");
        System.out.println("===============================================");
    }

    private static void cadastrarNovaVaga(LeitorConsole leitor, Estacionamento estacionamento) {
        int quantidade = leitor.lerInteiro("Quantidade de vagas a cadastrar: ");

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida. Digite um valor maior que zero.");
            return;
        }

        if (estacionamento.getTotalVagas() + quantidade > 50) {
            System.out.println("Limite máximo de vagas excedido. O estacionamento aceita até 50 vagas.");
            return;
        }

        estacionamento.cadastrarVagas(quantidade);
        System.out.println("Vagas cadastradas com sucesso!");
    }

    private static void registrarEntrada(LeitorConsole leitor, Estacionamento estacionamento) {
        String placa = leitor.lerTexto("Placa do veículo: ");
        if (!placaValida(placa)) {
            System.out.println("Placa inválida. A placa deve ter exatamente 7 caracteres alfanuméricos, sem espaços ou caracteres especiais.");
            return;
        }

        String modelo = leitor.lerTexto("Modelo do veículo: ");
        if (modelo.trim().isEmpty()) {
            System.out.println("Modelo inválido. O modelo não pode ficar em branco.");
            return;
        }

        TipoVeiculo tipo = escolherTipoVeiculo(leitor);

        if (tipo == null) {
            System.out.println("Tipo de veículo inválido. Operação cancelada.");
            return;
        }

        Optional<Vaga> vaga = estacionamento.registrarEntrada(placa, modelo, tipo);
        if (vaga.isPresent()) {
            System.out.println("Veículo cadastrado com sucesso na vaga " + vaga.get().getNumero() + "!");
        } else {
            System.out.println("Não há vagas disponíveis no momento.");
        }
    }

    private static void registrarSaida(LeitorConsole leitor, Estacionamento estacionamento) {
        String placa = leitor.lerTexto("Placa do veículo: ");
        if (!placaValida(placa)) {
            System.out.println("Placa inválida. A placa deve ter exatamente 7 caracteres alfanuméricos.");
            return;
        }

        double horasPermanencia = leitor.lerDouble("Quantas horas o veículo ficou estacionado? ");
        if (horasPermanencia <= 0) {
            System.out.println("A quantidade de horas deve ser maior que zero.");
            return;
        }

        double valor = estacionamento.registrarSaida(placa, horasPermanencia);

        if (valor < 0) {
            System.out.println("❌ Nenhum veículo com essa placa foi encontrado no estacionamento.");
        }
    }

    private static boolean placaValida(String placa) {
        if (placa == null) {
            return false;
        }

        String placaNormalizada = placa.trim().replaceAll("\\s+", "").toUpperCase();
        return !placaNormalizada.isEmpty() && placaNormalizada.length() == 7 && placaNormalizada.matches("[A-Z0-9]+");
    }

    private static TipoVeiculo escolherTipoVeiculo(LeitorConsole leitor) {
        System.out.println("Tipo do veículo:");
        TipoVeiculo[] tipos = TipoVeiculo.values();
        java.util.stream.IntStream.rangeClosed(1, tipos.length)
                .forEach(i -> System.out.println(i + " - " + tipos[i - 1].getDescricao()));

        int opcao = leitor.lerInteiro("Opção: ");

        if (opcao < 1 || opcao > tipos.length) {
            return null;
        }

        return tipos[opcao - 1];
    }
}
