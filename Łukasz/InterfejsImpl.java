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

    public InterfejsImpl() throws RemoteException {
        super(); //tutaj jest wywołany konstruktor UnicastRemoteObject
    }
    
    
    public int registryClient(String name) throws RemoteException {
    	clientsList.add(new Player(name));
    	for(Player l: clientsList) {
		System.out.println("Zarejestrowałem gracza o imieniu " + l.getName());
    	}
    	
    	return 2;
    }
    
    public List<Player> get() throws RemoteException {
	return clientsList;
    }
    
    
    
}
