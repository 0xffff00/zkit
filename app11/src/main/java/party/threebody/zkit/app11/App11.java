package party.threebody.zkit.app11;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@SpringBootApplication
/**
 * https://projects.spring.io/spring-security-oauth/docs/oauth2.html
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security
 * http://www.baeldung.com/spring-security-oauth-jwt
 *
 * http://blog.didispace.com/spring-security-oauth2-xjf-1/
 * https://github.com/brahalla/oauth2/blob/master/oauth-provider/src/main/java/com/brahalla/oauthprovider/configuration/JwtTokenConfiguration.java
 * https://github.com/tuanngda/spring-boot-oauth2-demo/blob/master/src/main/java/com/blogspot/sgdev/blog/security/AuthorizationServerConfiguration.java
 *
 * https://github.com/sharmaritesh/oauth2-spring-boot-mongo/blob/master/auth-server/src/main/java/com/rites/sample/oauth2/authserver/conf/AuthServerConfig.java
 */
@EnableAuthorizationServer
public class App11 {
    public static void main(String[] args) {
        SpringApplication.run(App11.class, args);
    }
}

@Configuration
class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()   //required
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("c1")
                .secret("123")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("write", "read", "write", "m11", "m12")
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(3600 * 24 * 30)

        //.authorities("ADMIN1")
        ;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        //converter.setVerifierKey("123");
        //converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());
        return converter;
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setAccessTokenValiditySeconds(7200);
        return defaultTokenServices;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // InMemoryTokenStore ts = new InMemoryTokenStore();
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
        endpoints
                .tokenStore(tokenStore())
                .tokenServices(tokenServices())
                .authenticationManager(authenticationManager);  //required

    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JwtAccessTokenConverter();
        //return new BaeldungCustomTokenEnhancer();
    }


}

@EnableWebSecurity
@Configuration
class WebSecConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("hzk").password("123").authorities("ADMIN")
                .and().withUser("u1").password("111").authorities("ADMIN");
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth/*").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }
}
