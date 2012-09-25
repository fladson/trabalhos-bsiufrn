package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Fladson
 */
public class Rsa {

    BigInteger p, q, n, fi, e, d;
    String mensagem;
    ArrayList<String> array_blocos_encriptados;

    /**
     * Construtor que recebe 'p' 'q' e a 'mensagem' como parametros e já faz o cálculo de 'N' e 'FI'
     * @param p
     * @param q
     * @param mensagem
     */
    public Rsa(BigInteger p, BigInteger q) {
        super();
        this.p = p;
        this.q = q;
        // após estar com 'p' e 'q' calculamos 'N'
        calcular_N();
        // o próximo passo é obter o número que após fatorado irá representar a chave pública 'e'
        calcular_FI();
    }

    /**
     * 
     */
    public void encriptar() {

        List<Integer> blocos = string_to_decimal(mensagem);
        String mensagem_encriptada = "";
        array_blocos_encriptados = new ArrayList<String>();
        for (Integer b : blocos) {
            BigInteger bloco_encriptado = aplica_chave_e_mod_no_bloco(e, BigInteger.valueOf(b));
            String aux = bloco_encriptado.toString();
            while (aux.length() < 3) {
                aux = "0" + aux;
            }
            mensagem_encriptada += aux;
            array_blocos_encriptados.add(aux);
        }
        mensagem = mensagem_encriptada;
    }

    /**
     * 
     */
    public void decriptar() {
        calcular_chave_d();
        String mensagem_decriptada = "";
        for (String bloco : array_blocos_encriptados) {
            BigInteger bloco_encriptado = aplica_chave_e_mod_no_bloco(d, BigInteger.valueOf((Integer.parseInt(bloco))));
            mensagem_decriptada += decimal_to_string(bloco_encriptado);
        }
        mensagem = mensagem_decriptada;
    }
    
    /**
     * A limitação do tamanho das chaves p e q são atribuídas a esse método.
     * Para regular o limite apenas altere o parametro de nextInt
     * @return
     */
    private static int calcular_random() {
        return new SecureRandom().nextInt(99);
    }

    /**
     * 
     * @param chave
     * @param bloco
     * @return
     */
    public BigInteger aplica_chave_e_mod_no_bloco(BigInteger chave, BigInteger bloco) {

      	 // base = bloco ; expoente = chave ; mod = N
    	//System.out.println("===================");
    	
    	//System.out.println("bloco:" + bloco);
      	String exponente_bin = chave.toString(2);
      	//System.out.println("exponente em bin: "+exponente_bin+" - tamanho expoente: "+exponente_bin.length());
      	BigInteger aux = new BigInteger("1");
      	 // percorrendo os binarios
      	 //for(int i = 0; i < exponente_bin.length(); i++){
      	int temp;
      	for(int i = exponente_bin.length(); i >0 ; i--){
      		temp = exponente_bin.length()-i;
      		//System.out.println("indice vigente: " + exponente_bin.charAt(i-1)+" - i: "+(temp));
               	if(Integer.parseInt(exponente_bin.charAt(i-1)+"") == 1){
               		//System.out.println("indice=1: " + exponente_bin.charAt(i-1));
                   	//System.out.println("aux: "+aux);
                   	BigInteger expoente = BigInteger.valueOf(2).pow(temp);
                   	//System.out.println("Expoente: " + expoente);
                   	BigInteger potencia = bloco.pow(expoente.intValue());
                   	BigInteger mod = potencia.mod(n);
                   	//System.out.println("mod: " + mod);
                   	aux = aux.multiply(mod);
      		 }
      	 }
      	//System.out.println("aux final: "+aux.mod(n));
       	return aux.mod(n);
       	
       	
       	/*Método anterior de exponenciação
       	 * BigInteger potencia = new BigInteger("1");
       	// enquanto i que começa de 1 for diferente de e+1
       	for (BigInteger i = new BigInteger("1"); !i.equals(chave.add(BigInteger.valueOf(1))); i = i.add(BigInteger.valueOf(1))) {
           	potencia = potencia.multiply(BigInteger.valueOf(bloco));
       	}
       	//System.out.println("método aplica chave e mod no bloco encerrado");
       	return potencia.mod(n);*/
   	}
    
