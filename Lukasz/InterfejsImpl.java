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

	private static List<Player> clientsList = new LinkedList<Player>();
	private static List<Question> questionsList = new LinkedList<Question>();
	private String[] answer;
	
	private void prepareQuestions() throws IOException {
		BufferedInputFile bif = new BufferedInputFile();
		for (int i = 0; i < 61; i = i + 2) {
			this.questionsList.add(new Question(bif.read("q&a.txt", i), bif.read("q&a.txt", i + 1)));
		}
	}

    public InterfejsImpl() throws RemoteException {
        super();
    }
    
    
    public int registryClient(String name) throws RemoteException {
    	int id = 0;
    	clientsList.add(new Player(name));
    	for(Player l: clientsList) {
    		id = l.getId();
    	}
    	System.out.println("Zarejestrowałem gracza o imieniu " + clientsList.get(clientsList.size()-1).getName() + " i id= " + id);
    	
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
    		this.answer = new String[clientsList.size()];
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    public String getQuestion(int i) throws RemoteException {
    	return questionsList.get(i).getAsk();
    }
    
    public void giveAnswer(String answer, int userId) throws RemoteException {
    	this.answer[userId] = answer;
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
		
		if(answer[userId].toLowerCase().equals(questionsList.get(i).getAnswer().toLowerCase())) {
			odp = "D O B R Z E !!!\n\n";
			clientsList.get(userId).increasePoints();
		}
		else {
			odp += "Ź L E !!!\n\n";
			odp += "Dobra odpowiedź to: " + questionsList.get(i).getAnswer() + "\n\n";
			odp += "Obecny stan punktowy: \n";
		}
		long cur = System.currentTimeMillis();
		while(System.currentTimeMillis() - cur < 500); 
				
		for(Player p: clientsList) {
			odp += p.getName() + ": " + p.getPoints() + " pkt\n";
		}
		
		for(int h=0; h<clientsList.size(); h++) {
			answer[h]="";
		}
		for(Player p: clientsList) {
			p.answere(false);
		}
		return odp;
	}

	
	public void finishGame(int userId) throws RemoteException {
		try {
			clientsList.clear();	
			System.exit(0);
			System.out.println("Koniec pracy serwera!");
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}

    
    
    
    
}
