package br.com.pavaneli.projeto.entity;

import org.springframework.beans.BeanUtils;

import br.com.pavaneli.projeto.dto.PermissaoPerfilRecursoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class PermissaoPerfilRecurso {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ID_PERFIL")
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "ID_RECURSO")
	private Recurso recurso;
	
	public PermissaoPerfilRecurso(PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
		BeanUtils.copyProperties(permissaoPerfilRecurso, this);
		if(permissaoPerfilRecurso != null && permissaoPerfilRecurso.getRecurso() != null) {
			this.recurso = new Recurso(permissaoPerfilRecurso.getRecurso());
		}
		if(permissaoPerfilRecurso != null && permissaoPerfilRecurso.getPerfil() != null) {
			this.perfil = new Perfil(permissaoPerfilRecurso.getPerfil());
		}	
	}
}
