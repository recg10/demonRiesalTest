package cl.riesal.demon.bd;

public enum ParametroEnum {
	RUTA_SOFTWARE_ARCHIVOS_INVENTORY(2, "ruta_software_archivos_inventory"),
	TIMMER(3, "TIMMER"),
	MAIL(1, "Mail")
	;
	
	
	private ParametroEnum (int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	private int id;
	private String nombre;
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}

	public static ParametroEnum getDocumentoById(String id) {
		int idInt = Integer.parseInt(id);
		ParametroEnum docReturn = null;
		for (ParametroEnum documento: values()) {
			if (documento.getId() == idInt) {
				docReturn = documento;
				break;
			}
		}
		return docReturn;
	}
}