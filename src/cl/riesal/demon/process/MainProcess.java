package cl.riesal.demon.process;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import cl.riesal.demon.bd.ManagerBD;
import cl.riesal.demon.bd.ParametroDTO;
import cl.riesal.demon.bd.ParametroEnum;
import cl.riesal.demon.bd.StatusProcessEnum;
import cl.riesal.demon.file.FileInventoria;
import cl.riesal.demon.file.FileInventory;
import cl.riesal.timmer.TimmerExecute;

public class MainProcess {
	
	private static Logger log = Logger.getLogger(MainProcess.class);

	public void inicia() {
		//lee estructura de archivos
		//Busco ruta de archivos property
		ManagerBD con = new ManagerBD();
		List<ParametroDTO> listParamtero =  con.consultaParametro(ParametroEnum.RUTA_SOFTWARE_ARCHIVOS_INVENTORY.getNombre());
		if(listParamtero==null || listParamtero.size()==0) {
			log.info("No existe parametro en BD");
			return;
		}
		
		File directorio = new File(listParamtero.get(0).getTexto());  //directorio actual		
		File[] listaFicheros = directorio.listFiles();
		List<FileInventory> list = new ArrayList<FileInventory>();
		log.info("Cantidad de ficheros: "+listaFicheros.length);
		//Leo los ficheros
		for (int i=0;i<listaFicheros.length;i++) {
			log.info("Archivo de directorio leido: "+listaFicheros[i].getName());
		    //Consulto si el nombre ya existe en la BD
			List<FileInventoria> listFileInventoria = con.consultaArchivos(listaFicheros[i].getName());
			//NO existe el archivo en la BD
			Date fechaFileDirectory = new Date(listaFicheros[i].lastModified());
			log.info("Cantidad de registros: "+listFileInventoria.size());
			if (listFileInventoria!=null && listFileInventoria.size()==0) {
				//Insert registro
				log.info("INSERTO tabla de inventoria fechaArchivo: "+fechaFileDirectory + " nombre de archivo:"+listaFicheros[i].getName());
				con.insertInventoria(listaFicheros[i].getName(), fechaFileDirectory, StatusProcessEnum.FAIL.getId());
				//Inserto detalle de en las otras tablas
				Stream<String> multilineas = null;
	    		try {
	    			multilineas = Files.lines(listaFicheros[i].toPath());
	    		} catch (IOException e) {    			
	    			log.error(e);
	    		}
	            for (Iterator<String> iterator = multilineas.iterator(); iterator.hasNext();) {	            	
	                String linea = iterator.next();
	                log.info("Lineas a procesar: "+linea);
	                linea = linea.replaceAll("\",\"" , "|");
	                                
//	                String[] columnas = linea.split("\",");
	                String[] columnas = linea.split("\\|");
	                String costo = columnas[2];
	                costo = transformValueNumeric(costo);	                
	                String total = columnas[5];
	                total = transformValueNumeric(total);
	                String cantidad = columnas[0];
	                cantidad = transformValueNumeric(cantidad);
	                log.info("idPreGastosReal:"+ columnas[4]);
	                log.info("articulo:"+ columnas[1]);
	                log.info("costo:"+ costo);
	                log.info("cantidad:"+ cantidad);
	                log.info("medida:"+ columnas[3]);
	                log.info("total:"+ total);
	                FileInventory fi = new FileInventory(columnas[4], new Date(), columnas[1], "s/e", new Integer(costo), 
	                		new Integer(costo), new Integer(cantidad), columnas[3], new Long(total), listaFicheros[i].getName());
	                log.info("Registro a insertar en Gasto real material:"+ fi.toString());
	                
	                con.insertaArticulo(fi);
	            }
				
			}else if (listFileInventoria!=null && listFileInventoria.size()==1) {
				Date fechaBD = listFileInventoria.get(0).getFechaActualizacion();
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				String fechaBDString =  dateFormat.format(fechaBD);
				String fechaFileDirectoryString =  dateFormat.format(fechaFileDirectory);
				Date fecBD=null;
				Date fecFile=null;
				try {
					fecBD=dateFormat.parse(fechaBDString);
					fecFile=dateFormat.parse(fechaFileDirectoryString);
					log.info("Fecha BD: "+fecBD+" fecha Archivo: "+fecFile);
				} catch (ParseException e1) {					
					log.error(e1);
				}
		        //Si las fechas de modificacion sin iguales no se debe hacer niguna modificacion en la BD
				if (fecBD.compareTo(fecFile)==0 ) {
					continue;
				} else if (fechaFileDirectory.compareTo(fechaBD) == 1) {//Si ha sido modificada la fecha del arhivo de directorios 
					//Elimino todos los registros de gasto reaql material
					con.deleteGastoRealMaterial(listFileInventoria.get(0).getArchivo());
					//Se actualiza					
					Date fechaDirectory = new Date(listaFicheros[i].lastModified());
					log.info("Actualizo tabla de inventoria fechaArchivo: "+fechaDirectory);
					con.updateInventoria(listaFicheros[i].getName(), fechaDirectory);
					
					Stream<String> multilineas = null;
		    		try {
		    			multilineas = Files.lines(listaFicheros[i].toPath());
		    			log.info("Linias a procesar: "+multilineas);
		    		} catch (IOException e) {    			
		    			log.error(e);
		    		}
		            for (Iterator<String> iterator = multilineas.iterator(); iterator.hasNext();) {		            	
		                String linea = iterator.next();
		                log.info("linea:"+ linea);
//		                linea = linea.replaceAll("\"", "");
		                linea = linea.replaceAll("\",\"" , "|");
//		                String[] columnas = linea.split(",");
		                String[] columnas = linea.split("\\|");		                
		                String costo = columnas[2];
		                costo = transformValueNumeric(costo);		                
		                String total = columnas[5];
		                total = transformValueNumeric(total);
		                String cantidad = columnas[0];
		                cantidad = transformValueNumeric(cantidad);		                
		                log.info("idPreGastosReal:"+ columnas[4]);
		                log.info("articulo:"+ columnas[1]);
		                log.info("costo:"+ costo);
		                log.info("cantidad:"+ cantidad);
		                log.info("medida:"+ columnas[3]);
		                log.info("total:"+ total);
		                
		                FileInventory fi = new FileInventory(columnas[4], new Date(), columnas[1], "s/e", new Integer(costo), 
		                		new Integer(costo), new Integer(cantidad), columnas[3], new Long(total), listaFicheros[i].getName());
		                con.insertaArticulo(fi);
		                log.info("Registro a insertar en Gasto real material:"+ fi.toString());
		            }
				}
			}
		}
	}
			
	private static String transformValueNumeric(String costo) {
		costo = costo.replace("$", "");
		costo = costo.replace(".", ",");
		costo = costo.replace(",00", "");
		costo = costo.replaceAll("\"", "");
		return costo;
	}
}
