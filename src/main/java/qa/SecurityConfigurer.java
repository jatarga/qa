package qa;

import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.LazyCsrfTokenRepository;
import qa.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import qa.service.TokenService;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    public TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(getTokenAuthFilter(), BasicAuthenticationFilter.class);
        http.csrf().disable();
        //http.cors().disable();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    private Filter getTokenAuthFilter() {
        return new TokenAuthenticationFilter(tokenService);
    }

}