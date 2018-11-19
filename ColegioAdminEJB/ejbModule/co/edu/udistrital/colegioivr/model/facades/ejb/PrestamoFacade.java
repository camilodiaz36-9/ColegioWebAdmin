/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.colegioivr.model.facades.ejb;

import co.edu.udistrital.colegioivr.model.entities.Prestamo;
import co.edu.udistrital.colegioivr.model.facades.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author camilo.diaz
 */
@Stateless
public class PrestamoFacade extends AbstractFacade<Prestamo> {

    @PersistenceContext(unitName = "ColegioAdminEJB")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrestamoFacade() {
        super(Prestamo.class);
    }
    
}
