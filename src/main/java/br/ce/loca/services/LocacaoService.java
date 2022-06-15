package br.ce.loca.services;

import static br.ce.loca.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;
import br.ce.loca.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {

		if(filme.getEstoque() == 0) {
			throw new Exception("Filme sem estoque");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

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
			Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), new Filme("nome", 1, 10.00 ));
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}