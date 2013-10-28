/**
 *  Serwer
 *  - stworzyć i zainstalować Security Manager
 *  - stworzyć co najmiej jedną instację zdalnego obiektu
 *  - zarejestrować obiekt w RMI Remote Object Registry
 */

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Serwer {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("parametry: //host/nazwa");
            System.exit(0);
        }
        String nazwa = args[0];

        //tworzenie i zainstalacja Security Manager

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        try {
            //tworzenie zdalnego obiektu

            InterfejsImpl impl = new InterfejsImpl();

            //rejestracja zdalnego obiektu

            Naming.rebind(nazwa, impl);
	
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("Serwer wystartował.");
    }
}
