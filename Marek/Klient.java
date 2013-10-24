
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Klient {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out
                    .println("parametry: //host/nazwa nazwa_użytkownika");
            System.exit(0);
        }
        String file_name = args[0];
        String user_name = args[1];

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
	
	try {
            interfejs = (Interfejs) Naming.lookup(file_name);
            id = interfejs.registryClient(user_name);
            lista_graczy = interfejs.getClientList();
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
	}
    }
}
