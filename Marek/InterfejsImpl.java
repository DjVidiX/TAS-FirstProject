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
	private static List<String> clientsList= new LinkedList<String>();

    public InterfejsImpl() throws RemoteException {
        super(); //tutaj jest wywołany konstruktor UnicastRemoteObject
    }
    
    
    public int registryClient(String name) throws RemoteException {
    	this.clientsList.add(name);
    	for(String l: clientsList) {
		System.out.println("Zarejestrowałem gracza o imieniu " + l);
    	}
    	
    	return clientsList.indexOf(name);
    }
}
