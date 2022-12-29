package Autenticacao;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Autenticacao {
    private Map<String, String> loginData = new HashMap<>();
    private Lock loginDataLock = new ReentrantLock();

    public Autenticacao() {
        loginData.put("admin", "admin");
        loginData.put("client", "client");
    }

    public void handleAuthRequest() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1 -> Login");
            System.out.println("2 -> Register");
            System.out.println("Enter a number to select an option:");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                System.out.println("Enter your username:");
                String username = scanner.nextLine();
                System.out.println("Enter your password:");
                String password = scanner.nextLine();

                boolean loginSuccess = login(username, password);
                if (loginSuccess) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (input.equals("2")) {
                System.out.println("Enter a username:");
                String username = scanner.nextLine();
                System.out.println("Enter a password:");
                String password = scanner.nextLine();

                boolean registerSuccess = register(username, password);
                if (registerSuccess) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Username already in use. Please choose a different username.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
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
