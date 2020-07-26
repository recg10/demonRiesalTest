package cl.riesal.demon.bd;

public enum StatusProcessEnum {
	SUCCESS(1, "exitoso"),
	FAIL(0, "Fallido")
	;
	
	
	private StatusProcessEnum (int id, String nombre) {
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

	public static StatusProcessEnum getDocumentoById(String id) {
		int idInt = Integer.parseInt(id);
		StatusProcessEnum docReturn = null;
		for (StatusProcessEnum documento: values()) {
			if (documento.getId() == idInt) {
				docReturn = documento;
				break;
			}
		}
		return docReturn;
	}
}