package br.com.pavaneli.projeto.dto;

import org.springframework.beans.BeanUtils;

import br.com.pavaneli.projeto.entity.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PerfilDTO {

	private Long id;
	private String descricao;
	
	public PerfilDTO(Perfil perfil) {
		BeanUtils.copyProperties(perfil, this);
	}
}
