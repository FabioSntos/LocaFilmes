package br.ce.loca.services;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static br.ce.loca.utils.DataUtils.*;

public class LocacaoServiceTest {

    @Test
    public void verifyLocationMoviesPrice() {
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1", 2, 5.00);
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);

        assertThat(locacao.getValor(), is(equalTo(5.00)));
    }

    @Test
    public void shouldReturnLocationDateEqualNow() {
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1", 2, 5.00);
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);

        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
    }

    @Test
    public void shouldBeTrueThatReturnDateIsTomorrow() {
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1", 2, 5.00);
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);

        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)),is(true));
    }
}