package admin;

public class AdminDaoFactory {
    private static AdminDao dao;

    private AdminDaoFactory(){

    }

    public static AdminDao getAdminDao(){
        if(dao==null){
            dao = new AdminDaoImpl();
        }
        return dao;
    }
}
