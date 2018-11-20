/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.colegioivr.model.facades.ejb;

import co.edu.udistrital.colegioivr.model.entities.Usuario;
import co.edu.udistrital.colegioivr.model.facades.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author camilo.diaz
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "ColegioAdminEJB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }
    
    public Usuario login(Usuario usuarioLogin) {
    	String jpql = "SELECT u FROM Usuario u WHERE u.correo = :correo AND u.password = :password AND (u.rol.nombre = 'Rector' or u.rol.nombre = 'Secretaria')";
    	Query q = em.createQuery(jpql);
    	q.setParameter("correo", usuarioLogin.getCorreo());
    	q.setParameter("password", usuarioLogin.getPassword());
    	Usuario usuarioLogueado = null;
    	try {
    		usuarioLogueado = (Usuario) q.getSingleResult();
    	} catch(NoResultException nre) {
    		System.err.println("UsuarioFacade.login() --> No hay resultados: " + nre.getLocalizedMessage());
    		usuarioLogueado = null;
    	}
		return usuarioLogueado;
    }
    
}