    /**
    * Recebe um BigInteger 'n' e retorna o resultado da fatoração em um ArrayList
    * @param n
    * @return 
    */
   public ArrayList fatorar(BigInteger n) {
       ArrayList<BigInteger> divisores = new ArrayList<BigInteger>();
       BigInteger valor = n;
       for (BigInteger i = new BigInteger("2"); !i.equals(valor); i = i.add(BigInteger.valueOf(1))) {
           if (n.mod(i).equals(BigInteger.ZERO)) {
               n = n.divide(i);
               divisores.add(i);
               i = i.subtract(BigInteger.ONE);
           }
       }
       return divisores;
   }

    /**
     * Calcula da chave privada d
     */
    private void calcular_chave_d() {
        for (BigInteger k = new BigInteger("1"); !k.equals(fi); k = k.add(BigInteger.valueOf(1))) {
            BigInteger numerador = k.multiply(fi).add(BigInteger.valueOf(1));
            if (numerador.mod(e).equals(BigInteger.ZERO)) {
                d = numerador.divide(e);               
                return;
            }
        }
    }

    /**
     * Atribui a 'e' o primeiro número que não é divisível pelos resultados da fatoração de phi
     * @param mcs
     */
    public void calcular_possibilidades_de_e(ArrayList<BigInteger> mcs) {
        ArrayList<BigInteger> mcs_sem_repeticao = new ArrayList<BigInteger>();
        for (BigInteger n : mcs) {
            if (!mcs_sem_repeticao.contains(n)) {
                mcs_sem_repeticao.add(n);
            }
        }
        for (BigInteger i = new BigInteger("2"); !i.equals(fi); i = i.add(BigInteger.valueOf(1))) {
            boolean primo_entre_si = true;
            for (BigInteger n : mcs_sem_repeticao) {
                if (i.mod(n).equals(BigInteger.valueOf(0))) {
                    primo_entre_si = false;
                    break;
                }
            }
            if (primo_entre_si) {
                e = i;
                return;
            }
        }
    }

    /**
     * N é obtido pela multiplicação de 'p' e 'q' - N = p*q
     */
    public void calcular_N() {
        this.n = this.p.multiply(this.q);
    }

    /**
     * FI é obtido pelo cálculo: fi = (p-1)*(q-1)
     */
    public void calcular_FI() {
        this.fi = this.p.subtract(BigInteger.ONE).multiply(this.q.subtract(BigInteger.ONE));
    }



    /**
     * 
     * @param s
     * @return
     */
    public List<Integer> string_to_decimal(String s) {
        List<Integer> mensagem_decimal = new ArrayList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            mensagem_decimal.add((int) s.charAt(i));
        }
        return mensagem_decimal;
    }

    /**
     * 
     * @param d
     * @return
     */
    public String decimal_to_string(BigInteger d) {
        return Character.toString((char) d.intValue());
    }

    /**
     * Recebe um BigInteger 'n' e retorna true se ele for considerado primo.
     * @param n
     * @return 
     */
    public boolean is_primo(BigInteger n) {
        for (BigInteger i = new BigInteger("2"); !i.equals(n); i = i.add(BigInteger.valueOf(1))) {
            if (n.mod(i).equals(BigInteger.valueOf(0))) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<BigInteger> gerar_p_e_q() {

        ArrayList<BigInteger> pq = new ArrayList<BigInteger>();

        
        // pega um numero randomico, extrai o valor absoluto e calcula o seu proximo provável primo
        pq.add(BigInteger.valueOf(calcular_random()).abs().nextProbablePrime());

        pq.add(BigInteger.valueOf(calcular_random()).abs().nextProbablePrime());
        return pq;
    }
    

    // Gets e Sets 
    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger n) {
        this.n = n;
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger e) {
        this.e = e;
    }

    public BigInteger getFi() {
        return fi;
    }

    public void setFi(BigInteger fi) {
        this.fi = fi;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }
}
