package com.senai.inscricao.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.config.PasswordEncoder;
import com.senai.inscricao.datatables.Datatables;
import com.senai.inscricao.datatables.DatatablesColunas;
import com.senai.inscricao.domains.Assistente;
import com.senai.inscricao.domains.Inscricao;
import com.senai.inscricao.domains.Perfil;
import com.senai.inscricao.domains.Usuario;
import com.senai.inscricao.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private Datatables datatables;

	@Transactional(readOnly = true)
	public Usuario buscarPorCpf(String cpf) {
		return repository.findByCpf(cpf).get();
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = buscarPorCpf(username);
		return new User(usuario.getCpf(), usuario.getSenha(),
				AuthorityUtils.createAuthorityList(getAtuthorities(usuario.getPerfis())));
	}

	private String[] getAtuthorities(List<Perfil> perfis) {
		String[] authorities = new String[perfis.size()];
		for (int i = 0; i < perfis.size(); i++) {
			authorities[i] = perfis.get(i).getDesc();
		}
		return authorities;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> buscarTodos(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColunas.USUARIOS);
		Page<Usuario> page = datatables.getSearch().isEmpty() ? repository.findAll(datatables.getPageable())
				: repository.findByCpfOrPerfil(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = false)
	public void salvarUsuario(Usuario usuario) {
		String crypt = passwordEncoder().encode(usuario.getSenha());
		usuario.setSenha(crypt);

		repository.save(usuario);
	}
	
	@Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Transactional(readOnly = false)
	public void salvarEdicaoUsuario(Usuario usuario) {
		repository.save(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {

		return repository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorIdEPerfis(Long usuarioId, Long[] perfisId) {

		return repository.findByIdAndPerfis(usuarioId, perfisId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario inexistente!"));
	}

	public static boolean isSenhaCorreta(String senhaDigitada, String senhaArmazenada) {

		return passwordEncoder().matches(senhaDigitada, senhaArmazenada);
	}

	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario, String senha) {
		usuario.setSenha(passwordEncoder().encode(senha));
		repository.save(usuario);
	}


	public List<Usuario> obterLista() { 
		return (List<Usuario>)repository.findAll(); 
	}

}