import java.util.Map;
import java.util.HashMap;

public class Autenticacao {

    private Map<String, String> usersRegistados = new HashMap<>();

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void register(String username, String password) {
        usersRegistados.put(username, password);
    }

    public boolean login(String username, String password) {
        String passGuardada = usersRegistados.get(username);
        return (passGuardada != null && passGuardada.equals(password));
    }
}
