package co.edu.udistrital.colegioivr.controller.mb;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.codec.digest.DigestUtils;

import co.edu.udistrital.colegioivr.controller.mb.util.JsfUtil;
import co.edu.udistrital.colegioivr.model.entities.Usuario;
import co.edu.udistrital.colegioivr.model.facades.ejb.UsuarioFacade;

import java.io.Serializable;

@Named("sesionController")
@SessionScoped
public class SesionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4051390508237133180L;
	
	@EJB
	private UsuarioFacade usuarioService;
	
	private String correoUsuario;
	private String contrasenaUsuario;
	private Usuario usuarioLogin;
	private boolean logueado;
	
	public String login() {
		usuarioLogin = new Usuario();
		
		usuarioLogin.setCorreo(getCorreoUsuario());
		usuarioLogin.setPassword(DigestUtils.sha1Hex(getContrasenaUsuario()));
		
		usuarioLogin = usuarioService.login(usuarioLogin);
		
		if(usuarioLogin != null) {
			return "/pages/administration";
		} else {
			JsfUtil.addErrorMessage("Credenciales no válidas.");
			correoUsuario = null;
			contrasenaUsuario = null;
			return null;
		}
	}
	
	public String logout() {
		return "/index.xhtml?faces-redirect=true";
	}

	public String getContrasenaUsuario() {
		return contrasenaUsuario;
	}

	public void setContrasenaUsuario(String contrasenaUsuario) {
		this.contrasenaUsuario = contrasenaUsuario;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public boolean isLogueado() {
		return logueado;
	}

	public void setLogueado(boolean logueado) {
		this.logueado = logueado;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}
	
}
