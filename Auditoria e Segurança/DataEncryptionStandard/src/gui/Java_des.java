package gui;

import java.util.Scanner;

import des.Des;

public class Java_des {
	public static int CONTADOR_MENSAGENS;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean novo = true;
		while (novo) {
			welcome();
			Scanner teclado = new Scanner(System.in);
			String mensagem;
			do {
				System.out.println("Insira a mensagem a ser criptografada: ");
				// nextLine para pegar mais de uma palavra
				mensagem = teclado.nextLine();
			} while (!valida_mensagem(mensagem));

			System.out
					.println("Insira a chave\n(Atenção, serão válidos somente os 8 primeiros caracteres): ");
			String chave = teclado.nextLine() + "00000000";
			String key = "" + chave.subSequence(0, 8);

			Des d = new Des(mensagem, key);
			CONTADOR_MENSAGENS++;

			System.out.println(d.toString(CONTADOR_MENSAGENS));
			
			System.out.println("Iniciando processo de Encriptação...");
			
			d.encriptar();
			
			System.out.println("\nMensagem Encriptada: " + d.getMensagem());
			
			System.out.println("\nProcesso de Encriptação finalizado.\n");
			String opcao;
			do {
				System.out.println("\nEscolha entre as opções abaixo: ");
				System.out.println("1. Decriptar mensagem");
				System.out.println("2. Novo processo");
				System.out.println("3. Sair");
				opcao = teclado.nextLine();
			} while (!valida_opcao(opcao));
			int op = Integer.parseInt(opcao);
			switch (op) {
			case 1:
				System.out.println("Decriptando mensagem...");
				d.decriptar();
				System.out.println("\nMensagem Decriptada: " + d.getMensagem());
				novo = false;
				break;
			case 2:
				System.out.println("Novo processo...");
				novo = true;
				break;
			case 3:
				novo = false;
				System.out.println("Processo encerrado.");
				System.exit(0);				
				break;
			default:
				break;
			}		
		}
	}

	public static void welcome() {
		System.out
				.println("=====================================================");
		System.out
				.println("|                                                   |");
		System.out
				.println("|         _DATA ENCRYPTION STANDARD EM JAVA_        |");
		System.out
				.println("|                                                   |");
		System.out
				.println("=====================================================");
		System.out
				.println("                                       Cícero|Fladson\n");
	}

	public static boolean valida_mensagem(String msg) {
		if (msg.trim().isEmpty()) {
			System.out
					.println("Você inseriu uma mensagem vazia. Insira uma mensagem válida.");
			return false;
		}
		return true;
	}

	public static boolean valida_opcao(String opcao) {
		if (opcao.trim().isEmpty()) {
			return false;
		} else if (opcao.trim().equals("1") || opcao.trim().equals("2")
				|| opcao.trim().equals("3")) {
			return true;
		}
		return false;
	}

}
