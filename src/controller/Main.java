package controller;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Functions start = new Functions();
		MySQL mysql = new MySQL();
		
		String s;
		Scanner read = new Scanner(System.in);
		System.out.println("Web Crawler - denuncio.com.br");
		System.out.println("Informe uma empresa a ser pesquisada (exemplo: ponto frio, americanas, netshoes):\n");
		
		s = read.nextLine();
		
		System.out.println("Pesquisando menções e reclamações sobre esta empresa...");
		
		
		start.getUrlSource(s);
		start.getElements();
		
		mysql.dataOutput();
	}





}
