import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.*;

public class Klient {

	private int id;
	private String ready = null;
	private Interfejs interf = null;
	private LinkedList<Player> players_list = new LinkedList<Player>();
	private Timer timer;
	//private GetAnswerTask GAT;

	/*class GetAnswerTask extends TimerTask {

		private String answer;

		@Override
        	public void run() {
			Scanner temp = new Scanner(System.in);
    			answer = temp.next();
		    if (answer.equals("dupa")) {
		    	timer.cancel();
		    	System.exit(0);
		    }
		}
	}*/

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
	    	while (!k.ready.equals("r")) {
	    		Scanner in = new Scanner(System.in);
	    		k.ready = in.next();
	    	}
	    	System.out.println("Oczekiwanie na pozostalych graczy...\n");
			k.interf.SetReady(k.id);
			boolean ready_all = false;
			while (!ready_all) {
				for(int i = 0; i < k.players_list.size(); i++) {
				if (!k.players_list.get(i).isReady()) break;
				if (i == k.players_list.size() - 1 && k.players_list.get(i).isReady()) ready_all = true;
				}
		}
		System.out.println("Gra rozpoczęta.\n");
		
		//k.timer = new Timer();
		//k.timer.schedule(k.GAT, 0, 2000);
	        
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
	}
}
