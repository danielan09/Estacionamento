package senac.estacionamento.util;

import java.util.Scanner;

public class LeitorConsole {
    private final Scanner scanner;

    public LeitorConsole(Scanner scanner) {
        this.scanner = scanner;
    }

    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        String valor = scanner.nextLine();

        if (valor == null || valor.trim().isEmpty()) {
            System.out.println("Este campo não pode ficar em branco.");
            return lerTexto(mensagem);
        }

        return valor.trim();
    }

    public int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        String valor = scanner.nextLine();

        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Digite um número inteiro.");
            return lerInteiro(mensagem);
        }
    }

    public double lerDouble(String mensagem) {
        System.out.print(mensagem);
        String valor = scanner.nextLine();

        try {
            return Double.parseDouble(valor.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Digite um número válido.");
            return lerDouble(mensagem);
        }
    }
}
