package co.edu.udistrital.colegioivr.controller.mb;

import co.edu.udistrital.colegioivr.model.entities.Extension;
import co.edu.udistrital.colegioivr.controller.mb.util.JsfUtil;
import co.edu.udistrital.colegioivr.controller.mb.util.JsfUtil.PersistAction;
import co.edu.udistrital.colegioivr.model.facades.ejb.ExtensionFacade;

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

@Named("extensionController")
@SessionScoped
public class ExtensionController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 989709861844941040L;
	@EJB
    private co.edu.udistrital.colegioivr.model.facades.ejb.ExtensionFacade ejbFacade;
    private List<Extension> items = null;
    private Extension selected;

    public ExtensionController() {
    }

    public Extension getSelected() {
        return selected;
    }

    public void setSelected(Extension selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ExtensionFacade getFacade() {
        return ejbFacade;
    }

    public Extension prepareCreate() {
        selected = new Extension();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ExtensionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ExtensionUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ExtensionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Extension> getItems() {
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

    public Extension getExtension(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Extension> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Extension> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @SuppressWarnings("rawtypes")
	@FacesConverter(forClass = Extension.class)
    public static class ExtensionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ExtensionController controller = (ExtensionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "extensionController");
            return controller.getExtension(getKey(value));
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
            if (object instanceof Extension) {
                Extension o = (Extension) object;
                return getStringKey(o.getIdExtension());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Extension.class.getName()});
                return null;
            }
        }

    }

}
