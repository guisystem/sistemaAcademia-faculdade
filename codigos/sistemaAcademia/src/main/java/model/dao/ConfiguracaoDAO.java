package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelConfiguracao;
import model.ModelUsuario;

public class ConfiguracaoDAO {

private final EntityManager em;
	
	public ConfiguracaoDAO(EntityManager em) {
		this.em = em;
	}
	
	/**
     * Insere uma configuracao dentro do banco de dados
     * @param configuracao exige que seja passado um objeto do tipo configuracao
	 * @throws SQLException 
     */
	public ModelConfiguracao insert(ModelConfiguracao configuracao) {
		em.persist(configuracao);
		return configuracao;
	}
	
    /**
     * Atualiza uma configuracao no banco de dados
     * @param configuracao
     * @return configuracao
     */
	public ModelConfiguracao update(ModelConfiguracao configuracao) {
		em.merge(configuracao);
		return configuracao;
	}
	
	/**
	 * Insere ou atualiza uma configuracao do banco de dados
	 * @param configuracao
	 * @return configuracao para o metodo insert ou update
	 */
	public ModelConfiguracao insertOrUpdate(ModelConfiguracao configuracao) {	
		if(configuracao.getId() > 0) {
			return this.update(configuracao);
		}
		
		return this.insert(configuracao);
	}
	
	/**
     * Deleta uma configuracao do banco de dados pelo id da configuracao passado
     * @param configuracao
     * @return 
     */
	public void delete(ModelConfiguracao configuracao) {
		em.merge(configuracao);
		em.remove(configuracao);
	}
	
	/**
     * Retorna um arraylist com todos as configuracoes do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos as configuracoes do banco
     */
	public List<ModelConfiguracao> selectAll(ModelUsuario usuario){
		String jpql = "SELECT u FROM configuracao as u WHERE u.usuario.id = :pIdUsuario";
		Query query = em.createQuery(jpql);
		
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
	/**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de configuracoes
     */
	@SuppressWarnings("unchecked")
	List<ModelConfiguracao> consulta(Query query) {
		List<ModelConfiguracao> configuracao;
		
		try {
			configuracao = query.getResultList();
		}catch(NoResultException e) {
			configuracao = new ArrayList<>();
		}
		
		return configuracao;
	}
	
}
