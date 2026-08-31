# Estacionamento

Sistema de gerenciamento de estacionamento em Java.

## Descrição
Este projeto simula um sistema de estacionamento com cadastro de vagas, entrada e saída de veículos, cálculo de valor por permanência e validações de regras de negócio.

## Tecnologias Utilizadas
- Java
- JDK
- IntelliJ IDEA
- Git
- Scanner
- Classes e estruturas da linguagem Java como List, Optional, Enum, LocalDateTime, Duration e Regex

## Funcionalidades
- Cadastro de vagas
- Listagem das vagas
- Registro de entrada de veículos
- Registro de saída de veículos
- Cálculo de valor por permanência
- Aplicação de desconto de 10% para permanência acima de 8 horas
- Período gratuito entre 10 e 20 minutos
- Validação de placa, campos vazios e limite máximo de vagas
- Menu interativo no console


## Estrutura do Projeto
````text
Estacionamento/
├── src/
│   └── senac/
│       └── estacionamento/
│           ├── main/
│           │   └── Main.java
│           ├── modules/
│           │   ├── TipoVeiculo.java
│           │   ├── Vaga.java
│           │   └── Veiculo.java
│           ├── servico/
│           │   └── Estacionamento.java
│           └── util/
│               └── LeitorConsole.java
````


## Como Executar
1. Clone o repositório
````bash
git clone https://github.com/danielan09/Estacionamento.git
````
3. Acesse a pasta do projeto
````bash
cd Estacionamento
````
5. Compile os arquivos Java
````bash
javac -d bin -sourcepath src src/senac/estacionamento/main/Main.java
````
7. Execute a aplicação
````bash
java -cp bin senac.estacionamento.main.Main
````


##Exemplo de Uso
````text
1 - Exibir informações gerais
2 - Cadastrar novas vagas
3 - Listar vagas
4 - Registrar entrada de veículo
5 - Registrar saída de veículo
0 - Sair
````
