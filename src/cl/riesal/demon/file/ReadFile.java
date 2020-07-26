package cl.riesal.demon.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class ReadFile {
	
	private static String ruta ="C:\\ProgramData\\NCH Software\\Inventoria\\Sellhistory\\";
	
	public static void main(String[] args) {
//		ReadFile readFile = new ReadFile();
//		readFile.leeArchivos();

	}
	
	public List<FileInventory> leeArchivos(File[] listaFicheros) {
		List<FileInventory> list = new ArrayList<FileInventory>();
//		File directorio = new File(ruta);  //directorio actual		
//		File[] listaFicheros = directorio.listFiles();
		//Verifico la fecha de odificacion de los archivos en el caso que cambien 
		//realizo la comparacion
				
		for (int i=0;i<listaFicheros.length;i++) {
		    System.out.println(listaFicheros[i].getName());
		    Stream<String> multilineas = null;
    		try {
    			multilineas = Files.lines(listaFicheros[i].toPath());
    		} catch (IOException e) {    			
    			e.printStackTrace();
    		}
            for (Iterator<String> iterator = multilineas.iterator(); iterator.hasNext();) {
                String linea = iterator.next();
                linea = linea.replaceAll("\"", "");
                String[] columnas = linea.split(",");
                String costo = columnas[2];
                costo = transformValueNumeric(costo);
                
                String total = columnas[5];
                total = transformValueNumeric(total);
                String cantidad = columnas[0];
                cantidad = transformValueNumeric(cantidad);
                FileInventory fi = new FileInventory(columnas[4], new Date(), columnas[1], "s/e", new Integer(costo), new Integer(costo), new Integer(cantidad), 
                		columnas[3], new Long(total), listaFicheros[i].getName());
                list.add(fi);
            }
            multilineas.close();
	    }
		return list;
	}

	private String transformValueNumeric(String costo) {
		costo = costo.replace("$", "");
		costo = costo.replace(".", "");
		costo = costo.replaceAll("\"", "");
		return costo;
	}
	

}
