package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQL {
	
	/* É PRECISO ALTERAR OS DADOS DE ACORDO COM O SEU BANCO 
	 * 
	 */
	
	public String urlBD = "crawler"; //criar BD com esse nome
	public String userBD = "seu-user";
	public String senhaBD = "sua-senha";
	
	   public void insert(String s) {
	        try {
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            try {
	                Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+urlBD, userBD, senhaBD);
	                try {
	                    String sql = s;
	                    Statement stm = conn.createStatement();
	                    try {
	                        stm.executeUpdate(sql);
	                        
	                      //MENSAGENS PARA O PROGRAMADOR
	                    } catch (Exception ex) {
	                       // System.out.println("\nErro no resultset!\n" + ex);
	                    }
	                } catch (Exception ex) {
	                    //System.out.println("\nErro no statement!");
	                }finally{
	                	if(conn != null){
	                		conn.close();
	                	}
	                	
	                }
	            } catch (Exception ex) {
	               // System.out.println("\nErro no connection!");
	            }
	        } catch (Exception ex) {
	           // System.out.println("\nDriver nao pode ser carregado: "+ex);
	        }

	    }
	   
	   public void dataOutput(){
	    	
	    	try {
	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	            
	            try {
	            	 Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+urlBD, userBD, senhaBD);
	                try {
	                    
	                    Statement stm = conn.createStatement();
	                    try {
	                    	String query = "SELECT * FROM data;";
	                        ResultSet rs = stm.executeQuery(query);
	                        
	                        System.out.println("TIMESTAMP   |                   EMPRESA      |                    RECLAMAÇÃO ");
	                        System.out.println("------------------------------------------------------------------------------------------------------");
	                        
	                        while(rs.next()){
	                        	
	                        	System.out.println(rs.getString("timestamp")+"   |     "+rs.getString("company")+"    |        "+rs.getString("complaint"));
	                        	
	                        }
	                        //MENSAGENS PARA O PROGRAMADOR
	                    } catch (Exception ex) {
	                        System.out.println(ex);
	                    }
	                } catch (Exception ex) {
	                   // System.out.println("\nErro no statement!");
	                }finally{
	                	if(conn != null){
	                		conn.close();
	                	}
	                }
	            } catch (Exception ex) {
	                //System.out.println("\nErro no connection! " + ex);
	            }
	        } catch (Exception ex) {
	            //System.out.println("\nDriver nao pode ser carregado!");
	        }

	   }
}
