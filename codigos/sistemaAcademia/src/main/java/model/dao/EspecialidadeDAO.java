package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelEspecialidade;
import model.ModelUsuario;

public class EspecialidadeDAO {

	private final EntityManager em;

	public EspecialidadeDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere uma especialidade dentro do banco de dados
     * @param especialidade exige que seja passado um objeto do tipo especialidade
	 * @throws SQLException 
     */
    public ModelEspecialidade insert(ModelEspecialidade especialidade) {
    	em.persist(especialidade);
    	return especialidade;
    	
    }
    
    /**
     * Atualiza uma especialidade no banco de dados
     * @param especialidade
     * @return especialidade
     */
    public ModelEspecialidade update(ModelEspecialidade especialidade){  
    	em.merge(especialidade);
    	return especialidade;    

    }
    
    /**
     * Insere ou atualiza uma especialidade do banco de dados
     * @param especialidade
     * @return especialidade para o metodo insert ou update
     */
    public ModelEspecialidade insertOrUpdate(ModelEspecialidade especialidade) {
    	if(especialidade.getId() == null) {
    		return this.insert(especialidade);
    		
    	}
    	
		if(especialidade.getId() > 0) {
			return this.update(especialidade);
		}
		
		return this.insert(especialidade);
		
	}
    
    /**
     * Deleta um especialidade do banco de dados pelo id da especialidade passado
     * @param especialidade
     * @return 
     */
    public void delete(ModelEspecialidade especialidade){
    	em.merge(especialidade);
    	em.remove(especialidade);
    	
    }

    /**
     * Deleta as especialidades do banco de dados em que o usuario(FK) for null
     * @param
     * @return 
     */
	public void deleteForUser() {
		String jpql = "SELECT u FROM especialidade as u WHERE u.usuario IS NULL";
		Query query = em.createQuery(jpql);
		
		for(ModelEspecialidade especialidades: consulta(query)) {
    		em.merge(especialidades);
    		em.remove(especialidades);
    	}
	}
    
	/**
     * Retorna um arraylist com todos as especialidades do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos as especialidades do banco
     */
    public List<ModelEspecialidade> selectAll(ModelUsuario usuario){
		String jpql = "SELECT u FROM especialidade as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de especialidades
     */
	@SuppressWarnings("unchecked")
	List<ModelEspecialidade> consulta(Query query) {
		List<ModelEspecialidade> especialidades;
		
		try {
			especialidades = query.getResultList();
		}catch(NoResultException e) {
			especialidades = new ArrayList<>();
		}
		
		return especialidades;
	}

}
