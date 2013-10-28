/**
 *
 *  Implementacja musi
 *  - implemetować co najmniej jeden zdalny interfejs
 *  - zdefiniować konstruktor dla zdalnego obiektu
 *  - zdefiniować metody, które mają być zdalnie wywoływane
 *
 */

import java.io.IOException;
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
	private int order = 0;
	//konstruktor zdalnego obiektu
	private static List<Player> clientsList = new LinkedList<Player>();
	private List<Question> questionsList = new LinkedList<Question>();
	private static Map<Integer, String> answersList = new HashMap<Integer, String>();
	
	//private static int counter = 0;
	
	private void prepareQuestions() throws IOException {
		//List<String> temp;
		int randNum;
		BufferedInputFile bif = new BufferedInputFile();
		for (int i = 0; i < 11; i = i + 2) {
			this.questionsList.add(new Question(bif.read("q&a.txt", i), bif.read("q&a.txt", i + 1)));
		}
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
    }
    
    public void waitForPlayers() throws RemoteException {
    	boolean ready_all = false;
    	while (!ready_all) {
    		for(int i = 0; i < clientsList.size(); i++) {
				if (!clientsList.get(i).isReady()) {
					ready_all = false;
					break;
				}
				else {
					ready_all = true;
				}
    		}
    	}
    	try {
    		this.prepareQuestions();
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    public String getQuestion(int i) throws RemoteException {
    	return questionsList.get(i).getAsk();
    }
    
    public void giveAnswer(String answer, int userId) throws RemoteException {
    	this.answersList.put(userId, answer);
    	clientsList.get(userId).answere(true);
    }
   
    
	public void waitForAnswers() throws RemoteException {
		boolean answer_all = false;
    	while (!answer_all) {
    		for(int i = 0; i < clientsList.size(); i++) {
				if (!clientsList.get(i).answered()) {
					answer_all = false;
					break;
				}
				else {
					answer_all = true;
				}
    		}
    	};
	}

	
	public String getScoreTable(int i, int userId) throws RemoteException {
		String odp = new String("");
		System.out.println("User: " + userId);
		for(String ans: answersList.values()) {
			if(ans.equals(questionsList.get(i).getAnswer())) {
				odp = "Poprawna odpowiedź!!";
				clientsList.get(i).increasePoints(clientsList.size()+order++);
			}
			else {
				odp = "ZŁA odpowiedź!!";
			}
			
		}
		
		for(Integer li: answersList.keySet()) {
			System.out.println(li + ": user" + userId + answersList.get(li));
		}
		
		for(Player p: clientsList) {
			odp += p.getName() + ": " + p.getPoints() + " pkt\n";
		}
		answersList.clear();
		order = 0;
		for(Player p: clientsList) {
			p.answere(false);
		}
		return odp;
	}

	
	public void finishGame(int userId) throws RemoteException {
		clientsList.remove(clientsList.get(userId));		
	}

    
    
    
    
}
