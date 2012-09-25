package gui;

import java.util.ArrayList;
import java.util.Collections;

import des.Des;

public class Util {
	public static void main(String[] args) {
	Des d = new Des("fladsonthhhhhhhha oin oin onki osinoinoi noin oin","12345678");
	d.encriptar();
	System.out.println("Mensagem Encriptada: "+d.getMensagem());
	d.decriptar();
	System.out.println("Mensagem Decriptada: "+d.getMensagem());
	}

	 
	
}



