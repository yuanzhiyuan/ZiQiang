


import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuanHao
 */
//some tools used to write 
public class MyWriter {
	
	
	//this method is used to write a string into a file
    public static boolean writeIntoFile(String filePath, String content, boolean isAppend) {
    	//a sign for if success
        boolean isSuccess = true;
        //omit the file name
        int index = filePath.lastIndexOf("/");
        //get the dir
        String dir = filePath.substring(0, index);
        //create the dir
        File fileDir = new File(dir);
        //make a dir used the dir object
        fileDir.mkdirs();
        //claim a file object
        File file = null;
        try {
        	//init the file
            file = new File(filePath);
            //create a file
            file.createNewFile();

        } catch (Exception e) {
        	//fail
            isSuccess = false;
            e.printStackTrace();
        }
        //clain a FileWriter object
        FileWriter fileWriter = null;
        try {
        	//init fileWriter
            fileWriter = new FileWriter(file, isAppend);
            //write the content
            fileWriter.write(content);
            //flush the stream
            fileWriter.flush();

        } catch (Exception e) {
        	//fail
            isSuccess = false;
            e.printStackTrace();
        } finally {
        	//the stream must be closed
            try {
                if (fileWriter != null) {

                    fileWriter.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return isSuccess;

    }
    public static boolean writeIntoDatabase(ArrayList<ZiQiang> content,String DBUser,String DBPassword,String DBName,String TbName){
    	//try the jdbc driver
    	boolean isSuccess = true;
    	try{
    		//find the drive 
    		Class.forName("com.mysql.jdbc.Driver");
    		System.out.println("successfully drive!");
    	}catch(ClassNotFoundException cnfe){
    		//fail
    		isSuccess = false;
    		System.out.println("drive failed");
    		cnfe.printStackTrace();
    	
    	}
    	//the port address of musql is 3306
    	String mysqlUrl = "jdbc:mysql://localhost:3306/"+DBName;
    	//dreate a connection
    	Connection conn;
    	try{
    		//connect the database
    		conn = DriverManager.getConnection(mysqlUrl,DBUser,DBPassword);
    		//cancel the auto commit 
    		//if I don't cancel I cannot update the db
    		conn.setAutoCommit(false);
    		System.out.println("successfully connected to database!");
    		
    	
    		//create a statement of sql
    		Statement stmt = conn.createStatement();
    		//the sql statement to create a table
    		String createTable = "create table " +TbName+" (No int primary key,Title text,Url text,Summary text,Content text)";
    		//execute the sql and create the table
    		stmt.execute(createTable);
    		//the update sql statement pattern
    		String insertIntoTable = "insert into  "+TbName+" values(?,?,?,?,?)";
    		//create a preparedstatement, it is convenient to execute many similar statement
    		PreparedStatement pst = conn.prepareStatement(insertIntoTable);;
    		//i is used to be the primary key of the table
    		int i=1;
    		//loop to write into db
    		for(ZiQiang ziqiang : content){
    			//init the pst
    			pst = conn.prepareStatement(insertIntoTable);
    			//replace the first ? with the int i
    			pst.setInt(1, i);
    			i++;
    			//replace the second ? whth the title
    			pst.setString(2, ziqiang.getTitle());
    			//replace the third ? with the url
    			pst.setString(3, ziqiang.getUrl());
    			//replace the forth ? with the summary
    			pst.setString(4, ziqiang.getSummary());
    			//replace the fifth ? with the content
    			pst.setString(5, ziqiang.getContent());
    			//execute the pst
    			pst.execute();
    		}
    		System.out.println("get to committing");
    		//commit the update
    		conn.commit();
    		System.out.println("committing end");
    		//close the stmt statement
    		stmt.close();
    		//close the pst statament
    		pst.close();
    		//close the connection with db
    		conn.close();
    		
    	}catch(Exception sqle){
    		//fail
    		isSuccess = false;
    		System.out.println("insert failed");
    		sqle.printStackTrace();
    		
    	}
    	return isSuccess;
    }
    
    

}
