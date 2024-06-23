package br.com.pavaneli.projeto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pavaneli.projeto.dto.RecursoDTO;
import br.com.pavaneli.projeto.entity.Recurso;
import br.com.pavaneli.projeto.repository.RecursoRepository;

@Service
public class RecursoService {

	@Autowired
	private RecursoRepository recursoRepository;
	
	public List<RecursoDTO> listarTodos(){
		List<Recurso> recursos = recursoRepository.findAll();
		return recursos.stream().map(RecursoDTO::new).toList();
	}
	
	public void inserir(RecursoDTO recurso) {
		Recurso recursoEntity = new Recurso(recurso);
		recursoRepository.save(recursoEntity);
	}
	
	public RecursoDTO alterar(RecursoDTO recurso) {
		Recurso recursoEntity = new Recurso(recurso);
		return new RecursoDTO(recursoRepository.save(recursoEntity));
	}
	
	public void excluir(Long id) {
		Recurso recurso = recursoRepository.findById(id).get();
		recursoRepository.delete(recurso);
	}
	
	public RecursoDTO buscarPorId(Long id) {
		return new RecursoDTO(recursoRepository.findById(id).get());
	}
}
