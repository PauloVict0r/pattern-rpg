package org.pattern.rpg;
import java.util.Scanner;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        LimparTerminal.limparTerminal();
        // O Scanner nos permite capturar o que o usuário digita no terminal
        Scanner scanner = new Scanner(System.in);
        boolean jogoRodando = true;

        // Loop principal do menu
        while (jogoRodando) {

            System.out.println("=================================");
            System.out.println("      BEM-VINDO VIAJANTE!       ");
            System.out.println("=================================");

            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Continuar");
            System.out.println("2. Novo Jogo");
            System.out.println("3. Scores");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção (1-4): ");

            // Lê a entrada do usuário como texto e tenta converter para um número
            String entrada = scanner.nextLine();

            try {
                int opcao = Integer.parseInt(entrada);

                // Estrutura de decisão para cada botão escolhido
                switch (opcao) {
                    case 1:
                        System.out.println("=> Opção escolhida: CONTINUAR o jogo.");
                        pausar(2000);
                        LimparTerminal.limparTerminal();
                        // Aqui entrará a lógica para carregar o save futuramente
                        break;
                    case 2:
                        System.out.println("=> Opção escolhida: Iniciar NOVO JOGO.");
                        // Aqui entrará a lógica de criação de personagem
                        novoJogo(scanner);
                        break;
                    case 3:
                        System.out.println("=> Opção escolhida: Visualizar SCORES.");
                        // Aqui você mostrará as pontuações
                        mostrarScores(scanner);
                        break;
                    case 4:
                        System.out.println("=> Opção escolhida: SAIR. Até a próxima aventura!");
                        pausar(1000);
                        LimparTerminal.limparTerminal();
                        jogoRodando = false; // Isso quebra o loop e encerra o programa
                        break;
                    default:
                        // Trata números que não estão entre 1 e 4
                        System.out.println("=> Opção inválida! Por favor, digite um número de 1 a 4.");
                        pausar(2000);
                        LimparTerminal.limparTerminal();
                        break;
                }
            } catch (NumberFormatException e) {
                // Trata o erro caso o jogador digite uma letra ou símbolo em vez de um número
                System.out.println("=> Entrada inválida! Digite apenas números.");
            }
        }

        // Boa prática: fechar o scanner ao final do programa
        scanner.close();
    }

    public static void novoJogo(Scanner scanner){
        LimparTerminal.limparTerminal();
        System.out.println("=================================");
        System.out.println("            NOVO JOGO            ");
        System.out.println("=================================");

        System.out.println("Digite o nome do seu Personagem: ");
        String nomePersonagem = scanner.nextLine();

        System.out.println("\nBem vindo(a), " + nomePersonagem + "!");
        pausar(1500);

        String[] equipamentos = {
            "Faca cega",
            "Arco improvisado",
            "Armadura em frangalhos",
            "Cajado velho",
            "Porrete rachado",
            "Cabo de espada",
            "Katana de madeira",
            "Cabo de lança",
            "Espada de Oito Empunhaduras Divergente Sila Divina General Mahoraga"
        };

        Random random = new Random();
        int item1 = random.nextInt(equipamentos.length);
        int item2 = random.nextInt(equipamentos.length);
        while(item1 == item2){
            item2 = random.nextInt(equipamentos.length);
        }
        int item3 = random.nextInt(equipamentos.length);
        while (item3 == item1 || item3 == item2) {
            item3 = random.nextInt(equipamentos.length);
        }

        boolean escolhendo = true;
        String equipamentoEscolhido = "";

        while (escolhendo) {
            LimparTerminal.limparTerminal();
            System.out.println("=================================");
            System.out.println("         ARSENAL INICIAL         ");
            System.out.println("=================================");
            //System.out.println("\nSua escolha (1-3): ");

            System.out.println("        Sua escolha (1-3):       ");
            System.out.println("---------------------------------");

            //System.out.println(String.format("| %-15s |", equipamentos[item1]));
            //System.out.println(String.format("| %-15s |", equipamentos[item2]));
            //System.out.println(String.format("| %-15s |", equipamentos[item3]));
            espacar(31, equipamentos[item1]);
            espacar(31, equipamentos[item2]);
            espacar(31, equipamentos[item3]);
            System.out.println("---------------------------------");

            String entrada = scanner.nextLine();

            try{
                int escolha = Integer.parseInt(entrada);
                if(escolha == 1){
                    equipamentoEscolhido = equipamentos[item1];
                    escolhendo = false;
                } else if (escolha == 2){
                    equipamentoEscolhido = equipamentos[item2];
                    escolhendo = false;
                } else if (escolha == 3){
                    equipamentoEscolhido = equipamentos[item3];
                    escolhendo = false;
                }else{
                    System.out.println("Escolha inválida. Os deuses exigem um número entre 1 e 3.");
                    pausar(2000);
                }
            }catch(NumberFormatException e){
                System.out.println("=> Entrada inválida. Digite apenas números.");
                pausar(2000);
            }
        }

        LimparTerminal.limparTerminal();
        System.out.println("=================================");
        System.out.println("Você empunhou: " + equipamentoEscolhido);
        System.out.println("A masmorra aguarda, " + nomePersonagem);
        System.out.println("=================================");
        System.out.println("\nPressione [ENTER] para iniciar a descida...");
        scanner.nextLine();
        LimparTerminal.limparTerminal();
    }

    public static void mostrarScores(Scanner scanner){
        LimparTerminal.limparTerminal();
        System.out.println("=================================");
        System.out.println("            HIGHSCORES           ");
        System.out.println("=================================");

        System.out.println(String.format("| %-15s | %-11s |", "Personagem", "Último Piso"));
        System.out.println("---------------------------------");

        System.out.println(String.format("| %-15s | %-11s |", "Gandalf", "15"));
        System.out.println(String.format("| %-15s | %-11s |", "Arthur", "100"));
        System.out.println(String.format("| %-15s | %-11s |", "Gandalf", "11"));
        System.out.println("---------------------------------");

        System.out.println("\n Pressione [ENTER] para retornar ao Menu Principal...");
        scanner.nextLine();
        LimparTerminal.limparTerminal();
    }

    public static void pausar(int milissegundos){
        try{
            Thread.sleep(milissegundos);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public static void espacar(int largura, String string){
        if(string.length() >= largura){
            System.out.println("|" + string + "|");
        }else{
            int espacosEsq = (largura - string.length())/2;
            String resultado = String.format("%" + (espacosEsq + string.length()) + "s", string);
            resultado = String.format("|" + "%-" + largura + "s" + "|", resultado);
            System.out.println(resultado);
        }
    }

    public class LimparTerminal{
        public static void limparTerminal(){
            try{
                String sistema = System.getProperty("os.name").toLowerCase();
                if(sistema.contains("win")){
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                }else{
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                }
            }catch(IOException | InterruptedException e){
                System.out.println("Erro ao limpar o console: " + e.getMessage());
            }
        }
    }
}