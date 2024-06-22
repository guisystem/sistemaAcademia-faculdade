package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelCustoBasico;
import model.ModelUsuario;

public class CustosBasicosDAO {

	private final EntityManager em;
	
	public CustosBasicosDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um custo dentro do banco de dados
     * @param custo exige que seja passado um objeto do tipo custo
	 * @throws SQLException 
     */
    public ModelCustoBasico insert(ModelCustoBasico custosBasicos) {
    	em.persist(custosBasicos);
    	return custosBasicos;
    	
    }
    
    /**
     * Atualiza um custo no banco de dados
     * @param custo
     * @return custo
     */
    public ModelCustoBasico update(ModelCustoBasico custosBasicos){  
    	em.merge(custosBasicos);
    	return custosBasicos;    

    }
    
    /**
     * Insere ou atualiza um custo do banco de dados
     * @param custo
     * @return custo para o metodo insert ou update
     */
    public ModelCustoBasico insertOrUpdate(ModelCustoBasico custosBasicos) {
    	if(custosBasicos.getId() == null) {
    		return this.insert(custosBasicos);
    	}
    	
		if(custosBasicos.getId() > 0) {
			return this.update(custosBasicos);
		}
		
		return this.insert(custosBasicos);
		
	}
    
    /**
     * Deleta um custo do banco de dados pelo id do custo passado
     * @param custo
     * @return
     */
    public void delete(ModelCustoBasico custosBasicos){
    	em.merge(custosBasicos);
    	em.remove(custosBasicos);
    	
    }

    /**
     * Deleta os custos do banco de dados em que o usuario(FK) for null
     * @param
     * @return 
     */
	public void deleteForUser() {
		String jpql = "SELECT u FROM custosbasicos as u WHERE u.usuario IS NULL";
		Query query = em.createQuery(jpql);
		
		for(ModelCustoBasico custos: consulta(query)) {
    		em.merge(custos);
    		em.remove(custos);
    	}
	}
    
	/**
     * Retorna um arraylist com todos os custos do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos os custos do banco
     */
    public List<ModelCustoBasico> selectAll(ModelUsuario usuario){
		String jpql = "SELECT u FROM custosbasicos as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de custos
     */
	@SuppressWarnings("unchecked")
	List<ModelCustoBasico> consulta(Query query) {
		List<ModelCustoBasico> custosBasicos;
		
		try {
			custosBasicos = query.getResultList();
		}catch(NoResultException e) {
			custosBasicos = new ArrayList<>();
		}
		
		return custosBasicos;
	}

	
}
