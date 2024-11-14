
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

// Remote interface
interface HotelReservationSystem extends Remote {
    String reserveRoom(String guestName) throws RemoteException;
    String checkAvailability() throws RemoteException;
}

// Implementation of the remote interface
class HotelReservationSystemImpl extends UnicastRemoteObject implements HotelReservationSystem {
    private final Map<String, Boolean> rooms;

    protected HotelReservationSystemImpl() throws RemoteException {
        rooms = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            rooms.put("Room " + i, true); // All rooms initially available
        }
    }

    @Override
    public synchronized String reserveRoom(String guestName) throws RemoteException {
        for (Map.Entry<String, Boolean> entry : rooms.entrySet()) {
            if (entry.getValue()) {
                rooms.put(entry.getKey(), false);
                return guestName + " has reserved " + entry.getKey();
            }
        }
        return "No rooms available.";
    }

    @Override
    public synchronized String checkAvailability() throws RemoteException {
        long availableRooms = rooms.values().stream().filter(available -> available).count();
        return "Available rooms: " + availableRooms;
    }
}

// Server setup
public class HotelReservationServer {
    public static void main(String[] args) {
        try {
            HotelReservationSystemImpl reservationSystem = new HotelReservationSystemImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("HotelReservationSystem", reservationSystem);
            System.out.println("Hotel Reservation System is ready.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

// Client
class HotelReservationClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            HotelReservationSystem reservationSystem = (HotelReservationSystem) registry.lookup("HotelReservationSystem");

            System.out.println(reservationSystem.checkAvailability());
            System.out.println(reservationSystem.reserveRoom("Alice"));
            System.out.println(reservationSystem.reserveRoom("Bob"));
            System.out.println(reservationSystem.checkAvailability());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
