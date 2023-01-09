import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Autenticacao {
    private Map<String, String> loginData;
    private List<String> online;
    private Lock loginDataLock = new ReentrantLock();

    public Autenticacao() {
        this.loginData = new HashMap<String,String>();
        this.online = new ArrayList<String>();

        loginData.put("admin", "admin");
        loginData.put("client", "client");
    }

    public void online(String u) {
        loginDataLock.lock();
        try {
            this.online.add(u);
        }
        finally {
            loginDataLock.unlock();
        }
    }
    
    public void offline(String u) {
        loginDataLock.lock();
        try {
            this.online.remove(u);
        }
        finally {
            loginDataLock.unlock();
        }
    }

    public Boolean logado(String u) {
        return online.contains(u);
    }

    public boolean login(String username, String password) {
        loginDataLock.lock();
        try {
            String storedPassword = loginData.get(username);
            if (storedPassword != null && storedPassword.equals(password)) {
                return true;
            } else {
                return false;
            }
        } finally {
            loginDataLock.unlock();
        }
    }

    public boolean register(String username, String password) {
        loginDataLock.lock();
        try {
            // Verificar se o username existe
            if (loginData.containsKey(username)) {
                return false;
            }
            // Adicionar novas credenciais ao Map
            loginData.put(username, password);
            return true;
        } finally {
            loginDataLock.unlock();
        }
    }
}
