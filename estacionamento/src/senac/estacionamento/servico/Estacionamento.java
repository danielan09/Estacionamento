package senac.estacionamento.servico;

import senac.estacionamento.modules.TipoVeiculo;
import senac.estacionamento.modules.Vaga;
import senac.estacionamento.modules.Veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Estacionamento {

    private static final int MAX_VAGAS = 50;

    private final String nome;
    private final String endereco;
    private final List<Vaga> vagas;

    public Estacionamento(String nome, String endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.vagas = new ArrayList<>();
    }

    public void cadastrarVagas(int quantidade) {
        if (quantidade <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        int vagasDisponiveis = MAX_VAGAS - vagas.size();
        if (quantidade > vagasDisponiveis) {
            System.out.printf("Quantidade inválida. O estacionamento suporta no máximo %d vagas. Você tentou cadastrar %d. %n",
                    MAX_VAGAS, quantidade);
            return;
        }

        int proximoNumero = vagas.size() + 1;
        IntStream.range(0, quantidade)
                .forEach(i -> vagas.add(new Vaga(proximoNumero + i)));
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public Optional<Vaga> registrarEntrada(String placa, String modelo, TipoVeiculo tipo) {
        if (placa == null || placa.trim().isEmpty()) {
            return Optional.empty();
        }

        if (modelo == null || modelo.trim().isEmpty()) {
            return Optional.empty();
        }

        String placaNormalizada = placa.trim().replaceAll("\\s+", "").toUpperCase();
        if (placaNormalizada.length() != 7 || !placaNormalizada.matches("[A-Z0-9]+")) {
            return Optional.empty();
        }

        Optional<Vaga> vagaLivre = vagas.stream()
                .filter(v -> !v.isOcupada())
                .findFirst();

        vagaLivre.ifPresent(v -> v.ocupar(new Veiculo(placaNormalizada, modelo.trim(), tipo)));
        return vagaLivre;
    }

    public double registrarSaida(String placa, double horasPermanencia) {
        if (horasPermanencia <= 0) {
            return -1;
        }

        Optional<Vaga> vagaOcupada = vagas.stream()
                .filter(Vaga::isOcupada)
                .filter(v -> v.getVeiculoAtual() != null && v.getVeiculoAtual().getPlaca().equalsIgnoreCase(placa))
                .findFirst();

        if (vagaOcupada.isEmpty()) {
            return -1;
        }

        Vaga vaga = vagaOcupada.get();
        Veiculo veiculo = vaga.getVeiculoAtual();
        long minutosPermanencia = Math.round(horasPermanencia * 60);

        double valor = veiculo.getTipo().calcularValor(minutosPermanencia);
        double valorComDesconto = valor;
        
        if (horasPermanencia > 8) {
            valorComDesconto = valor * 0.90;
        }

        veiculo.registrarSaida();
        vaga.liberar();

        System.out.println("\n========================================");
        System.out.println("     RECIBO DE SAÍDA - ESTACIONAMENTO");
        System.out.println("========================================");
        System.out.println("Placa do Veículo: " + veiculo.getPlaca());
        System.out.println("Modelo: " + veiculo.getModelo());
        System.out.println("Tipo: " + veiculo.getTipo().getDescricao());
        System.out.println("Permanência: " + horasPermanencia + " horas");
        System.out.println("----------------------------------------");
        System.out.println("Valor Inicial: R$ " + String.format("%.2f", valor));
        
        if (horasPermanencia > 8) {
            System.out.println("DESCONTO de 10% (permanência > 8h)");
            System.out.println("Desconto: -R$ " + String.format("%.2f", valor - valorComDesconto));
        } else {
            System.out.println("Nenhum desconto aplicável");
        }
        
        System.out.println("----------------------------------------");
        System.out.println("VALOR FINAL: R$ " + String.format("%.2f", valorComDesconto));
        System.out.println("========================================\n");

        return Math.round(valorComDesconto * 100.0) / 100.0;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getTotalVagas() {
        return vagas.size();
    }

    public long getTotalVagasOcupadas() {
        return vagas.stream().filter(Vaga::isOcupada).count();
    }

    public long getTotalVagasLivres() {
        return getTotalVagas() - getTotalVagasOcupadas();
    }

    public void exibirInformacoesGerais() {
        System.out.println("\n========================================");
        System.out.println(" ESTACIONAMENTO: " + nome);
        System.out.println(" Endereço: " + endereco);
        System.out.println("----------------------------------------");
        System.out.println(" Total de Vagas..... " + getTotalVagas());
        System.out.println(" Vagas Ocupadas....... " + getTotalVagasOcupadas());
        System.out.println(" Vagas Livres........... " + getTotalVagasLivres());
        System.out.println("----------------------------------------");
        System.out.println(" TABELA DE PREÇOS:");
        System.out.println("----------------------------------------");
        java.util.Arrays.stream(TipoVeiculo.values()).forEach(tipo ->
                System.out.printf(" %-15s | 1ª hora: R$ %6.2f | hora adic: X %6.2f%n",
                        tipo.getDescricao(), tipo.getValorPrimeiraHora(), tipo.getValorHoraAdicional()));
        System.out.println("----------------------------------------");
        System.out.println(" Primeiros 20 minutos: GRATUITO");
        System.out.println(" Permanência > 8 horas: DESCONTO 10%");
        System.out.println("========================================\n");
    }

    public void listarVagas() {
        if (vagas.isEmpty()) {
            System.out.println("Nenhuma vaga cadastrada ainda.");
            return;
        }

        vagas.forEach(System.out::println);
    }
}
