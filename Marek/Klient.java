import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.*;

public class Klient {

	private int id;
	private String ready;
	private Interfejs interf = null;
	private List<Player> players_list = new LinkedList<Player>();

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
		Klient k = new Klient();
            	k.interf = (Interfejs) Naming.lookup(file_name);
            	k.id = k.interf.registryClient(user_name);
            	k.players_list = k.interf.getClientList();
	    	System.out.println("Gdy bedziesz gotów, by rozpocząć grę, wpisz \"r\"\n");
	    	Scanner in = new Scanner(System.in);
	    	k.ready = in.next();
		k.interf.SetReady(k.id);
		boolean ready_all = false;
		while (!ready_all) {
			for(int i = 0; i < k.players_list.size(); i++) {
			if (!k.players_list.get(i).isReady()) break;
			if (i == k.players_list.size() - 1 && k.players_list.get(i).isReady()) ready_all = true;
			}
		}	
		//k.interf.GiveAnswer(k.id);
	        
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
	}
}
