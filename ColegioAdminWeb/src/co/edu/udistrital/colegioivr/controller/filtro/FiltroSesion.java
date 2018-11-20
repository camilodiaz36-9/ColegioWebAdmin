package co.edu.udistrital.colegioivr.controller.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.udistrital.colegioivr.controller.mb.SesionController;

@WebFilter(filterName = "FiltroSesion", urlPatterns = {"*.jsf"})
public class FiltroSesion implements Filter {

	public FiltroSesion() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // Obtengo el bean que representa el usuario desde el scope sesión
        SesionController sesionBean = (SesionController) req.getSession(true).getAttribute("sesionController");

        //Proceso la URL que está requiriendo el cliente
        String urlStr = req.getRequestURL().toString().toLowerCase();

        //Si no requiere protección continúo normalmente.
        if (noProteger(urlStr)) {
            chain.doFilter(request, response);
            return;
        }

        //El usuario no está logueado, lo redirige al inicio
        if (sesionBean == null || !sesionBean.isLogueado()) {
            res.sendRedirect(req.getContextPath() + "/index.jsf");
            return;
        } else {
        	if(urlStr.contains("pages")) {
        		chain.doFilter(request, response);
                return;
        	}
        }
        
	}
	
	private boolean noProteger(String urlStr) {
        if (urlStr.endsWith("index.jsf")) { //Página inicial
            return true;
        }
        if (urlStr.contains("resources")) { //Recursos de la página
            return true;
        }
        if (urlStr.contains("templates")) { //Recursos de la página
            return true;
        }
        if (urlStr.contains("ayuda")) { //Páginas de ayuda
            return true;
        }
        if (urlStr.contains("errores")) { //Páginas de error
            return true;
        }
        return urlStr.contains("/javax.faces.resource/");
    }

}
