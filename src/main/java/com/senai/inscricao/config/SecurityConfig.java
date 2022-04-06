package com.senai.inscricao.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.senai.inscricao.domains.PerfilTipo;
import com.senai.inscricao.services.UsuarioService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String ADMIN = PerfilTipo.ADMIN.getDesc();
    private static final String ASSISTENTE = PerfilTipo.ASSISTENTE.getDesc();
    private static final String CANDIDATO = PerfilTipo.CANDIDATO.getDesc();
	
	@Autowired
	private UsuarioService service;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			// acessos públicos liberados
			.antMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
			.antMatchers("/", "/cadastro", "/home", "/u/novo/cadastro/usuario", "/u/cadastro/salvar" ).permitAll()
			
			// acessos privados admin
			.antMatchers("/u/editar/senha", "/u/confirmar/senha").permitAll()
			.antMatchers("/u/**").hasAnyAuthority(ADMIN, ASSISTENTE)
			.antMatchers("/u/lista").hasAnyAuthority(ADMIN, ASSISTENTE)

			
			
			// acessos privados ASSISTENTEs
			.antMatchers("/ASSISTENTEs/especialidade/titulo/*").hasAnyAuthority(CANDIDATO, ASSISTENTE)
			.antMatchers("/ASSISTENTEs/dados", "/ASSISTENTEs/salvar", "/ASSISTENTEs/editar").hasAnyAuthority(ASSISTENTE, ADMIN)
			.antMatchers("/ASSISTENTEs/**").hasAnyAuthority(ADMIN, ASSISTENTE)
			
			// acessos privados CANDIDATOs
			.antMatchers("/CANDIDATOs/**").hasAuthority(CANDIDATO)			
			
			// acessos privados especialidades
			.antMatchers("/cursos/datatables/server/ASSISTENTE/*").hasAnyAuthority(ASSISTENTE, ADMIN)
			.antMatchers("/cursos/editar/*").hasAuthority(ADMIN)
			.antMatchers("/cursos/titulo").hasAnyAuthority(ASSISTENTE, ADMIN, CANDIDATO)
			.antMatchers("/cursos/**").hasAnyAuthority(ASSISTENTE, ADMIN)
			
			.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login-error")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/acesso-negado");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(service);
	}
	
	

}
