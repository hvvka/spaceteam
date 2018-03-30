package com.hania;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ServerImpl implements Server {

    // todo make serializable
    private Set<Player> crew;
    private Captain captain;

    public ServerImpl() {
        super();
        crew = new HashSet<>();
    }

    @Override
    public Set<Player> showList() {
        return crew;
    }

    @Override
    public void kickOut(Player player) {
        crew.remove(player);
    }

    @Override
    public void register(Object object) {
        if (object instanceof Captain) {
            registerCapitan(object);
        } else if (object instanceof Player && !crew.contains(object)) {
            registerPlayer(object);
        }
    }

    private void registerCapitan(Object object) {
        captain = (Captain) object;
    }

    private void registerPlayer(Object object) {
        crew.add((Player) object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ServerImpl server = (ServerImpl) o;

        return (crew != null ? crew.equals(server.crew) : server.crew == null) && (captain != null ? captain.equals(server.captain) : server.captain == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (crew != null ? crew.hashCode() : 0);
        result = 31 * result + (captain != null ? captain.hashCode() : 0);
        return result;
    }
}

/*
    private static final String HOST = "host";
    private static final int PORT = 1099;

        try {
            LocateRegistry.createRegistry(PORT);
            Server server = new ServerImpl();
            Server remoteServer = (Server) UnicastRemoteObject.exportObject(server, PORT);
            LocateRegistry.getRegistry(HOST, PORT).rebind("Server", remoteServer);
        } catch (RemoteException e) {
            e.printStackTrace();
            exit(1);
        }
 */