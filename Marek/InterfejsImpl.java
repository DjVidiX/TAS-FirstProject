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
	private List clientsList= new LinkedList();

    public InterfejsImpl() throws RemoteException {
        super(); //tutaj jest wywołany konstruktor UnicastRemoteObject
    }

    //definicja zdalnej metody

    public long obliczNWD(long a, long b) throws RemoteException {
        System.out.println("Otrzymałem liczby " + a + " i " + b);
        while (a != b)
            if (a > b)
                a = a - b;
            else
                b = b - a;

        return a;
    }
    
    public int registryClient(String name) throws RemoteException {
    	this.clientsList.add(name);
    	System.out.print("Zarejestrowałem gracza o imieniu " + name);
    	
    	return 1;
    }
}
