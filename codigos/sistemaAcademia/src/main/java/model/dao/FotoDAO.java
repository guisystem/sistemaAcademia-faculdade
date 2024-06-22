package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelFoto;
import model.ModelUsuario;

public class FotoDAO {

private final EntityManager em;
	
	public FotoDAO(EntityManager em) {
		this.em = em;
	}
	
	/**
     * Insere uma foto dentro do banco de dados
     * @param foto exige que seja passado um objeto do tipo foto
	 * @throws SQLException 
     */
	public ModelFoto insert(ModelFoto foto) {
    	em.persist(foto);
    	return foto;
    	
    }
    
    /**
     * Atualiza uma foto no banco de dados
     * @param foto
     * @return 
     */
    public ModelFoto update(ModelFoto foto){  
    	em.merge(foto);
    	return foto;    

    }
    
    /**
     * Insere ou atualiza uma foto do banco de dados
     * @param foto
     * @return foto para o metodo insert ou update
     */
    public ModelFoto insertOrUpdate(ModelFoto foto) {
		if(foto.getId() > 0) {
			return this.update(foto);
		}
		
		return this.insert(foto);
		
	}
    
    /**
     * Deleta uma foto do banco de dados pelo id da foto passado
     * @param foto
     * @return 
     */
    public void delete(ModelFoto foto){
    	em.merge(foto);
    	em.remove(foto);
    	
    }
    
    /**
     * Retorna um arraylist com todos as fotos do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos as fotos do banco
     */
    public List<ModelFoto> selectAll(ModelUsuario usuario){
    	String jpql = "SELECT u FROM foto as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de fotos
     */
    @SuppressWarnings("unchecked")
	List<ModelFoto> consulta(Query query) {
		List<ModelFoto> fotos;
		
		try {
			fotos = query.getResultList();
		}catch(NoResultException e) {
			fotos = new ArrayList<>();
		}
		
		return fotos;
		
	}
}
