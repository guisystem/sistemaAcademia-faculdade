/*
g * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelUsuario;
import servico.Criptografar;

/**
 *
 * @author guilherme
 */
public class UsuarioDAO {
	
	private final EntityManager em;
	
	public UsuarioDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um usuario dentro do banco de dados
     * @param usuario exige que seja passado um objeto do tipo usuario
	 * @throws SQLException 
     */
    public ModelUsuario insert(ModelUsuario usuario) {
    	em.persist(usuario);
    	return usuario;
    	
    }
    
    /**
     * Atualiza um usuario no banco de dados
     * @param usuario
     * @return usuario
     */
    public ModelUsuario update(ModelUsuario usuario){  
    	em.merge(usuario);
    	return usuario;    

    }
    
    /**
     * Insere ou atualiza um usuario do banco de dados
     * @param usuario
     * @return usuario para o metodo insert ou update
     */
    public ModelUsuario insertOrUpdate(ModelUsuario usuario) {
    	
		if(usuario.getId() > 0) {
			return this.update(usuario);
		}
		
		return this.insert(usuario);
		
	}
    
    /**
     * Deleta um usuario do banco de dados pelo id do usuario passado
     * @param usuario
     * @return 
     */
    public void delete(ModelUsuario usuario){
    	em.merge(usuario);
    	em.remove(usuario);
    	
    }
    
    /**
     * Retorna um arraylist com todos os usuarios do banco de dados
     * @param usuario
     * @return uma lista com todos os usuarios do banco
     */
    public List<ModelUsuario> selectAll(){
		String jpql = "SELECT u FROM usuario as u";
		Query query = em.createQuery(jpql);
		
		return consulta(query);
		
	}
    
    /**
     * Faz uma busca por todos os usuarios no dados e retorna true se o usuario passado tem o email e senha no banco
     * @param usuario
     * @return verdadeiro ou falso
     */
	public boolean selectPorUsuarioESenha(ModelUsuario usuarioAutenticado){
		String jpql = "SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario and u.senha = :pSenha";
		
		Query query = em.createQuery(jpql);
		
		query.setParameter("pUsuario", usuarioAutenticado.getNomeUsuario());
		query.setParameter("pSenha", Criptografar.criptografar(usuarioAutenticado.getSenha()));
		
		return !consulta(query).isEmpty();
		
	}
	
	/**
     * Faz uma busca por todos os usuarios no dados e retorna um usuario se o usuario passado tem o email e senha no banco
     * @param usuario
     * @return usuario
     */
	public ModelUsuario selectUsuarioPorUsuarioESenha(ModelUsuario usuarioAutenticado){
		String jpql = "SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario and u.senha = :pSenha";
		
		Query query = em.createQuery(jpql);
		
		query.setParameter("pUsuario", usuarioAutenticado.getNomeUsuario());
		query.setParameter("pSenha", Criptografar.criptografar(usuarioAutenticado.getSenha()));
		
		return consulta(query).isEmpty() ? null : consulta(query).get(0);
		
	}
    
	/**
     * Faz uma busca por todos os usuarios no dados e verifica se já existe um usuario com o nome, email ou cpf igual do usuario passado
     * @param usuario
     * @return inteiro
     */
	public int selectPorNomeEmailCpf(ModelUsuario usuario) {
		String jpql = "SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario";
		String jpql2 = "SELECT u FROM usuario as u WHERE u.cpf = :pCpf";
		String jpql3 = "SELECT u FROM usuario as u WHERE u.email = :pEmail";
		
		Query query = em.createQuery(jpql);
		Query query2 = em.createQuery(jpql2);
		Query query3 = em.createQuery(jpql3);
		
		query.setParameter("pUsuario", usuario.getNomeUsuario());
		query2.setParameter("pCpf", Criptografar.criptografar(usuario.getCpf()));
		query3.setParameter("pEmail", Criptografar.criptografar(usuario.getEmail()));
		
		if(!consulta(query).isEmpty()) {
			return 1;
		}
		
		if(!consulta(query2).isEmpty()) {
			return 2;
		}
		
		if(!consulta(query3).isEmpty()) {
			return 3;
		}
		
		return 0;
	}
    
	/**
	 * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
	 * @param query
	 * @return uma lista de usuarios
	 */
	@SuppressWarnings("unchecked")
	List<ModelUsuario> consulta(Query query) {
		List<ModelUsuario> usuarios;
		
		try {
			usuarios = query.getResultList();
		}catch(NoResultException e) {
			usuarios = new ArrayList<>();
		}
		
		return usuarios;
		
	}
	
}
