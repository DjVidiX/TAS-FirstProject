import java.net.MalformedURLException;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.*;
import java.io.*;

public class Klient {

	private int id;
	private String ready = "temp", question, answer, scoreTable;
	private Interfejs interf = null;
	private Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("parametry: //host/nazwa nazwa_użytkownika");
            System.exit(0);
        }
        String fileName = args[0];
        String userName = args[1];

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
	
        try {
        	Klient k = new Klient();
            k.interf = (Interfejs) Naming.lookup(fileName);
            k.id = k.interf.registryClient(userName);
            
	    	System.out.println("Gdy będziesz gotów, wpisz \"r\" by rozpocząć grę!\n");
	    	
	    	while (!k.ready.equals("r")) k.ready = k.in.next();
	    	k.interf.setReady(k.id);
	    	
	    	System.out.println("Oczekiwanie na pozostałych graczy...\n");
	    	
	    	k.interf.waitForPlayers();
	    	
	    	System.out.println("Gra rozpoczęta.\n");
	    	
	    	for (int i = 0; i < 5; i++) {
	    		Random rn = new Random();
	    		int randomNum = rn.nextInt(64);
	    		
	    		k.question = k.interf.getQuestion(randomNum);
	    		
	    		System.out.println(k.question + "\n\nOdpowiedź: ");
	    		
	    		k.answer = k.in.next();
	    		k.interf.giveAnswer(k.answer, k.id);
	    		
	    		System.out.println("Oczekiwanie na odpowiedzi pozostałych graczy...\n");

	    		k.interf.waitForAnswers();	    		
	    		
	    		k.scoreTable = k.interf.getScoreTable(randomNum, k.id);
	    		
		    	System.out.println("\n"+ k.scoreTable + "\n\n");
	    	}
	    	k.interf.finishGame(k.id);
	    	
	    	System.out.println("Gra zakończona.");
	    	
	        
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
	}
}
