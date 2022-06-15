package br.ce.loca.services;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;

import org.junit.Assert;
import org.junit.Test;

public class LocacaoServiceTest {

    @Test
    public void deveAlugarFilme() {
        LocacaoService locacaoService = new LocacaoService();
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), new Filme("nome", 1, 10.00));

        Assert.assertEquals("nome", locacao.getFilme().getNome());
    }
}