package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class RegistrationDAO {
    
    private final DAOFactory daoFactory;
    
    private final String QUERY_CREATE = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
    
    private final String QUERY_DELETEALL = "DELETE FROM registration WHERE termid = ? AND studentid = ?";
    
    private final String QUERY_DELETE = "DELETE FROM registration WHERE termid = ? AND studentid = ? AND CRN = ?";
    
    private final String QUERY_SELECT = "SELECT * FROM registration WHERE studentid = ? AND termid = ? ORDER BY crn";
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
               
                
                ps = conn.prepareStatement(QUERY_CREATE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int updateCount = ps.executeUpdate();
                
                if (updateCount > 0) {
            
                    result = true;

                }
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
               
                ps = conn.prepareStatement(QUERY_DELETE);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int updateCount = ps.executeUpdate();
                
                if (updateCount > 0) {
            
                    result = true;

                }
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                               
                ps = conn.prepareStatement(QUERY_DELETEALL);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                   
                int updateCount = ps.executeUpdate();
                
                if (updateCount > 0) {
            
                    result = true;

                }
                
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_SELECT);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                boolean hasresults = ps.execute();
                if (hasresults) {

                    rs = ps.getResultSet();

                    while(rs.next()) {
                        JsonObject Student_id = new JsonObject();
                        JsonObject Term_id = new JsonObject();
                        JsonArray RegistrationArray = new JsonArray();
                        Student_id.put("studentid", rs.getInt(studentid));
                        Term_id.put("termid", rs.getInt(termid));
                        RegistrationArray.add(Student_id);
                        RegistrationArray.add(Term_id);
                        
                        result = RegistrationArray.toString();
                        
                        
                    }

                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}
