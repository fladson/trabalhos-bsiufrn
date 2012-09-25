package ui;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import rsa.Rsa;

/**
 * Classe de execução do algoritmo.
 * @author Fladson
 */
public class Java_rsa {
    
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        Rsa rsa;
        
        // Minha intenção é gerar dois números primos altos e mostrá-los ao usuário, se ele aceitar esses números setamos normalmente.
        // Enquanto ele não aceitar ir gerando outros números.
        while(true){
            BigInteger p = Rsa.gerar_p_e_q().get(0);
            BigInteger q = Rsa.gerar_p_e_q().get(1);
            System.out.println("Os números gerados para 'p' e 'q', respectivamente foram: "+p+" | "+q+"\n De acordo? (S ou N)");
            String resposta = teclado.nextLine();
            if(resposta.equalsIgnoreCase("S")){
            	rsa = new Rsa(p,q);
            	break;
            }
        }
        // setando valores baixos para testes
        //rsa = new Rsa(BigInteger.valueOf(31), BigInteger.valueOf(29));
        //System.out.println("p: "+rsa.getP()+" q : "+rsa.getQ()+" N: "+rsa.getN()+" phi: "+rsa.getFi());
        System.out.println("\n Insira a mensagem a ser criptografada: ");
        rsa.setMensagem(teclado.nextLine());
         
        //BigInteger PQ = rsa.getFi();
        
        // após o cálculo temos que fatorar 'phi'
        ArrayList<BigInteger> mcs = rsa.fatorar(rsa.getFi());

        // o valor de 'e' não poderá ser divisível pelos valores de 'mcs'
        rsa.calcular_possibilidades_de_e(mcs);
        System.out.println("Encriptando mensagem...");
        rsa.encriptar();
        System.out.println("Mensagem encriptada: " + rsa.getMensagem());
        System.out.println("Decriptando mensagem...");
        rsa.decriptar();
        System.out.println("Mensagem decriptada: " + rsa.getMensagem());
    }   
}
