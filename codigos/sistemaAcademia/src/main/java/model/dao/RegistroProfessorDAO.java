package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelRegistroProfessor;
import model.ModelUsuario;

public class RegistroProfessorDAO {

	private final EntityManager em;
	
	public RegistroProfessorDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um registro de professor dentro do banco de dados
     * @param registro de professor exige que seja passado um objeto do tipo registro de professor
	 * @throws SQLException 
     */
    public ModelRegistroProfessor insert(ModelRegistroProfessor registroProfessor) {
    	em.persist(registroProfessor);
    	return registroProfessor;
    	
    }
    
    /**
     * Atualiza um registro de professor no banco de dados
     * @param registro de professor
     * @return registro de professor
     */
    public ModelRegistroProfessor update(ModelRegistroProfessor registroProfessor){  
    	em.merge(registroProfessor);
    	return registroProfessor;    

    }
    
    /**
     * Insere ou atualiza um registro de professor do banco de dados
     * @param registro de professor
     * @return registro de professor para o metodo insert ou update
     */
    public ModelRegistroProfessor insertOrUpdate(ModelRegistroProfessor registroProfessor) {
		if(registroProfessor.getId() > 0) {
			return this.update(registroProfessor);
		}
		
		return this.insert(registroProfessor);
		
	}
    
    /**
     * Deleta um registro de professor do banco de dados pelo id do registro de professor passado
     * @param registro de professor
     * @return 
     */
    public void delete(ModelRegistroProfessor registroProfessor){
    	em.merge(registroProfessor);
    	em.remove(registroProfessor);
    	
    }
    
    /**
     * Retorna um arraylist com todos os registros de professores do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos os registros de professores do banco
     */
    public List<ModelRegistroProfessor> selectAll(ModelUsuario usuario){
    	String jpql = "SELECT u FROM registroprofessor as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de registros de professores
     */  
	@SuppressWarnings("unchecked")
	List<ModelRegistroProfessor> consulta(Query query) {
		List<ModelRegistroProfessor> registroProfessores;
		
		try {
			registroProfessores = query.getResultList();
		}catch(NoResultException e) {
			registroProfessores = new ArrayList<>();
		}
		
		return registroProfessores;
		
	}
}
