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
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter(urlPatterns = {"/CategoryServlet", "/ItemServlet", "/adminHome.jsp", "/UserServlet"})
public class AdminFilter extends HttpFilter implements Filter {
       
    public AdminFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String operation = req.getParameter("operation");
        
        //si es UserServlet filtramos solo por las operaciones
        
        if (req.getRequestURI().endsWith("/UserServlet")) {
            // si NO son list o changeRole lo dejamos pasar y que se encarge authFilter
            if (!"list".equals(operation) && !"changeRole".equals(operation)) {
                chain.doFilter(request, response);
                return;
            }
        }
        
        
        HttpSession session = req.getSession(false);

        UserSessionDTO userDTO = null;

        if (session != null) {
            userDTO = (UserSessionDTO) session.getAttribute("user");
        }

        if (userDTO != null && "admin".equals(userDTO.getRole())) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect("index.jsp");
        }

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
