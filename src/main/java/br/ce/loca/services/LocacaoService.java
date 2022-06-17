package br.ce.loca.services;

import static br.ce.loca.utils.DataUtils.adicionarDias;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;
import br.ce.loca.exceptions.LocadoraException;
import br.ce.loca.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filme) throws LocadoraException {

		if(filme == null || filme.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}

		for(Filme filmes: filme) {
			if(filmes.getEstoque() == 0) {
				throw new LocadoraException("Filme sem estoque");
			}
		}


		if(usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}


		Locacao locacao = new Locacao();

		Double valorTotal = 0.0;

		for(int i = 0; i < filme.size(); i++){
			Filme filmes = filme.get(i);
			Double valorFilmes = filmes.getPrecoLocacao();

			switch (i){
				case 2:
					valorFilmes = valorFilmes * 0.75; break;
				case 3:
					valorFilmes = valorFilmes * 0.50; break;

				case 4:
					valorFilmes = valorFilmes * 0.25; break;
				case 5:
					valorFilmes = 0.0; break;
			}
			valorTotal += valorFilmes;
		}
		locacao.setValor(valorTotal);
		locacao.setFilmes(filme);

		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	public static void main(String[] args) {
		LocacaoService locacaoService = new LocacaoService();
		try {
			Filme filme = new Filme();
			filme.setEstoque(1);
			filme.setNome("Filme 1");
			filme.setPrecoLocacao(10.0);
			Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), Collections.singletonList(filme));
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}