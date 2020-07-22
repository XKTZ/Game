package test;

import xktz.game.page.GamePage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TestClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        GamePage page = new GamePage(8889);
    }
}
