/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wbd.qst;

import com.wbd.ans.Answer;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.jboss.logging.Logger;

/**
 *
 * @author User
 */
@WebService(serviceName = "QuestionWS")
public class QuestionWS {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "retrieveQ")
    public ArrayList<Question> retrieveQ() {
        ArrayList<Question> questions = new ArrayList<Question>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wbd?zeroDateTimeBehavior=convertToNull","root","");
            Statement stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM question";
            
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            ResultSet rs = dbStatement.executeQuery();
            
            int i = 0;
            while (rs.next()){
                questions.add(new Question(rs.getInt("IDQ"),rs.getString("access_token"),rs.getString("QuestionTopic"),rs.getString("Content"),rs.getInt("Vote")));
                ++i;
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException ex){
            //Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getQuestionbyID")
    public ArrayList<Question> getQuestionbyID(@WebParam(name = "qid") int qid) {
        ArrayList<Question> questions = new ArrayList<Question>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wbd","root","");
            Statement stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM question WHERE IDQ = ?";
            
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            dbStatement.setInt(1,qid);
            ResultSet rs = dbStatement.executeQuery();
            
            int i = 0;
            while (rs.next()){
                questions.add(new Question(rs.getInt("IDQ"),rs.getString("access_token"),rs.getString("QuestionTopic"),rs.getString("Content"),rs.getInt("Vote")));
                ++i;
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException ex){
            //Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "createQ")
    public int createQ(@WebParam(name = "access_token") String access_token, @WebParam(name = "title") String title, @WebParam(name = "content") String content) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wbd","root","");
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO question(access_token, QuestionTopic, Content, Vote) VALUES (?,?,?,?)";
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            dbStatement.setString(1, access_token);
            dbStatement.setString(2, title);
            dbStatement.setString(3, content);
            dbStatement.setInt(4, 0);
            dbStatement.executeUpdate();
            stmt.close();
            return 1;
        } catch (SQLException ex){
            //Logger.getLogger(QuestionWS.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateQ")
    public int updateQ(@WebParam(name = "access_token") String access_token, @WebParam(name = "qid") int qid, @WebParam(name = "title") String title, @WebParam(name = "content") String content) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wbd","root","");

            // Turn on transactions
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE question SET QuestionTopic = ?, Content = ? WHERE IDQ = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setInt(3, qid);
            pstmt.executeUpdate();
            conn.commit();


            System.out.println("Order successful!  Thanks for your business!");
            return 1;
        }
       catch (Exception e) {
            // Any error is grounds for rollback
            try {
                conn.rollback();
            }
            catch (SQLException ignored) { }
                System.out.println("Order failed. Please contact technical support.");
                  return 0;
       }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "deleteQ")
    public int deleteQ(@WebParam(name = "access_token") String access_token, @WebParam(name = "qid") int qid) {
        Connection conn = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wbd","root","");
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM question WHERE IDQ = ?";
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            dbStatement.setInt(1, qid);
            dbStatement.executeUpdate();
            stmt.close();
            return 1;
        } catch (SQLException ex){
            System.out.println(ex);
            return 0;
        }
    }    

    /**
     * Web service operation
     */
    @WebMethod(operationName = "voteUp")
    public int voteUp(@WebParam(name = "access_token") String access_token, @WebParam(name = "qid") String qid) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wbd","root","");

            // Turn on transactions
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE question SET Vote = Vote + 1 WHERE IDQ = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, qid);
            pstmt.executeUpdate();
            conn.commit();


            System.out.println("Order successful!  Thanks for your business!");
            return 1;
        }
       catch (Exception e) {
            // Any error is grounds for rollback
            try {
                conn.rollback();
            }
            catch (SQLException ignored) { }
                System.out.println("Order failed. Please contact technical support.");
                  return 0;
       }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "voteDown")
    public int voteDown(@WebParam(name = "access_token") String access_token, @WebParam(name = "qid") String qid) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wbd","root","");

            // Turn on transactions
            conn.setAutoCommit(false);

            Statement stmt = conn.createStatement();
            String sql = "UPDATE question SET Vote = Vote - 1 WHERE IDQ = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, qid);
            pstmt.executeUpdate();
            conn.commit();


            System.out.println("Order successful!  Thanks for your business!");
            return 1;
        }
       catch (Exception e) {
            // Any error is grounds for rollback
            try {
                conn.rollback();
            }
            catch (SQLException ignored) { }
                System.out.println("Order failed. Please contact technical support.");
                  return 0;
       }
    }
}
