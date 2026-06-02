package br.com.ord.ORD.config;
import br.com.ord.ORD.Interceptor.OrdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private OrdInterceptor interceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).excludePathPatterns(
                        "/",
                        "/cadastro",
                        "/index",
                        "/login",
                        "/login/**",
                        "/cadastro/**",
                        "/api/login",
                        "/api/login/**",
                        "/api/cadastro",
                        "/api/cadastro/**",
                        "/api/verificacao",
                        "/api/verificacao/**",
                        "/api/verificacao/verificar",
                        "/api/verificacao/verificar/**",
                        "/api/esqueci-senha",
                        "/api/esqueci-senha/**",
                        "/api/nova-senha",
                        "/api/nova-senha/**",
                        "/nova-senha",
                        "/nova-senha/**",
                        "/verificar",
                        "/verificar/**",
                        "/assets/**",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/admin",
                        "/admin/**",
                        "/esqueci-senha",
                        "/nova-senha"
                );
    }
}

