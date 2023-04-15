package quizweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import quizweb.app.authentic.MyPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService;
 
    
    /**
     * リクエストパラメータから認証情報を取得するためのフィルターを返却する
     * @return 認証に使用するフィルター
     * @throws Exception
     */
    public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter() throws Exception {
        MyPreAuthenticatedProcessingFilter filter = new MyPreAuthenticatedProcessingFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
    
    /**
     * 認証に使用するプロバイダーを作成する
     * @return 認証プロバイダー
     */
    public PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        // ユーザ認証を行うサービス設定
        provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService);
        // 認証されたユーザをチェックする処理を設定（ここではユーザ情報がnullかどうかをチェック）
        provider.setUserDetailsChecker(new AccountStatusUserDetailsChecker());
        return provider;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 認証プロバイダーを設定する
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Security Filter Chain から除外するパス等を設定
        web.ignoring().antMatchers("/img/**/","/","/doAuth","/**/manifest.json");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
            .authorizeRequests()
                // マッチするURLの場合認証処理を行う
                .antMatchers("/login","/test/test").authenticated()
                // それ以外は認証を行わない
                .anyRequest().permitAll()
            .and()
                .logout().logoutUrl("/logout")
            .and()
                // 認証時のフィルターを追加する（本クラスで定義）
                .addFilter(preAuthenticatedProcessingFilter());
                // .exceptionHandling()
                // .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"));
    }
}
