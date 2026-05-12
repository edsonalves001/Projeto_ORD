package br.com.ord.ORD.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OrdInterceptor implements HandlerInterceptor {
    //Este é o interceptor, ele vai servir para bloquear o usuário de acessar as paginas sem estar logado, ele roda antes do controller, e só permite acesso as paginas caso esteja logado.

        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler) throws Exception {
            HttpSession session = request.getSession(false);

            if(session == null){
                response.sendRedirect("/login");
                return false;
            }
            String alunoId = (String) session.getAttribute("usuarioId");

            if (alunoId == null) {
                response.sendRedirect("/login");
                return false;
            }

            return true;
        }
    }



