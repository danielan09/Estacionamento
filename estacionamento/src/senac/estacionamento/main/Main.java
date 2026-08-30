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
        estacionamento.cadastrarVagas(quantidade);
        System.out.println("Vagas cadastradas com sucesso!");
    }

    private static void registrarEntrada(LeitorConsole leitor, Estacionamento estacionamento) {
        String placa = leitor.lerTexto("Placa do veículo: ");
        String modelo = leitor.lerTexto("Modelo do veículo: ");
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
        double valor = estacionamento.registrarSaida(placa);

        if (valor < 0) {
            System.out.println("Nenhum veículo com essa placa foi encontrado no estacionamento.");
        } else {
            System.out.printf("Saída registrada! Valor a pagar: R$ %.2f%n", valor);
        }
    }

    private static TipoVeiculo escolherTipoVeiculo(LeitorConsole leitor) {
        System.out.println("Tipo do veículo:");
        TipoVeiculo[] tipos = TipoVeiculo.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + " - " + tipos[i].getDescricao());
        }

        int opcao = leitor.lerInteiro("Opção: ");

        if (opcao < 1 || opcao > tipos.length) {
            return null;
        }

        return tipos[opcao - 1];
    }
}
