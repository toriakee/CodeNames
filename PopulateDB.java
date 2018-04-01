package Database;

import java.sql.*;

/**
 * A class to create two tables in the database, Words and Players,
 * and add all the data into both.
 * 
 * @author Chloe Brinkman
 *
 */
public class PopulateDB {
	
	
	public static void main (String [] args)  throws Exception {
	  
	   Connection connection = null;
       connection = ConnectionManager.getConnection();

	 /** Statement stmt = null;
	   Statement stmt2 = null;
	   Statement inPlayers = null;
	   Statement inWords = null;   **/
	      
	      // The following two statements have been executed -
	      // the lyon database now contains two tables, Words and Players.
	    
	      
	    /**  System.out.println("Creating Players table in database...");
	      stmt = connection.createStatement();
	      
	      String string = "CREATE TABLE Players " +
	                   "(userID INTEGER, " +
	                   " username VARCHAR(9), " + 
	                   " password CHAR(6), " + 
	                   " victories INTEGER, " + 
	                   " losses INTEGER, " + 
	                   " PRIMARY KEY ( userID ))"; 

	      stmt.executeUpdate(string);
	      System.out.println("Created a table!");
	     **/
	      
	      /**
	      System.out.println("Creating Words table in database...");
	      stmt2 = connection.createStatement();
	      
	      String string2 = "CREATE TABLE Words " +
                "(wordNumber VARCHAR(7), " +
                " set1 VARCHAR(15), " + 
                " set2 VARCHAR(15), " + 
                " set3 VARCHAR(15), " + 
                " set4 VARCHAR(15), " + 
                " set5 VARCHAR(15), " + 
                " set6 VARCHAR(15), " + 
                " set7 VARCHAR(15), " + 
                " set8 VARCHAR(15), " + 
                " set9 VARCHAR(15), " + 
                " set10 VARCHAR(15), " + 
                " set11 VARCHAR(15), " + 
                " set12 VARCHAR(15), " + 
                " set13 VARCHAR(15), " + 
                " set14 VARCHAR(15), " + 
                " set15 VARCHAR(15), " + 
                " set16 VARCHAR(15), " + 
                " set17 VARCHAR(15), " + 
                " set18 VARCHAR(15), " + 
                " set19 VARCHAR(15), " + 
                " set20 VARCHAR(15), " + 
                " set21 VARCHAR(15), " + 
                " set22 VARCHAR(15), " + 
                " set23 VARCHAR(15), " + 
                " set24 VARCHAR(15), " + 
                " set25 VARCHAR(15), " + 
                " set26 VARCHAR(15), " + 
                " set27 VARCHAR(15), " + 	                              
                " PRIMARY KEY ( wordNumber ))";  

	      stmt2.executeUpdate(string2);
	      System.out.println("Created a table!");
	   **/   
	   
       
       
       
	   // The Words table was populated using the command line by copying a CSV file into it.  
	   // The following code was executed to populate the Players table.
	   
	   /**
	   try{	     
  
		      System.out.println("Inserting records into players...");		     		    	  		      
		      
		        inPlayers = connection.createStatement();
		      
		      String sql = "INSERT INTO Players " +
		                   "VALUES (1, 'player1', 'aaa001', null, null)";
		      inPlayers.executeUpdate(sql);
		      
		      
		      sql = "INSERT INTO Players " +
		                   "VALUES (2, 'player2', 'aaa002', null, null)";
		      inPlayers.executeUpdate(sql);
		      
		      
		      sql = "INSERT INTO Players " +
		                   "VALUES (3, 'player3', 'aaa003', null, null)";
		      inPlayers.executeUpdate(sql);
		      
		      
		      sql = "INSERT INTO Players " +
	                  "VALUES(4, 'player4', 'aaa004', null, null)";
		      inPlayers.executeUpdate(sql);
		      
		      
		      sql = "INSERT INTO Players " +
		                   "VALUES(5, 'player5', 'aaa005', null, null)";
		      inPlayers.executeUpdate(sql);
		      
		      
		      sql = "INSERT INTO Players " +
	                   "VALUES (6, 'player6', 'aaa006', null, null)";
	          inPlayers.executeUpdate(sql);
	          
	          
	          sql = "INSERT INTO Players " +
	                   "VALUES (7, 'player7', 'aaa007', null, null)";
	          inPlayers.executeUpdate(sql);
	      
	          
	          sql = "INSERT INTO Players " +
	                   "VALUES (8, 'player8', 'aaa008', null, null)";
	          inPlayers.executeUpdate(sql);
	          
	          
	          sql = "INSERT INTO Players " +
	                   "VALUES(9, 'player9', 'aaa009', null, null)";
	          inPlayers.executeUpdate(sql);
	         
	      
	         String sql = "INSERT INTO Players " +
                  "VALUES (10, 'player10', 'aaa010', null, null)";
             inPlayers.executeUpdate(sql);
             
             
             sql = "INSERT INTO Players " +
                  "VALUES (11, 'player11', 'aaa011', null, null)";
             inPlayers.executeUpdate(sql);
             
             
             sql = "INSERT INTO Players " +
                  "VALUES (12, 'player12', 'aaa012', null, null)";
             inPlayers.executeUpdate(sql);
     
		      
		      System.out.println("Data has been inserted into Players");
		     
		   }catch(SQLException se){
		      
		      se.printStackTrace();
		   }finally{
		     
		      try{
		         if(inPlayers!=null&&inWords!=null)
		            connection.close();
		      }catch(SQLException se){
		      }
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		   }		
		 **/
	 System.out.println("Words table and Players table have been created. "
			             + "Players table has been populated.");
}   
}