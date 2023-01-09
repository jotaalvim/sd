public class Menu {
    public static void opcoes(){
        System.out.println("COMANDOS DISPONIVEIS:\n - register <user> <password>\n     → insere um registo de um user novo no servidor     \n      • register joao179 123pass456\n \n - login <user> <password>\n     → permite fazer login na aplicaçao e ter acesso a outras funcionalidades\n      • login joao179 123pass456\n \n - list <x> <y> \n     → mostra que trotinetes estao livres num raio de 2 unidades de distancia\n      • list 0 0\n \n - request <x> <y> <distancia>\n     → aluga uma trotinete a uma distancia de determinadas coodenadas\n      • request 3 4 2\n \n - park <x> <y> <codigo>\n     → estaciona uma trotinete em determinadas coordenadas\n      • park 10 13 0\n \n - logout \n     → sai da aplicacao \n \n- help\n     → mostra comandos disponiveis");
    }
}
