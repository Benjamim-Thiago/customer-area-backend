package ai.webjump.customer_area_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration TODO
public class SecurityConfigBck {

    // Configura o filtro de segurança principal
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Define as permissões de acesso baseadas nas roles (appRoles) do token JWT
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_admin")  // Requer a role 'admin'
                        .requestMatchers("/api/user/**").hasAuthority("ROLE_user")    // Requer a role 'user'
                        .anyRequest().authenticated()  // Qualquer outra requisição precisa estar autenticada
                )
                // Configura o servidor de recursos OAuth2 para usar JWT
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())  // Converte as claims JWT em authorities
                        )
                );

        return http.build();
    }

    // Configura o conversor de JWT para extrair as roles (authorities) das claims do token JWT
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");  // Prefixa as roles com 'ROLE_' exigido pelo Spring Security

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
