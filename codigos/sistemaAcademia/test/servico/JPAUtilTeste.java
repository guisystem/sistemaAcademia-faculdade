package servico;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JPAUtilTeste {

	private EntityManagerFactory emf;
    private EntityManager entityManager;
    private JPAUtil jpaUtil;

    @BeforeEach
    public void setUp() {
    	emf = Mockito.mock(EntityManagerFactory.class);
    	entityManager = Mockito.mock(EntityManager.class);

        when(emf.createEntityManager()).thenReturn(entityManager);

        jpaUtil = new JPAUtil(emf);
    }

    @Test
    public void testarGetEntityManager() {
        EntityManager entityManager = jpaUtil.getEntityManager();

        assertNotNull(entityManager);
        verify(emf, times(1)).createEntityManager();
    }

}
