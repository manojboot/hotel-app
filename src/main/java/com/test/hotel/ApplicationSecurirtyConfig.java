package com.test.hotel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.test.hotel.auth.HotelManuUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurirtyConfig extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private HotelManuUserDetailsService userDetailsService;
		
		@Bean
		public DaoAuthenticationProvider authenticatioProvider() {
			
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(userDetailsService);
			provider.setPasswordEncoder(new BCryptPasswordEncoder(11)); 
			provider.setAuthoritiesMapper(authoritiesMapper());
			return provider;
			
		}
		
		@Bean
		public GrantedAuthoritiesMapper authoritiesMapper() {
			SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
			authorityMapper.setConvertToUpperCase(true);
			authorityMapper.setDefaultAuthority("USER");
			return authorityMapper;
		}
		
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
			auth.authenticationProvider(authenticatioProvider());
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
				http
					.csrf().disable()
					.authorizeRequests()
					.antMatchers("/","/index","/css/*","/js/*").permitAll()
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login").permitAll()
					.and()
					.logout().invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/logout-success").permitAll();
							
		}
		
	/*	@Bean
		@Override
		public UserDetailsService userDetailsService() {
			
				List<UserDetails> users = new ArrayList<>();
				users.add(User.withDefaultPasswordEncoder().username("mikefoley").password("password").roles("USER").build());
				users.add(User.withDefaultPasswordEncoder().username("admin").password("password").roles("USER","ADMIN").build());
				return new InMemoryUserDetailsManager(users);
			
		}	*/
		
		
}

