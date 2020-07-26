package cl.riesal.demon.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cl.riesal.demon.file.FileInventoria;
import cl.riesal.demon.file.FileInventory;
import cl.riesal.demon.process.MainProcess;

public class ManagerBD {
	
	private Connection connection;
	private Statement statement;
	
	private static Logger log = Logger.getLogger(ManagerBD.class);
	 
	public static void main(String[] args) {
	}
	
	private void conecta(){
		
//		String url = "jdbc:mysql://45.7.230.72:3306/riesal";
//	    String username = "rcastro";
//	    String password = "gogi9999";
		
		String url = "jdbc:mysql://localhost:3306/riesal";
	    String username = "root";
	    String password = "root";

		
		try {
			  connection = DriverManager.getConnection(url, username, password);
			  statement = connection.createStatement();
		} catch (Exception e) {
			log.error(e);
		}
	  
	 }
	
	 private void  desconecta(){
		 try {
			statement.close();
			connection.close();
		} catch (SQLException e) {			
			log.error(e);
		}		  
	 }
	 
	 public List<ParametroDTO> consultaParametro(String name) {
			this.conecta();	
			List<ParametroDTO> listParametro = new ArrayList<>();
			String sql = "SELECT * FROM pre_parametro WHERE NOMBRE=?";
			PreparedStatement pre = null;
			List<FileInventoria> listFile = new ArrayList<>();
			try {
				pre = connection.prepareStatement(sql);
				pre.setString(1, name);
				ResultSet rs = pre.executeQuery();			
				while (rs.next()) {	
				  ParametroDTO parametroDTO = new ParametroDTO(rs.getInt("id"), rs.getString("NOMBRE"), rs.getString("TEXTO"), rs.getInt("NUMERICO"));
				  listParametro.add(parametroDTO);
				}
			} catch (SQLException e1) {			
				log.error(e1);
			}finally {
				try {
					pre.close();
					this.desconecta();
				} catch (SQLException e) {				
					log.error(e);
				}
			}
			return listParametro;
			    
		 }
	 
	 public void insertaArticulo(FileInventory fi) {
		 this.conecta();
		 
		 String sql = "INSERT INTO pre_gasto_real_material (" + 
		    		"  ID_PRE_GASTOS_REAL," + 
		    		"  FECHA," + 
		    		"  ARTICULO," + 
		    		"  DESCRIPCION," + 
		    		"  COSTO," + 
		    		"  PRECIO," + 
		    		"  CANTIDAD," + 
		    		"  UBICACION, " +
		    		" TOTAL, " +
		    		" FILE_NAME )" + 
		    		"  values" + 
		    		"  ('"+fi.getIdPreGastosReal()+"'," + 
		    		"	now()," +
		    		"	'"+fi.getArticulo()+"'," + 
		    		"	'"+fi.getDescipcion()+"'," + 
		    		"	" +fi.getCosto()+" ," + 
		    		"	" +fi.getPrecio()+" ," +
		    		"	" +fi.getCantidad()+" ," +
		    		"	'"+fi.getMedida()+"' ,"+
		    		"	"+fi.getTotal() +"," +
		    		"	'"+fi.getFileName()+"' "+
		    		"	)";		    
			try {
				statement.execute(sql);
			} catch (SQLException e) {
				System.out.println("Error al insertar:"+sql);
				log.error(e);
			}finally {
				desconecta();
			}
		    
	 }
	 
	 public List<FileInventoria> consultaArchivos(String name) {
		this.conecta();		 
		String sql = "SELECT * FROM FILE_INVENTORIA WHERE FILE=?";
		PreparedStatement pre = null;
		List<FileInventoria> listFile = new ArrayList<>();
		try {
			pre = connection.prepareStatement(sql);
			pre.setString(1, name);
			ResultSet rs = pre.executeQuery();			
			while (rs.next()) {
			  String archivo = rs.getString("file");
			  Integer procesado = rs.getInt("procesado");
			  Timestamp timestamp = rs.getTimestamp("fecha_actualizacion");
			  Date fechaActualizacion = null;
			  if (timestamp != null) {
				  fechaActualizacion = new java.util.Date(timestamp.getTime());
			  }
			  listFile.add(new FileInventoria(fechaActualizacion, archivo, procesado));
			}
		} catch (SQLException e1) {			
			log.error(e1);
		}finally {
			try {
				pre.close();
				this.desconecta();
			} catch (SQLException e) {				
				log.error(e);
			}
		}
		return listFile;
		    
	 }
	 
	 
	 public boolean insertInventoria(String name, Date fecha, Integer procesado) {
			this.conecta();		 
			String sql = "INSERT INTO FILE_INVENTORIA ( file, fecha_actualizacion ) VALUES ( ?, ?, ?)";
			PreparedStatement pre = null;
			boolean ejecute=false;
			try {
				pre = connection.prepareStatement(sql);
				pre.setString(1, name);
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(fecha);
				pre.setString(2, currentTime);
				pre.setInt(3, procesado);
				ejecute = pre.execute();
			} catch (SQLException e1) {			
				log.error(e1);
			}finally {
				try {
					pre.close();
					this.desconecta();
				} catch (SQLException e) {				
					log.error(e);
				}
			}
			return ejecute;
		 }
	 
	 public boolean updateInventoria(String name, Date fecha) {
			this.conecta();		 
			String sql = "UPDATE FILE_INVENTORIA SET fecha_actualizacion=? WHERE file=?";
			PreparedStatement pre = null;
			boolean ejecute=false;
			try {
				pre = connection.prepareStatement(sql);
				pre.setString(2, name);
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(fecha);
				pre.setString(1, currentTime);
				ejecute = pre.execute();
			} catch (SQLException e1) {			
				log.error(e1);
			}finally {
				try {
					pre.close();
					this.desconecta();
				} catch (SQLException e) {				
					log.error(e);
				}
			}
			return ejecute;
		 }
	 
	 public boolean deleteGastoRealMaterial(String idFile) {
			this.conecta();		 
			String sql = "DELETE FROM pre_gasto_real_material WHERE file_name = ? ";
			PreparedStatement pre = null;
			boolean ejecute=false;
			try {
 				pre = connection.prepareStatement(sql);				
				pre.setString(1, idFile);
				ejecute = pre.execute();
			} catch (SQLException e1) {			
				log.error(e1);
			}finally {
				try {
					pre.close();
					this.desconecta();
				} catch (SQLException e) {				
					log.error(e);
				}
			}
			return ejecute;
		 }

}
