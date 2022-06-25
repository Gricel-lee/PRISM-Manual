package exec.prism;

import java.io.File;
import parser.ast.ModulesFile;
import parser.ast.PropertiesFile;
import prism.Prism;
import prism.PrismDevNullLog;
import prism.PrismFileLog;
import prism.PrismLog;
import prism.Result;

public class run {

	private static String result;
	
	
	public String getResult() {
		///class that will provide an access from python
	    return result;
	}
	
	public run() {}
	
	public static void main( String[] args ) throws Exception {
		
		run scheduler = new run();
		File pm =new File("/Users/gris/eclipse-workspace/execPrismAPI/models/1_0PM[2, 4, 0, 1].mdp");
		
		scheduler.getFeasiblePermutations(pm.toString());
			
		System.out.println(result);
			
    }
	

	

	public static boolean delete = true; // delete .pm files non-schedulable
	
	public String getFeasiblePermutations(String path) throws Exception {
		
		String res = "";
		System.out.println("---------Getting schedules------"+path);
		
		/**Get schedules **/
		File f = new File(path);
		
		// MDP file
		
			
		System.out.println("init prism");
		// a) Initialise PRISM engine 
		long startTime = System.nanoTime(); 
		PrismLog mainLog = new PrismFileLog("stdout"); //new PrismFileLog("stdout");   // Create a log for PRISM output (hidden or stdout)
		Prism prism = new Prism(mainLog);
		prism.initialise();
		
		//long estimatedTime = (System.nanoTime() - startTime)/1000000;
		
		System.out.println("\n\n======Time to init: "+(System.nanoTime() - startTime)/1000000 + "ms");
		
					
		// Parse and load a PRISM model 
		ModulesFile modulesFile = prism.parseModelFile(f); 
		prism.loadPRISMModel(modulesFile);

		// b) Result of "Pmax=?[F done]", if 0, then not schedulable
		PropertiesFile propertiesFile  = prism.parsePropertiesString(modulesFile, "Pmax=?[F done]");
		Result result = prism.modelCheck(propertiesFile, propertiesFile.getPropertyObject(0));
		double d = Double.parseDouble( result.getResult().toString() );
		
		System.out.println("\n\n======Time to f1: "+(System.nanoTime() - startTime)/1000000 + "ms");
		
		
		
		// c.1) NOT Possible to Schedule
		//------
		if (d==0 | d==0.0) {
			run.result = "[-1,-1,-1]";
			//return "[-1,-1,-1]";
		}
		
		
		// c.2) Schedulable
		
			// get properties values
			propertiesFile  = prism.parsePropertiesString(modulesFile, "R{\"travel\"}min=?[F done]"); //System.out.println(propertiesFile.getPropertyObject(0));
			Result rTravel = prism.modelCheck(propertiesFile, propertiesFile.getPropertyObject(0));
			
			System.out.println("\n\n======Time to f2: "+(System.nanoTime() - startTime)/1000000 + "ms");
		
			
			propertiesFile  = prism.parsePropertiesString(modulesFile, "R{\"idle\"}min=?[F done]");
			Result rIdle = prism.modelCheck(propertiesFile, propertiesFile.getPropertyObject(0));
			
			System.out.println("\n\n======Time to f3: "+(System.nanoTime() - startTime)/1000000 + "ms");
			
			
			propertiesFile  = prism.parsePropertiesString(modulesFile, "Pmin=?[G fail]");
			Result rProb = prism.modelCheck(propertiesFile, propertiesFile.getPropertyObject(0));
			
			System.out.println("\n\n======Time to f4: "+(System.nanoTime() - startTime)/1000000 + "ms");
			
			//System.out.println("R{\"travel\"}min=? " +rTravel.getResult()+ " R{\"idle\"}min=? " + rIdle.getResult()+  " Pmin=? " + rProb.getResult() + " || Schedulable MDP: "+f.toString());
			
			// delete mdp
			f.delete();
			// save data
			res = "[" + (String) rTravel.getResult() +"," + (String) rIdle.getResult() +","+ (String) rProb.getResult() + "]";
			run.result = res;
		
		
		return res;
	}
}



