package s3test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MySQLConnection {
     
    	public static void dbConnection(HttpServletRequest request, HttpServletResponse response) throws Exception {

	          Class.forName("com.mysql.cj.jdbc.Driver");    
    		        String DB_USER = "admin";
		            String DB_PASSWORD = "vK123kar45#";
		    
//     	           String KEY_STORE_FILE_PATH = "/Users/vamshithatikonda/Desktop/AWS/truststore.jks";
//		            String KEY_STORE_PASS = "vk123kar";    
//            System.setProperty("javax.net.ssl.trustStore", KEY_STORE_FILE_PATH);
//            System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASS);		   
            
		            Properties properties = new Properties();
		            properties.put("user", DB_USER);
		            properties.put("password", DB_PASSWORD);
		            
		     
		            Connection connection = null;
		            Statement stmt = null;
		            ResultSet rs = null;
		            try {
		           
		            	   PrintWriter pw=response.getWriter();
		            	   pw.write("test-----");
		            	   
		                Context initContext = new InitialContext();
		                Context envContext = (Context) initContext.lookup("java:comp/env");
		                DataSource ds = (DataSource) envContext.lookup("jdbc/Sqldb");
		                connection = ds.getConnection();
		                
		                String query = "SHOW STATUS LIKE 'Ssl_cipher'";
		                final PreparedStatement pre1 = connection.prepareStatement(query);
		                ResultSet rs2=pre1.executeQuery();
		             
		                while (rs2.next()) {
		                    String Variable_name = rs2.getString("Variable_name");
		                    String Value = rs2.getString("Value");
		                    //System.out.println("Variable_name: " + Variable_name + "\t  Value: " + Value);
		                    System.out.println("Variable_name: " + Variable_name + "\nValue: " + Value);
		                }
		                String q1="SHOW SCHEMAS";
		                PreparedStatement pre= connection.prepareStatement(q1);
		                rs=pre.executeQuery();
		                while(rs.next()) {
		                	String Schema=rs.getString(1);
		                	pw.write(Schema);
		                	String q2="SELECT table_name FROM information_schema.tables\n"
		                			+ "WHERE table_schema = (?)";
		                	PreparedStatement p=connection.prepareStatement(q2);
		                	p.setString(1,Schema);
		                	ResultSet rs1=p.executeQuery();
		                	while(rs1.next()) {
		                	//	System.out.println("table--"+rs1.getString(1));
		                	}
		                }
		                System.out.println("Connected!");
		                
		                    } catch (SQLException ex) {
		                    	System.out.print(ex.getMessage());
		                    	
		                        throw new Error("Error ", ex);
		                    } 
		        
		            return;
		        }
     
    }
            














       