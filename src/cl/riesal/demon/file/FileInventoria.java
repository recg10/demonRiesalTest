package cl.riesal.demon.file;

import java.io.Serializable;
import java.util.Date;

public class FileInventoria implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3726119493592770178L;
	
	private Date fechaActualizacion;
	private String archivo;
	private Integer procesado;
	
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	
	
	public FileInventoria(Date fechaActualizacion, String archivo, Integer procesado) {
		super();
		this.fechaActualizacion = fechaActualizacion;
		this.archivo = archivo;
		this.procesado = procesado;
	}
	public Integer getProcesado() {
		return procesado;
	}
	public void setProcesado(Integer procesado) {
		this.procesado = procesado;
	}
	@Override
	public String toString() {
		return "FileInventoria [fechaActualizacion=" + fechaActualizacion + ", archivo=" + archivo + ", procesado="
				+ procesado + "]";
	}
	
	

}
