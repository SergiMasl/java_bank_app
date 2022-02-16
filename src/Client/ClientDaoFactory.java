package Client;

public class ClientDaoFactory {
    private static ClientDao dao;

    private ClientDaoFactory(){

    }

    public static ClientDao getClientDao(){
        if(dao==null){
            dao = new ClientDaoImpl();
        }
        return dao;
    }
}
