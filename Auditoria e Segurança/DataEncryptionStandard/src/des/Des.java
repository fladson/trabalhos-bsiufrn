package des;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;

public class Des {

    String mensagem;
    String chave;
    int[] PC_1 = new int[]{
        57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26,
        18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55,
        47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45,
        37, 29, 21, 13, 5, 28, 20, 12, 4};
    int[] PC_2 = new int[]{
        14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23,
        19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30,
        40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    int[] IP = new int[]{
        58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28,
        20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24,
        16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
    int[] E = new int[]{
        32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11,
        12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22,
        23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1,};
    int[][] S1 = new int[][]{
        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};
    int[][] S2 = new int[][]{
        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    };
    int[][] S3 = new int[][]{
        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}};
    int[][] S4 = new int[][]{
        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}};
    int[][] S5 = new int[][]{
        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}};
    int[][] S6 = new int[][]{
        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}};
    int[][] S7 = new int[][]{
        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}};
    int[][] S8 = new int[][]{
        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};
    int[] P = new int[]{
        16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18,
        31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};
    int[] IP_menos_um = new int[]{
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };
    int[] deslocamentos_a_esquerda = new int[]{
        1, 1, 2, 2, 2, 2, 2, 2, 1, 2,
        2, 2, 2, 2, 2, 1};
    // Representarão os dezesseis blocos C0, D0 e Cn e Dn
    ArrayList<String> C = new ArrayList<String>();
    ArrayList<String> D = new ArrayList<String>();
    // Representarão as chaves Kns
    ArrayList<String> subChaves = new ArrayList<String>();
    // Representarão Lns e Rns
    ArrayList<String> Lns = new ArrayList<String>();
    ArrayList<String> Rns = new ArrayList<String>();

    /**
     * Construtor simples que já inicializa a mensagem e a chave.
     * @param mensagem
     * @param chave
     */
    public Des(String mensagem, String chave) {
        super();
        this.mensagem = mensagem;
        this.chave = chave;
    }

    public void decriptar() {

        String msg_encriptada_bin = hex_to_bin(mensagem);

        ArrayList<String> blocos_encriptados = dividir_msg_blocos_64_bits(msg_encriptada_bin); // dividindo codigo binario em blocos de 64bits

        Collections.reverse(subChaves); // revertendo a ordem das sub chaves

        String bloco_decriptado_hexa = "";


        for (int i = 0; i < blocos_encriptados.size(); i++) {
            String bloco = blocos_encriptados.get(i);

            // permutando os blocos pela tabela 'IP', gerando as 'mensagens_IP'
            String mensagem_IP = permutar_pela_tabela(bloco, IP);

            String L16 = mensagem_IP.substring(0, 32);
            String R16 = mensagem_IP.substring(32, 64);

            Lns.add(L16);
            Rns.add(R16);

            gerar_Lns_e_Rns_Para_Bloco();

            String R0_L0 = Rns.get(16) + Lns.get(16);

            String permutacao_ip_menos_um = permutar_pela_tabela(R0_L0, IP_menos_um);

            int inicio = 0, fim = 16;
            for (int j = 0; j < 4; j++) { // 4 iterações = 4 blocos de 16 bits = 64 bits
                // transformando 'IP_menos_1' para a base 16 (Hexadecimal) de 16 em 16bits
                bloco_decriptado_hexa += binario_to_hexa(permutacao_ip_menos_um.substring(inicio, fim));
                inicio = fim;
                fim += 16;
            }

            // limpando arrays após o processo
            Lns.clear();
            Rns.clear();
        }
        //System.out.println("Mensagem decriptada Hexa: "+bloco_decriptado_hexa);
        String mensagem_decriptada_ascii = hex_to_ascii(bloco_decriptado_hexa);
        //System.out.println("Mensagem decriptada: "+mensagem_decriptada_ascii);
        mensagem = mensagem_decriptada_ascii;
    }

    /**
     *	Principal método do sistema, faz todo o trabalho de chamar as funções de encriptação
     */
    public void encriptar() {

        // Passando a mensagem para a base 2 (Binário)
        String bin = string_to_bin(mensagem);

        // Depois de convertida para binária, temos que deixar os bits múltiplos de 64.
        bin = deixa_mensagem_multipla_64_bits(bin);

        // Com os bits múltiplos de 64, vamos dividí-los em blocos de 64bits.
        // Cada bloco de 64bits será uma entrada para o ArrayList 'blocos'
        // O método 'dividir_msg_blocos_64_bits' retorna o ArrayList já povoado com os blocos divididos
        ArrayList<String> blocos = dividir_msg_blocos_64_bits(bin);

        // Convertendo a chave para a base 2 (Binário)
        String K = string_to_bin(chave);

        // Um dos primeiros passos do algoritmo DES é conseguir a chave 'K+'
        // Para isso, devemos permutar a chave 'K' pela tabela 'PC1'
        // o método 'permutar_pela_tabela' é totalmente genérico, ou seja,
        // pode receber qualquer String como chave e qualquer vetor(int[]) como tabela de permutação
        String K_plus = permutar_pela_tabela(K, PC_1);

        // O segundo passo do algoritmo DES é gerar as chaves CNs d DNs a partir da chave K+
        // O método 'gerar_Cns_Dns' já realiza a divisão em 'C0' e 'D0' e também todo o deslocamento
        gerar_Cns_Dns(K_plus);

        // O terceiro passo do algoritmo DES, é gerar as 16 chaves Kns
        for (int i = 1; i <= 16; i++) {
            // para a obtenção das chaves Kns temos que ter a chave 'CnDn', que nada mais é que a junção de uma chave 'Cn' e 'Dn'
            // para isso concatenamos as chaves 'C(i)' e 'D(i)' correntes.
            String CnDn = C.get(i) + D.get(i);
            // com 'CnDn' em mãos, o Kn é gerado pela permutação entre 'CnDn' e a tabela PC2 fornecida
            String Kn = permutar_pela_tabela(CnDn, PC_2);
            // após a permutação, adicionamos a chave Kn ao ArrayList 'subChaves'
            // ao todo teremos 16 chaves Kn no ArrayList 'subChaves'
            subChaves.add(Kn);
        }

        String mensagem_encriptada = ""; // string auxiliar para concatenarmos os blocos encriptados
        // Nesta iteração iremos codificar cada bloco de 64 bits de dados permutando pela tabela 'IP'
        for (int i = 0; i < blocos.size(); i++) {
            // pegando blocos de 64bits do ArrayList 'blocos'
            String bloco = blocos.get(i);
            // permutando os blocos pela tabela 'IP', gerando as 'mensagens_IP'
            String mensagem_IP = permutar_pela_tabela(bloco, IP);
            // Cada bloco de 64 bits da 'mensagem_IP' é dividido em dois blocos de 32 bits,
            // um sub-bloco esquerdo 'L0' e um sub-bloco direito 'R0'
            String L0 = mensagem_IP.substring(0, 32);
            String R0 = mensagem_IP.substring(32, 64);
            // adicionando 'L0' e 'R0' nos ArrayLists correspondentes
            Lns.add(L0);
            Rns.add(R0);

            // gerar os Lns e Rns para o bloco seguindo as fórmulas específicas do algoritmo
            gerar_Lns_e_Rns_Para_Bloco();

            // outro passo do algoritmo é concatenar o R16 com o L16
            String R16_L16 = Rns.get(16) + Lns.get(16);

            // um dos últimos passos do algoritmo DES é permutar esse 'R16_L16' anterior com a tabela 'IP-1'
            String IP_menos_1 = permutar_pela_tabela(R16_L16, IP_menos_um);

            // O último passo é converter o resultado da permutação de 'R16_L16' com a tabela 'IP-1' para a base 16 (Hexadecimal)
            String bloco_encriptado_hexa = ""; // irá receber a concatenação dos blocos
            //quebramos a string 'IP_menos_1' em 4 partes de 16 porque o método 'bin_to_hexa' não aceita blocos maiores de 28 bits
            int inicio = 0, fim = 16;
            for (int j = 0; j < 4; j++) { // 4 iterações = 4 blocos de 16 bits = 64 bits
                // transformando 'IP_menos_1' para a base 16 (Hexadecimal) de 16 em 16bits
                bloco_encriptado_hexa += binario_to_hexa(IP_menos_1.substring(inicio, fim));
                inicio = fim;
                fim += 16;
            }
            // apaga todos os Lns e Rns gerados pelo bloco anterior para que o novo bloco não utilize os Lns e Rns errados
            Lns.clear();
            Rns.clear();
            // nesta primeira rodada teremos a encriptação, já na base 16, do primeiro bloco da mensagem
            mensagem_encriptada += bloco_encriptado_hexa;
        }

        mensagem = mensagem_encriptada;
    }

    /**
     * Método que recebe uma string e de acordo com a tabela passada, realiza a permutação entre os dois.
     * @param s
     * @param tabela
     * @return
     */
    public String permutar_pela_tabela(String s, int[] tabela) {
        String string_permutada = ""; // variável auxiliar para armazenar o resultado da permutação
        for (int i = 0; i < tabela.length; i++) { // percorrendo toda a tabela
            // a 'string_permutada' será o resultado dos 'cortes' de 's' a partir dos índices 'i-1' e 'i', para pegar exatamente o valor
            // Exemplo: s = 1001 0101 0101 0000 1011 1101 0011
            //          tabela = 10, 4, 21, 9, 1, 4, 3, 12
            //          i=0 - string_permutada = 10-1, 10 / indice de 's' de 9 a 10 = 0
            //          i=1 - string_permutada =  4-1,  4 / indice de 's' de 3 a 4 = 0 - 00
            //          i=2 - string_permutada = 21-1, 21 / indice de 's' de 20 a 21 = 1 - 001
            //          i=3 - string_permutada =  9-1,  9 / indice de 's' de 8 a 9 = 1 - 0011 e assim vai
            string_permutada += s.substring(tabela[i] - 1, tabela[i]);
        }
        return string_permutada;
    }

    /**
     * Método que recebe uma string e gera os Cns e Dns a serem usados no algoritmo.
     * @param s
     */
    public void gerar_Cns_Dns(String s) {
        // dividimos esta chave em duas metades, esquerda C0 e direita D0, onde cada metade tem exatos 28 bits.
        String C0 = s.substring(0, 28);

        String D0 = s.substring(28, 56);

        // adicionando as metades no ArrayList 'C' e 'D', respectivamente.
        C.add(C0);
        D.add(D0);

        for (int i = 0; i < 16; i++) { // o algoritmo DES exige que o deslocamento seja feito 16 vezes.
            // pega o valor 'i' corrente do vetor 'deslocamentos_a_esquerda'
            int deslocamento = deslocamentos_a_esquerda[i];
            // O deslocamento é feito da seguinte forma:
            // 	Primeiro é feito um corte do valor no indice 'i' do ArrayList 'C' a partir do valor de 'deslocamento' corrente até
            //	o tamanho do elemento corrente do ArrayList C.
            // 	Segundamente é feito um segundo corte novamente no valor de indice 'i' do ArrayList 'C', só que agora
            //	a partir de 0 até o valor do delocamento corrente.
            String Cn = C.get(i).substring(deslocamento, C.get(i).length()) + C.get(i).substring(0, deslocamento);
            //System.out.println("C" + (i + 1) + ": " + Cn);
            // da mesma forma que o deslocamento acima, só que desta vez, buscando valores do ArrayList 'D'
            String Dn = D.get(i).substring(deslocamento, D.get(i).length()) + D.get(i).substring(0, deslocamento);
            //System.out.println("D" + (i + 1) + ": " + Dn);
            // Adicionando os resultados dos deslocamentos de Cn e Dn aos ArrayLists 'C' e 'D'
            // Ao total, 'C' e 'D' terá 16 chaves de deslocamento cada mais o C0 e D0.
            C.add(Cn);
            D.add(Dn);
        }
    }

    /**
     * Gera os Lns e Rns de cada bloco de 32bits a partir das fórmulas fornecidas pelo algoritmo
     */
    public void gerar_Lns_e_Rns_Para_Bloco() {
        for (int i = 0; i < 16; i++) {
            // Ln = Rn-1
            String Ln = Rns.get(i);
            // Rn = Ln-1 + f(Rn - 1, kn);
            String Rn = XOR(Lns.get(i), funcao_f(Rns.get(i), subChaves.get(i)));
            Lns.add(Ln);
            Rns.add(Rn);
        }
    }

    public void gerar_Lns_e_Rns_Para_Bloco_2() {
        for (int i = 16; i < 0; i++) {
            // Ln = Rn-1
            String Ln = Rns.get(i);
            // Rn = Ln-1 + f(Rn - 1, kn);
            String Rn = XOR(Lns.get(i), funcao_f(Rns.get(i), subChaves.get(i)));
            Lns.add(Ln);
            Rns.add(Rn);
        }
    }

    public String XOR(String s1, String s2) {
        String resultado = "";
        for (int i = 0; i < s1.length(); i++) {
            resultado += Integer.parseInt(s1.charAt(i) + "") ^ Integer.parseInt(s2.charAt(i) + "");
        }
        return resultado;
    }

    /**
     * a 'funcao_f' é chamada apenas pelo método 'gerar_Lns_e_Rns_Para_Bloco'
     * @param Rn
     * @param K
     * @return
     */
    public String funcao_f(String Rn, String K) {
        String E_Rn = permutar_pela_tabela(Rn, E);
        String K_E_Rn = XOR(E_Rn, K);
        // Dividindo em 8 grupos de 6 bits cada para aplicarmos os S boxes
        int inicio = 0;
        int fim = 6;
        // String para concatenar os 8 blocos de 4bits resultantes da funcao_s
        String resultado_funcao_s = "";
        for (int i = 0; i < 8; i++) {
            resultado_funcao_s += funcao_s(K_E_Rn.substring(inicio, fim), get_matriz_s(i));
            inicio = fim;
            fim += 6;
        }
        return permutar_pela_tabela(resultado_funcao_s, P);
    }

    /**
     * Representa a permutação de um bloco de 6 bits e uma matriz S especificada.
     * Essa permutação será feita da seguinte forma:
     * 	.o primeiro e último bits do bloco serão convertidos para um número na base 10 (Decimal) que representará a linha da matriz_s passada
     *  .os quatro bits do 'meio' (do segundo ao quinto) também serão convertidos para um número da base 10 (Decimal) que representará a coluna da matriz_s
     * Com esses dois números em mãos, pegamos o valor na 'matriz_s' que está nesta 'linha' e 'coluna' obtidas.
     * A 'funcao_s' é chamada exclusivamente pela 'funcao_f'.
     * @param bloco
     * @param matriz_s
     * @return
     */
    public String funcao_s(String bloco, int[][] matriz_s) {
        String linha = bloco.substring(0, 1) + bloco.substring(5, 6);
        String coluna = bloco.substring(1, 5);
        int indice = matriz_s[bin_to_decimal(linha)][bin_to_decimal(coluna)];
        return decimal_to_bin(indice);
    }

    /**
     * Recebe a mensagem binária, múltipla de 64 e a divide em blocos de 64bits.
     * Retornando um ArrayList de String, 'blocos', com todos os blocos já inseridos.
     * @param msg
     * @return blocos
     */
    private ArrayList<String> dividir_msg_blocos_64_bits(String msg) {
        ArrayList<String> blocos = new ArrayList<String>();
        int inicio = 0, fim = 64; // variáveis auxiliares para fazer os 'cortes' na msg, sempre de 64 em 64
        while (fim <= msg.length()) {
            blocos.add(msg.substring(inicio, fim)); // utilizamos o método 'substring' para fazer o corte entre os índices (inclusivos) 'inicio' e 'fim'
            inicio = fim;
            fim += 64;
        }
        return blocos;
    }

    /**
     * Método que testa se a mensagem é multipla de 64 bits,
     * caso não seja deixa a mesma múltipla adicionando blocos de 8 zeros no final.
     *
     * @param msg
     * @return msg
     */
    public String deixa_mensagem_multipla_64_bits(String msg) {
        while (msg.length() % 64 != 0) {
            msg += "00000000";
        }
        return msg;
    }

    /**
     * Método que recebe uma string e a converte para a base 2 (Binário)
     *
     * @param string
     * @return bin
     */
    public String string_to_bin(String string) {
        String bin = ""; // variável vazia para armazenar os blocos binários
        for (int i = 0; i < string.length(); i++) { // for percorrendo toda a string
            // Convertendo cada caractere de string para o seu binário equivalente
            String b = Integer.toBinaryString((int) string.charAt(i));
            // Como o método descar os 0 a esquerda, aqui concatena-se os 0 descartados pelo método
            while (b.length() % 8 != 0) {
                b = "0" + b;
            }
            // Como o método é caractere por caractere, no final temos que ir concatenando o resultado
            // No final temos toda a string convertida para binário
            bin += b;
        }
        return bin;
    }

    /**
     * Recebe uma String na base 2 (Binário) e a transforma para a base 10 (Decimal)
     * @param bin
     * @return
     */
    public int bin_to_decimal(String bin) {
        return Integer.parseInt(bin, 2);
    }

    /**
     * Recebe um inteiro na base 10 (Decimal) e o transforma para a base 2 (Binário)
     * @param dec
     * @return
     */
    public String decimal_to_bin(int dec) {
        String bin = Integer.toString(dec, 2);
        while (bin.length() % 4 != 0) {
            bin = "0" + bin;
        }
        return bin;
    }

    /**
     * Get das matrizes 'S'.
     * Recebe um inteiro para representar qual matriz 'S' você deseja.
     * 1 para a matriz 'S1', 2 para a matriz 'S2' e assim por diante.
     * @param i
     * @return
     */
    public int[][] get_matriz_s(int i) {
        switch (i) {
            case 0:
                return S1;
            case 1:
                return S2;
            case 2:
                return S3;
            case 3:
                return S4;
            case 4:
                return S5;
            case 5:
                return S6;
            case 6:
                return S7;
            case 7:
                return S8;
            default:
                return null;
        }
    }

    /**
     * Recebe uma String na base 2 (Binário) e a transforma para a base 16 (Hexadecimal)
     * @param bin
     * @return
     */
    public String binario_to_hexa(String bin) {
        // MUDEI AQUI
        String resultado = "";
        for (int i = 0; i < bin.length(); i+= 8) {
            String aux = Integer.toHexString(bin_to_decimal(bin.substring(i, i + 8)));
            if(aux.length() % 2 != 0){
                aux = "0" + aux;
            }
            resultado += aux;
        }
        return resultado;
        // ANTES
        //return Integer.toHexString(bin_to_decimal(bin)) + "";
    }

    /**
     * ToString que informa o número do processo e a mensagem e chave informadas pelo usuário, apenas para visualização dos dados.
     * @param Contador
     * @return
     */
    public String toString(int Contador) {
        return "\nProcesso de Número: " + Contador + "\nMensagem: "
                + this.mensagem + "\nChave: " + this.chave;
    }

    /**
    private void formataMensagem() {
    mensagem += "\r\n";
    }
     */
    private String string_to_hexa(String s) {
        String hex = "";
        for (int w = 0; w < s.length(); w++) {
            String aux = Integer.toHexString(s.charAt(w));
            if (aux.length() == 1) {
                aux = "0" + aux;
            }
            hex += aux;
        }
        /**
         * hex += "0D0A"; int l = s.length() + 2;
         */
        while (hex.length() % 16 != 0) {
            hex += "00";
        }
        return hex;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String hex_to_ascii(String hex) {
        String ascii = "";
        int tam = hex.length();
        System.out.println(tam);
        for (int i = 0; i < hex.length(); i += 2) {
            int valor = Integer.parseInt(hex.substring(i, i + 2), 16);
            char c = (char) valor;
            ascii += c;
        }

        return ascii;
    }


    /*
    public static String hex_to_bin(String hex) {
        String bin = "";
        for(int i = 0; i < hex.length(); i++){
            String aux = Integer.toBinaryString(Character.digit(hex.charAt(i),16));
            while(aux.length() % 4 !=  0){
                aux = "0" + aux;
            }
            bin += aux;
        }
        return bin; // colocando a msg_encriptada na base 2 (Binário)
    }
     * 
     */

   
    public String hex_to_bin(String hex) {
        String msg2 = hex_to_ascii(hex);
        return string_to_bin(msg2); // colocando a msg_encriptada na base 2 (Binário)
    }
}
