
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Klient {
	
	private String name;
	private Interfejs interfejs;
	private String nazwa;
	private int id;
	
	public Klient() {
		this.name = "Undefined";
	}
	
	public Klient(String name) {
		this.name = name;
	}
	
	public void registry(String nazwa) {
		try {
            interfejs = (Interfejs) Naming.lookup(nazwa);
            id = interfejs.registryClient(name);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
	}

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out
                    .println("parametry: //host/nazwa nazwa_użytkownika");
            System.exit(0);
        }
        String nazwa = args[0];
        String name = args[1];

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        Klient k = new Klient(name);
        k.registry(nazwa);
    }
}
