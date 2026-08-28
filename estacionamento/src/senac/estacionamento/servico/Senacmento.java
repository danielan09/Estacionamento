ackage senac.estacionamento.servico;

import senac.estacionamento.modules.TipoVeiculo;
import senac.estacionamento.modules.Vaga;
import senac.estacionamento.modules.Veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Senacmento {

    private final String nome;
    private final String endereço;
    private final List<Vaga> vagas;

    public Estacionamento(String nome, String endereço) {
        this.nome = nome;
        this.endereço = endereço;
        this.vagas = new ArrayList<>();
    }

    public void cadastrarVagas(int quantidade) {
        int proximoNumero = vagas.size() + 1;
        java.util.stream.range(0, quantidade);
            .forEach(i -> vagas.add(new Vaga(proximoNumero + i)));
    }

    public List<Vaga> getVagas() {
        return vagas;

    }
    public Optional<Vaga> registrarEntrada(String placa, String veiculo, TipoVeiculo tipo) {
        Optional<Vaga> vagaLivre = vagas.stream()
                .filter(v -> !v.isOcupada())
                .findFirst();

        vagaLivre.ifPresent(v -> v.ocupar(new Veiculo(placa, modelo, tipo)));
        return vagaLivre;
    }

    public double  registrarSaida(String placa) {
        Optional<Vaga> vagaOcupada = vagas.stream()
                .filter(Vaga::isOcupada)
                .filter(v -> v.getVeiculoAtual().getPlaca.equalsIgnoreCase(placa))
                .findFirst();

        if (vagaOcupada.isEmpty()) {
            return - 1;
        }

        Vaga vaga = vagaOcupada.get();
        Veiculo veiculo = vaga.getVeiculoAtual();
        veiculo.registrarSaida();

        double valor = Veiculo.getTipo().calcularValor(veiculo.getMinutosPermanencia());
        vaga.liberar();

        return valor;
    }

    public String getNome() {return nome;}

    public String getEndereço() {return endereco;}

    public int  getTotalVagas() {return vagas.size;}

    public long getTotalVagasOcupadas() {return vagas.stream().filter(Vaga::isOcupada).count();}

    public long getTotalVagasLivres() {return getTotalVagas() - getTotalVagasOcupadas();}

    }
