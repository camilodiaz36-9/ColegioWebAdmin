package co.edu.udistrital.colegioivr.controller.mb;

import co.edu.udistrital.colegioivr.model.entities.Solicitud;
import co.edu.udistrital.colegioivr.controller.mb.util.JsfUtil;
import co.edu.udistrital.colegioivr.controller.mb.util.JsfUtil.PersistAction;
import co.edu.udistrital.colegioivr.model.facades.ejb.SolicitudFacade;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("solicitudController")
@SessionScoped
public class SolicitudController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4794514369620629096L;
	@EJB
	private co.edu.udistrital.colegioivr.model.facades.ejb.SolicitudFacade ejbFacade;
	private List<Solicitud> items = null;
	private Solicitud selected;

	public SolicitudController() {
	}

	public Solicitud getSelected() {
		return selected;
	}

	public void setSelected(Solicitud selected) {
		this.selected = selected;
	}

	protected void setEmbeddableKeys() {
	}

	protected void initializeEmbeddableKey() {
	}

	private SolicitudFacade getFacade() {
		return ejbFacade;
	}

	public Solicitud prepareCreate() {
		selected = new Solicitud();
		initializeEmbeddableKey();
		return selected;
	}

	public void create() {
		persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SolicitudCreated"));
		if (!JsfUtil.isValidationFailed()) {
			items = null; // Invalidate list of items to trigger re-query.
		}
	}

	public void update() {
		persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SolicitudUpdated"));
	}

	public void destroy() {
		persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SolicitudDeleted"));
		if (!JsfUtil.isValidationFailed()) {
			selected = null; // Remove selection
			items = null; // Invalidate list of items to trigger re-query.
		}
	}

	public List<Solicitud> getItems() {
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
					JsfUtil.addErrorMessage(ex,
							ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
				}
			} catch (Exception ex) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
				JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void descargarReporte() {
		final Font SMALL_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
		final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);

		Document document = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment; filename=" + this.getSelected().getTramite().getNombre()
				+ this.getSelected().getEstudiante().getNumeroIdentificacion() + " " + hashCode() + ".pdf");

		try {
            try (ServletOutputStream servletOutputStream = response.getOutputStream()) {
                response.setContentType("application/pdf");
                facesContext.responseComplete();
                try {
                	document = new Document(PageSize.A4);
        			PdfWriter.getInstance(document, servletOutputStream);
        			document.open();
        			document.addTitle(this.getSelected().getTramite().getNombre());
        			document.addSubject(this.getSelected().getTramite().getNombre());
        			document.addAuthor("SAC");
        			Paragraph preface = new Paragraph();
        			addEmptyLine(preface, 3);
        			preface.add(new Phrase("Trámite: ", NORMAL_FONT));
        			preface.add(new Phrase(this.getSelected().getTramite().getNombre(), NORMAL_FONT));
        			addEmptyLine(preface, 1);
        			preface.add(new Phrase("Fecha: ", SMALL_BOLD));
        			preface.add(new Phrase(new Date().toLocaleString(), NORMAL_FONT));
        			addEmptyLine(preface, 1);
        			preface.add(new Phrase("El estudiante: ", SMALL_BOLD));
        			preface.add(new Phrase(
        					this.getSelected().getEstudiante().getNombre() + " " + this.getSelected().getEstudiante().getApellido(),
        					NORMAL_FONT));
        			addEmptyLine(preface, 1);
        			preface.add(new Phrase("Se encuentra matriculado en la institución.", NORMAL_FONT));
        			preface.setAlignment(Element.ALIGN_CENTER);
        			document.add(preface);
        			if (null != document) {
        				document.close();
        			}
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                servletOutputStream.flush();
                servletOutputStream.close();
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
		
	}

	public void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public Solicitud getSolicitud(java.lang.Integer id) {
		return getFacade().find(id);
	}

	public List<Solicitud> getItemsAvailableSelectMany() {
		return getFacade().findAll();
	}

	public List<Solicitud> getItemsAvailableSelectOne() {
		return getFacade().findAll();
	}

	@SuppressWarnings("rawtypes")
	@FacesConverter(forClass = Solicitud.class)
	public static class SolicitudControllerConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			SolicitudController controller = (SolicitudController) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "solicitudController");
			return controller.getSolicitud(getKey(value));
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
			if (object instanceof Solicitud) {
				Solicitud o = (Solicitud) object;
				return getStringKey(o.getIdSolicitud());
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						"object {0} is of type {1}; expected type: {2}",
						new Object[] { object, object.getClass().getName(), Solicitud.class.getName() });
				return null;
			}
		}

	}

}
