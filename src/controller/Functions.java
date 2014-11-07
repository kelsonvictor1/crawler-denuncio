package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Functions {
	
	MySQL mysql = new MySQL();
	
	public void getUrlSource(String url) throws IOException {
		
		url = "http://www.denuncio.com.br/busca/?search="+url+"&x=0&y=0"; //URL padrão de busca no denuncio.com.br
		
		//Salvando o código fonte da página com os resultados da minha busca em um arquivo .txt
		
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter("output.txt"));  
		
		URL c = new URL(url);
		URLConnection yc = c.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream(), "iso-8859-1"));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null){
			a.append(inputLine);
			buffWrite.append(inputLine+"\n");
			
		}
			in.close();
			buffWrite.close();
			
	}
	
	public void getElements() throws IOException{
		
		//Lendo o arquivo do código fonte para tratar somente os itens desejados
		
		FileReader dados = new FileReader("output.txt"); 
		BufferedReader br = new BufferedReader(dados);
		
		
		String[] splitsForCompanies = null;
		LinkedList<String> companies = new LinkedList<String>();
		
		String[] splitsForComplaints = null;
		LinkedList<String> complaints = new LinkedList<String>();
		
		DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");    
		Date date = new Date();    
		String data = dateFormat.format(date);
		
		
		
		while( br.ready() ){
			
			String linha = br.readLine();
			
			/*
			 * BUSCANDO SOMENTE O CONTEÚDO DENTRO DAS DIVS ESPECÍFICAS DA LISTAGENS DE EMPRESAS E RECLAMAÇÕES
			 */
			
			if(linha.startsWith("							<div class=\"company-cat-name\">")){ //ADICIONANDO AS EMPRESAS
				
				/*
				 * /Depois que a string é dividida, 
				 * pego somente o conteúdo dentro da <div ..></div> e removo as últimas tags htmls da string 
				 */
				
				splitsForCompanies = linha.split("/\">");
				companies.add(splitsForCompanies[1].substring(0, splitsForCompanies[1].length()-10)); 
			}
			if(linha.startsWith("							<div class=\"company-name\">")){ //ADICIONANDO SUAS RESPECTIVAS MENÇÕES E RECLAMAÇÕES
				
				splitsForComplaints = linha.split("/\">");
				complaints.add(splitsForComplaints[1].substring(0, splitsForComplaints[1].length()-10));
			}
			
			
		}
		
		br.close();
		dados.close();
		int i = 0;
		
		//INSERINDO NO BANCO DE DADOS AS INFORMAÇÕES JÁ TRATADAS
		
		for(String s: companies){
			
		mysql.insert("INSERT INTO data (timestamp, company, complaint) VALUES ('"+data+"','"+s+"','"+complaints.get(i)+"');");
		System.out.println("Salvando resultados no banco de dados...");
		i++;
		}
	}

}
