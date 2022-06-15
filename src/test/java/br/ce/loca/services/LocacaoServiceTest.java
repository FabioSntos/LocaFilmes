package br.ce.loca.services;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static br.ce.loca.utils.DataUtils.*;

public class LocacaoServiceTest {

    @Test
    public void verifyLocationMoviesPrice() throws Exception {
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1", 1, 5.00);

            Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);
            assertThat(locacao.getValor(), is(equalTo(5.00)));

    }

    @Test
    public void shouldNotBeAbleToLocateMovieWithoutInventory(){
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1", 0, 5.00);

        try {
            locacaoService.alugarFilme(new Usuario("Fabio"), filme);
            Assert.fail("Should not be able to locate movie without inventory");
        }catch (Exception e) {
            assertThat(e.getMessage(), is(equalTo("Filme sem estoque")));
        }
    }

    @Test
    public void shouldReturnLocationDateEqualNow() throws Exception {
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1", 2, 5.00);

        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }

    @Test
    public void shouldBeTrueThatReturnDateIsTomorrow() throws Exception {
        LocacaoService locacaoService = new LocacaoService();
        Filme filme = new Filme("filme 1", 2, 5.00);
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }
}