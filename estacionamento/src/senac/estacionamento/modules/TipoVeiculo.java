package senac.estacionamento.modules;


public enum TipoVeiculo {

    CARRO("Carro", 5.00, 2.00),
    MOTO("Moto", 10.00, 3.00),
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
            return valorPrimeiraHora;
        }
        if (minutosPermanencia <= 60) {
            return valorPrimeiraHora;
        }
        long minutosExcedentes = minutosPermanencia - 60;
        long horasAdicionais = (long) Math.ceil(minutosExcedentes/ 60.0);

        return valorPrimeiraHora + (horasAdicionais * valorHoraAdicional);
    }
}
