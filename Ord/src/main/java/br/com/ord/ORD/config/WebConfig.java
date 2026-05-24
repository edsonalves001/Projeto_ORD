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

        registry.addInterceptor(interceptor)
                .excludePathPatterns(
                        "/",
                        "/cadastro",
                        "/index",
                        "/login",
                        "/login/**",
                        "/cadastro",
                        "/cadastro/**",
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

