package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserSessionDTO;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/UserServlet")
public class AuthFilter extends HttpFilter implements Filter {
    public AuthFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        String operation = req.getParameter("operation");

        // acciones de registro, no necesitan estar logueado
        if ("new".equals(operation) || "add".equals(operation)) {
            chain.doFilter(request, response);
            return;
        }

        // el resto de operaciones necesita tener una sesion con un UserDTO
        HttpSession session = req.getSession(false);

        UserSessionDTO userDTO = null;

        if (session != null) {
            userDTO = (UserSessionDTO) session.getAttribute("user");
        }

        if (userDTO != null) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("login.jsp");
        }
        
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
