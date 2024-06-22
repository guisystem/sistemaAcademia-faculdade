package servico;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author guilherme
 */
public class JPAUtil {
	
    private static EntityManagerFactory emf;

    public JPAUtil() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("mysql");
        }
    }
    
    /**
     * Retorna um Entity Manager de Conexao com o banco de dados
     * @return
     */
    public EntityManager getEntityManager() {
            return emf.createEntityManager();
    }

    public JPAUtil(EntityManagerFactory factory) {
        emf = factory;
    }
    
}
