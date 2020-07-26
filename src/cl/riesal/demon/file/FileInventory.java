package cl.riesal.demon.file;

import java.io.Serializable;
import java.util.Date;

public class FileInventory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7557352230405717791L;
	
	private String idPreGastosReal;
	private Date fecha;
	private String articulo;
	private String descipcion;
	private Integer costo;
	private Integer precio;
	private Integer cantidad;
	private String medida;
	private Long total;
	private String fileName;
	
	

	
	public FileInventory(String idPreGastosReal, Date fecha, String articulo, String descipcion, Integer costo,
			Integer precio, Integer cantidad, String medida, Long total, String fileName) {
		super();
		this.idPreGastosReal = idPreGastosReal;
		this.fecha = fecha;
		this.articulo = articulo;
		this.descipcion = descipcion;
		this.costo = costo;
		this.precio = precio;
		this.cantidad = cantidad;
		this.medida = medida;
		this.total = total;
		this.fileName = fileName;
		
	}
	public String getIdPreGastosReal() {
		return idPreGastosReal;
	}
	public void setIdPreGastosReal(String idPreGastosReal) {
		this.idPreGastosReal = idPreGastosReal;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getArticulo() {
		return articulo;
	}
	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}
	public String getDescipcion() {
		return descipcion;
	}
	public void setDescipcion(String descipcion) {
		this.descipcion = descipcion;
	}
	public Integer getCosto() {
		return costo;
	}
	public void setCosto(Integer costo) {
		this.costo = costo;
	}
	public Integer getPrecio() {
		return precio;
	}
	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public String getMedida() {
		return medida;
	}
	public void setMedida(String medida) {
		this.medida = medida;
	}



	public Long getTotal() {
		return total;
	}



	public void setTotal(Long total) {
		this.total = total;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "FileInventory [idPreGastosReal=" + idPreGastosReal + ", fecha=" + fecha + ", articulo=" + articulo
				+ ", descipcion=" + descipcion + ", costo=" + costo + ", precio=" + precio + ", cantidad=" + cantidad
				+ ", medida=" + medida + ", total=" + total + ", fileName=" + fileName + "]";
	}
	

}
