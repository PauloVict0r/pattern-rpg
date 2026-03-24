package org.pattern.rpg;
import java.util.Scanner;
import java.io.IOException;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean jogoRodando = true;

        // Loop principal do menu
        while (jogoRodando) {
            LimparTerminal.limparTerminal();
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

                switch (opcao) {
                    case 1:
                        System.out.println("=> Opção escolhida: CONTINUAR o jogo.");
                        pausar(2000);
                        LimparTerminal.limparTerminal();
                        fimDeJogo(scanner, "pv", "mahoraga");
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

            System.out.println("        Sua escolha (1-3):       ");
            System.out.println("---------------------------------");

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

        iniciarBatalha(scanner, nomePersonagem, equipamentoEscolhido);
    }

    public static void iniciarBatalha(Scanner scanner, String nomeJogador, String equipamentoEscolhido){
        boolean emBatalha = true;

        int nivel = 1;
        int hp = 100;
        String logBatalha = "Um grupo de inimigos bloqueia seu caminho!";

        String estadoMenu = "PRINCIPAL";

        while(emBatalha){
            LimparTerminal.limparTerminal();
            System.out.println("=================================");
            System.out.println(String.format("%-25s %25s", "Nível: " + String.format("%02d", nivel), "inimigo 1 (HP: 30)"));
            System.out.println(String.format("%-25s %25s", "HP: " + hp, "Inimigo 2 (HP: 45)"));
            System.out.println(String.format("%-25s %25s", "", "Inimigo 3 (HP: 20)"));

            System.out.println("-------------------------------------------------------");
            int espacosEsquerda = Math.max(0, (55 - logBatalha.length()) / 2);
            System.out.println(" ".repeat(espacosEsquerda) + logBatalha);
            System.out.println("-------------------------------------------------------");

            if(estadoMenu.equals("PRINCIPAL")){
                System.out.println("  1. Atacar      |  3. Fugir (Sair do teste)");
                System.out.println("  2. Usar item   |");
                System.out.print("\nO que " + nomeJogador + " fará? (1-3): ");

                String escolha = scanner.nextLine();
                switch (escolha) {
                    case "1": estadoMenu = "ATACAR"; break;
                    case "2": estadoMenu = "ITEM"; break;
                    case "3": 
                        logBatalha = "Você fugiu covardemente...";
                        emBatalha = false; 
                        break;
                    default: 
                        logBatalha = "Ação inválida! Escolha de 1 a 3."; 
                        break;
                }
            }else if(estadoMenu.equals("ATACAR")){
                System.out.println("  1. Ataque Rápido  (" + equipamentoEscolhido + ")");
                System.out.println("  2. Ataque Pesado  (" + equipamentoEscolhido + ")");
                System.out.println("  3. [Voltar]  ");
                System.out.println("\n Escolha seu ataque (1-3): ");

                String escolha = scanner.nextLine();
                switch (escolha) {
                    case "1":
                        logBatalha = "Você usou Ataque Rápido. Causou 15 de dano.";
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "2":
                        logBatalha = "Você usou Ataque Pesado. Causou 25 de dano!";
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "3": estadoMenu = "PRINCIPAL"; break;
                    default: 
                        logBatalha = "Ataque inválido";
                        break;
                }
            }else if(estadoMenu.equals("ITEM")){
                System.out.println("  1. Poção de Vida Pequena");
                System.out.println("  2. Bomba de Fumaça");
                System.out.println("  3. [Voltar]");
                System.out.println("\nEscolha o item (1-3): ");

                String escolha = scanner.nextLine();
                switch (escolha) {
                    case "1":
                        logBatalha = "Você usou Poção de vida Pequena. Recuperou 20HP!";
                        hp = Math.min(100, hp + 20);
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "2":
                        logBatalha = "Você usou Bomba de Fumaça. Os inimigos foram cegados temporariamente!";
                        estadoMenu = "PRINCIPAL";
                        break;
                    case "3":
                        estadoMenu = "PRINCIPAL";
                    default:
                        logBatalha = "Item Inválido!";
                        break;
                }
            }
        }
        System.out.println("\nFim do combate inicial. Pressione [ENTER] para continuar...");
        scanner.nextLine();
        fimDeJogo(scanner, nomeJogador, equipamentoEscolhido);
    }

    public static void fimDeJogo(Scanner scanner, String nomeJogador, String equipamentoEscolhido){
        LimparTerminal.limparTerminal();
        System.out.println("=================================");
        System.out.println("            FIM DE JOGO           ");
        System.out.println("=================================");

        System.out.println(String.format("| %-15s | %-11s |", "Personagem", nomeJogador));
        System.out.println(String.format("| %-15s | %-11s |", "Equipamento", equipamentoEscolhido));
        System.out.println(String.format("| %-15s | %-11s |", "Piso Final", "100"));
        System.out.println("=================================");

        System.out.println("\n Pressione [ENTER] para retornar ao Menu Principal...");
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