
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Klient {
	
	private String name;
	private Interfejs interfejs;
	private String nazwa;
	
	public Klient() {
		this.name = "Undefined";
	}
	
	public Klient(String name) {
		this.name = name;
	}
	
	public void registry(String nazwa, long a, long b) {
		try {
            this.interfejs = (Interfejs) Naming.lookup(nazwa);
            long wynik = this.interfejs.obliczNWD(a, b);
            System.out.println("NWD (" + a + "," + b + ") = " + wynik);
            
            int id = this.interfejs.registryClient("Łukasz gej");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
	}

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out
                    .println("parametry: //host/nazwa pierwsza_liczba druga_liczba");
            System.exit(0);
        }
        String nazwa = args[0];

        long a = Long.parseLong(args[1]);
        long b = Long.parseLong(args[2]);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        Klient k = new Klient();
        k.registry(nazwa, a, b);
    }
}
