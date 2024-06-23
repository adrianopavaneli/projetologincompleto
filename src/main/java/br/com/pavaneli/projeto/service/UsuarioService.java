package br.com.pavaneli.projeto.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pavaneli.projeto.dto.UsuarioDTO;
import br.com.pavaneli.projeto.entity.Usuario;
import br.com.pavaneli.projeto.entity.UsuarioVerificador;
import br.com.pavaneli.projeto.entity.enums.TipoSituacaoUsuario;
import br.com.pavaneli.projeto.repository.UsuarioRepository;
import br.com.pavaneli.projeto.repository.UsuarioVerificadorRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private UsuarioVerificadorRepository usuarioVerificadorRepository;
	
	public List<UsuarioDTO> listarTodos(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(UsuarioDTO::new).toList();
	}
	
	public void inserir(UsuarioDTO usuario) {
		Usuario usuarioEntity = new Usuario(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuarioEntity);
	}
	
	public void inserirNovoUsuario(UsuarioDTO usuario) {
		Usuario usuarioEntity = new Usuario(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioEntity.setSituacao(TipoSituacaoUsuario.PENDENTE);
		usuarioEntity.setId(null);
		usuarioRepository.save(usuarioEntity);
		
		UsuarioVerificador verificador = new UsuarioVerificador();
		verificador.setUsuario(usuarioEntity);
		verificador.setUuid(UUID.randomUUID());
		verificador.setDataExpiracao(Instant.now().plusMillis(900000));
		usuarioVerificadorRepository.save(verificador);
		
		//TODO - Enviar um email para verificar a conta
		emailService.enviarEmailTexto(usuario.getEmail(), 
				"Novo usuário cadastrado", 
				"Você está recebendo um email de cadastro o número para validação é " + verificador.getUuid());
		
		
	}
	public String verificarCadastro(String uuid) {
		
		UsuarioVerificador usuarioVerificacao = usuarioVerificadorRepository.findByUuid(UUID.fromString(uuid)).get();
		
		if(usuarioVerificacao != null) {
			if(usuarioVerificacao.getDataExpiracao().compareTo(Instant.now()) >= 0) {
				
				Usuario u = usuarioVerificacao.getUsuario();
				u.setSituacao(TipoSituacaoUsuario.ATIVO);
				
				usuarioRepository.save(u);
				
				return "Usuário Verificado";
			}else {
				usuarioVerificadorRepository.delete(usuarioVerificacao);
				return "Tempo de verificação expirado";
			}
		}else {
			return "Usuario não verificado";
		}
		
	}
	
	public UsuarioDTO alterar(UsuarioDTO usuario) {
		Usuario usuarioEntity = new Usuario(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
	}
	
	public void excluir(Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
	}
	
	public UsuarioDTO buscarPorId(Long id) {
		return new UsuarioDTO(usuarioRepository.findById(id).get());
	}
}
