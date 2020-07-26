package cl.riesal.timmer;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import cl.riesal.demon.bd.ManagerBD;
import cl.riesal.demon.bd.ParametroDTO;
import cl.riesal.demon.bd.ParametroEnum;
import cl.riesal.demon.process.MainProcess;

public class TimmerExecute {
	
	private static Logger log = Logger.getLogger(TimmerExecute.class);
	
	public static void main(String arglist[]) {
		BasicConfigurator.configure();
		ManagerBD con = new ManagerBD();
		List<ParametroDTO> listParamtero =  con.consultaParametro(ParametroEnum.TIMMER.getNombre());
		if(listParamtero==null || listParamtero.size()==0) {
			log.info("No existe parametro en BD");
			return;
		}
		int delay = listParamtero.get(0).getNumerico();
		log.info("tiempo de ejecucion segun BD: "+delay +" milisegundos");
	    Timer timer;
	    timer = new Timer();
	    log.debug("Inicio ejecucion...");
	    TimerTask task = new TimerTask() {
	    MainProcess mainProcess = new MainProcess();
	        @Override	        
	        public void run()
	        {
	        	mainProcess.inicia();
	        	
	        }
	        };
	 // Empezamos dentro de 10ms y luego lanzamos la tarea cada 1000ms
	    timer.schedule(task,0, delay);
	}
}
