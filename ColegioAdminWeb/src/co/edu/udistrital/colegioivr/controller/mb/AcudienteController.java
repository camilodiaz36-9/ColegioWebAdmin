package co.edu.udistrital.colegioivr.controller.mb;

import co.edu.udistrital.colegioivr.model.entities.Acudiente;
import co.edu.udistrital.colegioivr.controller.mb.util.JsfUtil;
import co.edu.udistrital.colegioivr.controller.mb.util.JsfUtil.PersistAction;
import co.edu.udistrital.colegioivr.model.facades.ejb.AcudienteFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("acudienteController")
@SessionScoped
public class AcudienteController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6971447872328301922L;
	@EJB
    private co.edu.udistrital.colegioivr.model.facades.ejb.AcudienteFacade ejbFacade;
    private List<Acudiente> items = null;
    private Acudiente selected;

    public AcudienteController() {
    }

    public Acudiente getSelected() {
        return selected;
    }

    public void setSelected(Acudiente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AcudienteFacade getFacade() {
        return ejbFacade;
    }

    public Acudiente prepareCreate() {
        selected = new Acudiente();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("AcudienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("AcudienteUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("AcudienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Acudiente> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Acudiente getAcudiente(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Acudiente> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Acudiente> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @SuppressWarnings("rawtypes")
	@FacesConverter(forClass = Acudiente.class)
    public static class AcudienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AcudienteController controller = (AcudienteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "acudienteController");
            return controller.getAcudiente(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Acudiente) {
                Acudiente o = (Acudiente) object;
                return getStringKey(o.getIdAcudiente());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Acudiente.class.getName()});
                return null;
            }
        }

    }

}
