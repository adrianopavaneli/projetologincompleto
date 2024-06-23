package br.com.pavaneli.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pavaneli.projeto.dto.PerfilUsuarioDTO;
import br.com.pavaneli.projeto.entity.PerfilUsuario;
import br.com.pavaneli.projeto.repository.PerfilUsuarioRepository;

@Service
public class PerfilUsuarioService {

	@Autowired
	private PerfilUsuarioRepository perfilUsuarioRepository;
	
	public List<PerfilUsuarioDTO> listarTodos(){
		List<PerfilUsuario> perfilUsuarios = perfilUsuarioRepository.findAll();
		return perfilUsuarios.stream().map(PerfilUsuarioDTO::new).toList();
	}
	
	public void inserir(PerfilUsuarioDTO perfilUsuario) {
		PerfilUsuario perfilUsuarioEntity = new PerfilUsuario(perfilUsuario);
		perfilUsuarioRepository.save(perfilUsuarioEntity);
	}
	
	public PerfilUsuarioDTO alterar(PerfilUsuarioDTO perfilUsuario) {
		PerfilUsuario perfilUsuarioEntity = new PerfilUsuario(perfilUsuario);
		return new PerfilUsuarioDTO(perfilUsuarioRepository.save(perfilUsuarioEntity));
	}
	
	public void excluir(Long id) {
		PerfilUsuario recurso = perfilUsuarioRepository.findById(id).get();
		perfilUsuarioRepository.delete(recurso);
	}
	
	public PerfilUsuarioDTO buscarPorId(Long id) {
		return new PerfilUsuarioDTO(perfilUsuarioRepository.findById(id).get());
	}
}
