package br.ce.loca.services;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;

import br.ce.loca.exceptions.LocadoraException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static br.ce.loca.utils.DataUtils.*;

public class LocacaoServiceTest {
    private LocacaoService locacaoService;

    @Before
    public void setup(){
        locacaoService = new LocacaoService();

    }

    @Test
    public void verifyLocationMoviesPrice() throws Exception {
        Filme filme = new Filme("filme 1", 1, 5.00);

        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);
        assertThat(locacao.getValor(), is(equalTo(5.00)));

    }

    @Test
    public void shouldReturnLocationDateEqualNow() throws Exception {
        Filme filme = new Filme("filme 1", 2, 5.00);

        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }

    @Test
    public void shouldBeTrueThatReturnDateIsTomorrow() throws Exception {
        Filme filme = new Filme("filme 1", 2, 5.00);
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filme);
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }


    @Test
    public void shouldNotBeAbleToLocateMovieWithoutInventory(){
        Filme filme = new Filme("filme 1", 0, 5.00);

        try {
            locacaoService.alugarFilme(new Usuario("Fabio"), filme);
            Assert.fail();
        }catch (LocadoraException e) {
            assertThat(e.getMessage(), is(equalTo("Filme sem estoque")));
        }
    }

    @Test
    public void userCannotBeNull() {
        Filme filme = new Filme("filme 1", 1, 5.00);

        try {
            locacaoService.alugarFilme(null, filme);
            Assert.fail();
        }catch (LocadoraException e) {
            assertThat(e.getMessage(), is(equalTo("Usuario vazio")));
        }
    }

    @Test
    public void movieCannotBeNull(){

        try {
            locacaoService.alugarFilme(new Usuario("Fabio"), null);
            Assert.fail();
        }catch (LocadoraException e) {
            assertThat(e.getMessage(), is(equalTo("Filme vazio")));
        }
    }
}