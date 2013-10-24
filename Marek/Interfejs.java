/**
 *  Oto definicja interfejsu deklarującego metody, które mają być
 *  zdalnie udostępnione. Zdalny obiekt musi być instancją klasy
 *  implemetującej ten interfejs. Podczas budowy takiego interfejsu
 *  nalezy przestrzegać kilka reguł:
 *
 *   - interfejs musi być publiczny
 *   - interfejs musi dziedziczyć po interfejsie java.rmi.Remote;
 *   - każda zdalna metoda musi deklarować możliwość wyrzucenia
 *     wyjątku java.rmi.RemoteException
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Interfejs extends Remote {
    
    public int registryClient(String nazwa) throws RemoteException;
    public List<Player> getClientList() throws RemoteException;
    public void SetReady(int id) throws RemoteException;
}
