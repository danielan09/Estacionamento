package senac.estacionamento.servico;

import senac.estacionamento.modules.TipoVeiculo;
import senac.estacionamento.modules.Vaga;
import senac.estacionamento.modules.Veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Estacionamento {

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

        int proximoNumero = vagas.size() + 1;
        IntStream.range(0, quantidade)
                .forEach(i -> vagas.add(new Vaga(proximoNumero + i)));
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public Optional<Vaga> registrarEntrada(String placa, String modelo, TipoVeiculo tipo) {
        Optional<Vaga> vagaLivre = vagas.stream()
                .filter(v -> !v.isOcupada())
                .findFirst();

        vagaLivre.ifPresent(v -> v.ocupar(new Veiculo(placa, modelo, tipo)));
        return vagaLivre;
    }

    public double registrarSaida(String placa) {
        Optional<Vaga> vagaOcupada = vagas.stream()
                .filter(Vaga::isOcupada)
                .filter(v -> v.getVeiculoAtual() != null && v.getVeiculoAtual().getPlaca().equalsIgnoreCase(placa))
                .findFirst();

        if (vagaOcupada.isEmpty()) {
            return -1;
        }

        Vaga vaga = vagaOcupada.get();
        Veiculo veiculo = vaga.getVeiculoAtual();
        veiculo.registrarSaida();

        double valor = veiculo.getTipo().calcularValor(veiculo.getMinutosPermanencia());
        vaga.liberar();

        return valor;
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
        System.out.println("========================================");
        System.out.println(" ESTACIONAMENTO: " + nome);
        System.out.println(" Endereço: " + endereco);
        System.out.println("----------------------------------------");
        System.out.println(" Total de Vagas..... " + getTotalVagas());
        System.out.println(" Vagas Ocupadas....... " + getTotalVagasOcupadas());
        System.out.println(" Vagas Livres........... " + getTotalVagasLivres());
        System.out.println("----------------------------------------");
        System.out.println(" Tabelas de Preços:");
        java.util.Arrays.stream(TipoVeiculo.values()).forEach(tipo ->
                System.out.printf(" %-12s 1a hora: R$ %.2f | hora adicional: R$ %.2f%n",
                        tipo.getDescricao(), tipo.getValorPrimeiraHora(), tipo.getValorHoraAdicional()));
        System.out.println("========================================");
    }

    public void listarVagas() {
        if (vagas.isEmpty()) {
            System.out.println("Nenhuma vaga cadastrada ainda.");
            return;
        }

        vagas.forEach(System.out::println);
    }
}
