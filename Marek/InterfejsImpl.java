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

    //konstruktor zdalnego obiektu
	private static List<Player> clientsList = new LinkedList<Player>();
	
	private static int counter = 0;

    public InterfejsImpl() throws RemoteException {
        super(); //tutaj jest wywołany konstruktor UnicastRemoteObject
    }
    
    
    public int registryClient(String name) throws RemoteException {
	int id = 0;
    	clientsList.add(new Player(name, counter++));
    	for(Player l: clientsList) {
		id = l.getId();
		System.out.println("Zarejestrowałem gracza o imieniu " + l.getName() + " i id= " + id);
    	}

    	return id;
    }
    
    public void getClientList() throws RemoteException {
    }
    
    public void SetReady(int user_id) throws RemoteException {
	for(Player l: clientsList) {
		if(l.getId() == user_id) {
			l.setReady();
		}
	}
    }
    
    
    
}
