package senac.estacionamento.modules;


public enum TipoVeiculo {

    CARRO("Moto", 5.00, 2.00),
    MOTO("Carro", 10.00, 3.00),
    CAMINHONETE("Caminhonete", 15.00, 5.00);

    private final String descricao;
    private final double valorPrimeiraHora;
    private final double valorHoraAdicional;

    TipoVeiculo(String descricao, double valorPrimeiraHora, double valorHoraAdicional) {
        this.descricao = descricao;
        this.valorPrimeiraHora = valorPrimeiraHora;
        this.valorHoraAdicional = valorHoraAdicional;
    }

    public String getDescricao() {return descricao;}

    public double getValorPrimeiraHora() {return valorPrimeiraHora;}

    public double getValorHoraAdicional() {return valorHoraAdicional;}

    public double calcularValor(long minutosPermanencia){
        if (minutosPermanencia <= 0) {
            return 0.0;
        }

        if (minutosPermanencia >= 10 && minutosPermanencia <= 20) {
            return 0.0;
        }

        long minutosCobrançados = Math.max(0, minutosPermanencia - 20);
        long horasCobrançadas = (long) Math.ceil(minutosCobrançados / 60.0);

        if (horasCobrançadas <= 0) {
            return 0.0;
        }

        return valorPrimeiraHora * horasCobrançadas;
    }
}
