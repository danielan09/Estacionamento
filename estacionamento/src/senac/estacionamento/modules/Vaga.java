package senac.estacionamento.modules;

public class Vaga {
    private final int numero;
    private boolean ocupada;
    private Veiculo veiculoAtual;

    public Vaga(int numero) {
        this.numero = numero;
        this.ocupada = false;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public Veiculo getVeiculoAtual() {
        return veiculoAtual;
    }

    public void ocupar(Veiculo veiculo) {
        this.veiculoAtual = veiculo;
        this.ocupada = true;
    }

    public void liberar() {
        this.veiculoAtual = null;
        this.ocupada = false;
    }

    @Override
    public String toString() {
        if (ocupada) {
            return String.format("Vaga %02d [OCUPADA] -> %s", numero, veiculoAtual);
        }
        return String.format("Vaga %02d [LIVRE]", numero);
    }
}
