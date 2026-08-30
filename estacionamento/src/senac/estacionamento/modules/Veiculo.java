package senac.estacionamento.modules;

import java.time.Duration;
import java.time.LocalDateTime;

public class Veiculo {

    private final String placa;
    private final String modelo;
    private final TipoVeiculo tipo;
    private final LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;

    public Veiculo(String placa, String modelo, TipoVeiculo tipo) {
        this.placa = placa.toUpperCase();
        this.modelo = modelo;
        this.tipo = tipo;
        this.horaEntrada = LocalDateTime.now();
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSaida() {
        return horaSaida;
    }

    public void registrarSaida() {
        this.horaSaida = LocalDateTime.now();
    }

    public long getMinutosPermanencia() {
        LocalDateTime fim = (horaSaida != null) ? horaSaida : LocalDateTime.now();
        return Duration.between(horaEntrada, fim).toMinutes();
    }

    @Override
    public String toString() {
        return String.format("Placa: %s | Modelo: %s | Tipo: %s | Entrada: %s",
                placa, modelo, tipo.getDescricao(), horaEntrada);
    }
}