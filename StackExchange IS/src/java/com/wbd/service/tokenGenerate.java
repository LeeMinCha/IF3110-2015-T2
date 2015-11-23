//tokenGenerate.java


package com.wbd.service;

import com.wbd.rest.Token;
import com.wbd.db.DBConnection;

@Path("/token")
public class tokenGenerate{

	public static boolean isTokenFound(Token token){
		boolean found = false;

		DBConnection dbc = new DBConnection();
		PreparedStatement stmt = dbc.getStatement();
		Connection conn = dbc.getConnection();
		try{
			String sql = "SELECT access_token FROM user WHERE access_token = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, token.access_token);
    
            ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()){
				found = true;
			}             
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}

		return found;
	}

	public static Token generateToken(String email, String password){
		Token token = new Token();

		DBConnection dbc = new DBConnection();
		PreparedStatement stmt = dbc.getDBStmt();
		Connection conn = dbc.getConn();
		try{
			String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
			
			//System.out.println(email);
			//System.out.println(password);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			//System.out.println(stmt);
			ResultSet rs = stmt.executeQuery(sql);
			//System.out.println(sql);
			
			if(rs.next()){
				//User is not unique
	            MD5Hashing md5 = new MD5Hashing();

 	            token.access_token = md5.Hash(password); 
        		token.lifetime = 5;
				
				sql = "INSERT INTO user(access_token) VALUES(" + token.access_token + ")";
				stmt.executeUpdate(sql);

                //Format the response to JSON
                //userToken.put("access_token",access_token);
                //userToken.put("lifetime", lifetime);             								
			}

		} catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		} catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}		
		return token;
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Token post(@FormParam("email") String email,
	@FormParam("password") String password) {
		Token token = generateToken(email,password);
		System.out.println(token.access_token);
		return token;
	}


}
