package com.senai.inscricao.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senai.inscricao.datatables.Datatables;
import com.senai.inscricao.datatables.DatatablesColunas;
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
		byte[] decoded = Base64.decodeBase64(usuario.getSenha().getBytes());
//		String senhaUsuario = Arrays.toString(decoded);
		
		String decodedString = new String(decoded);
		
//		System.out.println("            ==================================================");
//		System.out.println("            ==================================================");
//		System.out.println("loadUserByUsername: ");
//		System.out.print("senha criptografada: ");
//		System.out.println(usuario.getSenha());
//		System.out.print("Senha descriptografgada: ");
//		System.out.println(decodedString);
//		
//		
//		System.out.println("            ==================================================");
//		System.out.println("            ==================================================");
		
		return new User(usuario.getCpf(), decodedString,
				AuthorityUtils.createAuthorityList(getAtuthorities(usuario.getPerfis())));
	}

	@Transactional(readOnly = false)
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
		String crypt = Base64.encodeBase64String(usuario.getSenha().getBytes());
		usuario.setSenha(crypt);

		repository.save(usuario);
	}
	
	@Transactional(readOnly = false)
	public void salvarEdicaoUsuario(Usuario usuario) {
		repository.save(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {

		return repository.findById(id).get();
	}

	@Transactional(readOnly = false)
	public Usuario buscarPorIdEPerfis(Long usuarioId, Long[] perfisId) {

		return repository.findByIdAndPerfis(usuarioId, perfisId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario inexistente!"));
	}

	@Transactional(readOnly = true)
	public static boolean isSenhaCorreta(String senhaDigitada, Usuario usuario) {

		
		byte[] decoded = Base64.decodeBase64(usuario.getSenha().getBytes());
		String decodedString = new String(decoded);
		
		System.out.println("isSenhaCorreta - senhaArmazenada: " + usuario.getSenha() );
		System.out.println("isSenhaCorreta - Senha decoded: " + decodedString );
		System.out.println("isSenhaCorreta - senhaDigitada: " + senhaDigitada );
		
		if(decodedString.equals(senhaDigitada)) {
			System.out.println("Senha: true");
			return true;
		} else {
			System.out.println("Senha: false");
			return false;
		}
	}


	@Transactional(readOnly = false)
	public void alterarSenha(Usuario usuario, String senha) {
		
		String crypt = Base64.encodeBase64String(senha.getBytes());
		usuario.setSenha(crypt);
		
		repository.save(usuario);
	}

	public List<Usuario> obterLista() { 
		return (List<Usuario>)repository.findAll(); 
	}

	
	public List<Usuario> obterListaNaoInscrito() {
		return (List<Usuario>)repository.findListaNaoInscritos("nao-inscrito");
	}

	
	public boolean validaCpf(String string) {
		
		String cpfUser = string;
		Integer qtd_caracteres = cpfUser.length();
		boolean cpfVerificado = cpfUser.matches("^\\d+$");  // <-- repare que seq está sem as aspas
		
		if (!cpfVerificado) {
			return false;
		}
		 
		if(qtd_caracteres != 11) {
			return false;
		} else {
			return true;
		}
		 
		
	}

}