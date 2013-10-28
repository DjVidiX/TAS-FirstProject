/**
 *
 *  Implementacja musi
 *  - implemetować co najmniej jeden zdalny interfejs
 *  - zdefiniować konstruktor dla zdalnego obiektu
 *  - zdefiniować metody, które mają być zdalnie wywoływane
 *
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/*
 * Dziedziczenie po UnicastRemoteObject sprawia, że klasa będzie używała
 * domyślnego protokołu komunikacyjnego używanego przez RMI (czyli TCP) oraz
 * będzie cały czas uruchomiona.
 */

public class InterfejsImpl extends UnicastRemoteObject implements Interfejs {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//konstruktor zdalnego obiektu
	private static List<Player> clientsList = new LinkedList<Player>();
	private List<Question> questionsList = new LinkedList<Question>();
	private List<String> answersList = new LinkedList<String>();
	
	//private static int counter = 0;
	
	private void prepareQuestions() {
		this.questionsList.add(new Question("Kto jest gejem?", "Łukasz"));
	}

    public InterfejsImpl() throws RemoteException {
        super(); //tutaj jest wywołany konstruktor UnicastRemoteObject
    }
    
    
    public int registryClient(String name) throws RemoteException {
    	int id = 0;
    	clientsList.add(new Player(name));
    	for(Player l: clientsList) {
    		id = l.getId();
    	}
    	//System.out.println("Zarejestrowałem gracza o imieniu " + clientsList.getLast().getName() + " i id= " + id);
    	
    	return id;
    }
    
    public void setReady(int id) throws RemoteException {
    	clientsList.get(id).setReady();
		System.out.println("LOOL l.getId():)");
    }
    
    public void waitForPlayers() throws RemoteException {
    	boolean ready_all = false;
    	while (!ready_all) {
    		for(int i = 0; i < clientsList.size(); i++) {
    			System.out.println("klient i= " + i + "jest gotowy? " + clientsList.get(i).isReady());
				if (!clientsList.get(i).isReady()) {
					System.out.println("klient i=" + i + "jest niegotowy:" );
					ready_all = false;
					break;
				}
				else {
					System.out.println("klient i=" + i + "jest gotowy:" );
					ready_all = true;
				}
    		}
    	}
    	this.prepareQuestions();
    	
    }
    
    public String getQuestion(int i) throws RemoteException {
    	return questionsList.get(i).getAsk();
    }
    
    public void giveAnswer(String answer, int userId) throws RemoteException {
    	this.answersList.add(answer);
    	clientsList.get(userId).increasePoints(5);
    }
   
    
	public void waitForAnswers() throws RemoteException {
    	while(answersList.size()==clientsList.size());		
	}

	
	public String getScoreTable() throws RemoteException {
		answersList.clear();
		String odp = new String("");
		for(Player p: clientsList) {
			odp += p.getName() + ": " + p.getPoints() + " pkt\n";
		}
		return odp;
	}

	
	public void finishGame(int userId) throws RemoteException {
		clientsList.remove(clientsList.get(userId));		
	}
    
    
    
    
}
