package jp.ac.ohara.oharabank.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jp.ac.ohara.oharabank.service.LoginFormService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private LoginFormService userService;
	
//	@Autowired
//	private BankAccountRepository repository;
	
	@Bean
	public UserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager jdbcManager = new JdbcUserDetailsManager(this.dataSource);
//		this.repository.saveAndFlush(this.makeUser(3,"五島新之助","gotosin5",0));
		return jdbcManager;
	}
//	private BankAccount makeUser(long accountId, String userName, String password, Integer balance) {
//        BankAccount record = new BankAccount();
//        record.setAccountId(accountId);
//        record.setUserName(userName);
//        record.setPassword(this.passwordEncoder().encode(password));
//        record.setBalance(balance);
//        return record;
//    }
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
		throws Exception {
	  http
	  	  .httpBasic(
			 (basic) -> basic.disable())
		  .authorizeHttpRequests(request -> {
		  request
		  .requestMatchers("/login").permitAll()
		  .requestMatchers("/").permitAll()
		  .requestMatchers("/form").permitAll()
		  .requestMatchers("/webjars/**").permitAll()
		  .requestMatchers("/js/**").permitAll()
		  .requestMatchers("/css/**").permitAll()
		  .requestMatchers("/img/**").permitAll()
		  .anyRequest().authenticated();
	  }).csrf(csrf -> {
		  csrf.disable();
	  }).formLogin(form -> {
		  form
		      .loginPage("/")
		      .loginProcessingUrl("/login")
		      .defaultSuccessUrl("/")
		      .failureUrl("/login/?error=true")
		      .usernameParameter("userName")
		      .passwordParameter("password");
	  })
	  .userDetailsService(this.userService)
	  .logout(logout -> {
		  logout
		  		.logoutUrl("/logout")
		  		.logoutSuccessUrl("/login")
		  		.deleteCookies("JSESSIONID")
		  		.invalidateHttpSession(true);
	  });
		  return http.build();
	}
	
}	

//@Configuration //設定用のクラスであることをSpringに伝える
//@EnableWebSecurity //Spring Securityを使うための設定
//public class SecurityConfig extends WebSecurityConfiguration {
//	
//	@Autowired
//	private LoginFormService memberDetailsService;
//
//	//   (1)主に全体に対するセキュリティ設定を行う
//	//  このメソッドをオーバーライドすることで、
//	//  特定のリクエストに対して「セキュリティ設定」を無視する
//	//  設定など全体に関わる設定ができる。
//	//  具体的には静的ファイルに対してセキュリティの設定を無効にする（静的ファイルのリクエストまで弾かない。）。
//	@Override
//	public void configure(WebSecurity web)throws Exception{
//          web.ignoring().antMatchers("/css/**","/js/**","/images/**");
//	}
//	
//    //  (2)主にURLごとに異なるセキュリティ設定を行う
//	//  このメソッドをオーバーライドすることで認可に設定や
//	//  ログイン、ログアウトに関する設定ができる。
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//       //(2-1)認可に関する設定
//    	http
//    	.authorizeHttpRequests() 
//    	.antMatchers("/","/register","/register/decision").permitAll() //ここに記載したパスは全てのユーザーに許可
//    	 // /admin/から始まるパスはADMIN権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
//    	//.antMatchers("/admin/**").hasRole("ADMIN")
//    	// /user/から始まるパスはUSER権限でログインしている場合のみアクセス可(権限設定時の「ROLE_」を除いた文字列を指定)
//    	//.antMatchers("/user/**").hasRole("USER") 
//    	//それ以外のパスは認証が必要
//    	.anyRequest().authenticated(); 
//    	
//      //(2-2)ログインに関する設定
//    	
//    	.formLogin(form ->{
//      //ログイン画面に遷移させるパス(ログイン認証が必要なパスを指定してかつログインされていないとこのパスに遷移される。)
//    	form
//	    	.loginPage("/") 
//	    	.loginProcessingUrl("/login") //ログインボタンを押したときに遷移させるパス（ここに遷移させれば自動的にログインが行われる。）
//	    	.failureUrl("/?error=true") //ログイン失敗時に遷移させるパス
//	    	.defaultSuccessUrl("/afterLogin/top",true) // 第1引数:デフォルトでログイン成功時に遷移させるパス
//									                  // 第2引数: true :認証後常に第1引数のパスに遷移 
//		// false:認証されてなくて一度ログイン画面に飛ばされてもログインしたら指定したURLに遷移
//	    	.usernameParameter("email")     //認証時に使用するユーザー名のリクエストパラメータ名（今回はメールアドレスを使用）
//	    	.passwordParameter("password"); //認証時に使用するパスワードのリクエストパラメーター名
//	    	})
//    	.logout(logout ->{ //(2-3)ログアウトに関する設定
//    	logout
//	    	.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //ログアウトさせる際に遷移させるパス
//	    	.logoutSuccessUrl("/") //ログアウト後に遷移させるパス
//	    	.deleteCookies("JSESSIONID") //ログアウト後、Cookieに保存されているセッションIDを削除
//	    	.invalidateHttpSession(true); //ログアウト後、セッションを無効にする false:セッションを無効にしない
//	    		});
//    	}
//    
//    //  (3) 主に認証方法の実装の設定を行う
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	auth.userDetailsService(memberDetailsService)
//    	.passwordEncoder(new BCryptPasswordEncoder())
//    	;
//    }
//    
//    /**
//     * 
//     * (4)bcryptアルゴリズムでハッシュ化する実装を返します.
//     * これを指定することでパスワードハッシュ化やマッチ確認する際に
//     * @Autowired
//	 * private PasswordEncoder passwordEncoder;
//	 * と記載するとDIされるようになります。
//     * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//    		return new BCryptPasswordEncoder();
//    }
//}


