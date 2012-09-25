/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package rsa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Cícero
 */
public class Util {


	public static void main(String[] args) {
		
		BigInteger p = new BigInteger("109758125950115069");
		//System.out.println(p.toString(2));
		//fatorar(p);
		
		
		String b = "123456";
		System.out.println("lenght: "+b.length());
		for(int i=b.length() ; i>0 ; i--){
			System.out.print(b.charAt(i-1));
		}
		
	}
	
	   public static ArrayList fatorar(BigInteger n) {
	       ArrayList<BigInteger> divisores = new ArrayList<BigInteger>();
	       BigInteger valor = n;
	       for (BigInteger i = new BigInteger("2"); !i.equals(valor); i = i.add(BigInteger.valueOf(1))) {
	           if (n.mod(i).equals(BigInteger.ZERO)) {
	               n = n.divide(i);
	               divisores.add(i);
	               i = i.subtract(BigInteger.ONE);
	               System.out.println(i);
	           }
	       }
	       return divisores;
	   }
}
