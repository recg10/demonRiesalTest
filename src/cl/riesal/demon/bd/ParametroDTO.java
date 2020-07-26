package cl.riesal.demon.bd;

import java.io.Serializable;

public class ParametroDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1177123405251146850L;
	/*
	 * CREATE TABLE `pre_parametro` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(200) NOT NULL DEFAULT '0',
  `TEXTO` varchar(200) NOT NULL,
  `NUMERICO` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1
	 * **/
	private Integer id;
	private String nombre;
	private String texto;
	private Integer numerico;
	
	
	public ParametroDTO(Integer id, String nombre, String texto, Integer numerico) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.texto = texto;
		this.numerico = numerico;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Integer getNumerico() {
		return numerico;
	}
	public void setNumerico(Integer numerico) {
		this.numerico = numerico;
	}
	@Override
	public String toString() {
		return "ParametroDTO [id=" + id + ", nombre=" + nombre + ", texto=" + texto + ", numerico=" + numerico + "]";
	}

}
