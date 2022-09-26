import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.mysql.jdbc.PreparedStatement;

//@SuppressWarnings("serial")
public class Jdbc_servlet extends HttpServlet{
	String Driverclass_name;
    String Connection_url;
	String UN;
	String PS;
	int age=0;
	@Override
	public void init(ServletConfig sc) throws ServletException {
		Driverclass_name=sc.getInitParameter("Driverclass_name");
		Connection_url=sc.getInitParameter("Connection_url");
		UN=sc.getInitParameter("UN");
		PS=sc.getInitParameter("PS");
	}

	@Override
	public  void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//get value from  page 
		String firstname=req.getParameter("first_name");
		String midealname=req.getParameter("mideal_name"); 
		String lastname=req.getParameter("last_name");
        age=Integer.parseInt(req.getParameter("age"));	
        String qry="insert into Servlet.table1 values(?,?,?,?)";
        
        //connect to data base server..!
         Connection con=null;
         
         PreparedStatement pstm=null;
        //load and register
        try {
        	//load and register
			Class.forName(Driverclass_name);
			//get connection
			 con=DriverManager.getConnection(Connection_url , UN ,PS);
			//create Statement
			 pstm=con.prepareStatement(qry);
			//we have to set value 
			pstm.setString(1,firstname);
			pstm.setString(2, midealname);
			pstm.setString(3, lastname);
			pstm.setInt(4, age);
			pstm.executeUpdate();
		
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        
        PrintWriter out=res.getWriter();
        out.println("<html>"
				  + "<body bgcolor='blue'><br><br><br><br><br>"
				  +   "<center><h1>DATA INSERT SUCESSFULLY</h1><center>"
				  +   "</body>"
				  +   "</html>");
        
	}
}
