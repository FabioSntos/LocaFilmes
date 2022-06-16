package br.ce.loca.services;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;

import br.ce.loca.exceptions.LocadoraException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.*;

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
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 10.0),
                new Filme("Filme 2", 2, 10.0),
                new Filme("Filme 3", 2, 10.0)
        );
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filmes);
        assertThat(locacao.getValor(), is(equalTo(30.00)));

    }

    @Test
    public void shouldReturnLocationDateEqualNow() throws Exception {
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 10.0),
                new Filme("Filme 2", 2, 10.0),
                new Filme("Filme 3", 2, 10.0)
        );

        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filmes);
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }

    @Test
    public void shouldBeTrueThatReturnDateIsTomorrow() throws Exception {
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 10.0),
                new Filme("Filme 2", 2, 10.0),
                new Filme("Filme 3", 2, 10.0)
        );
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filmes);
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }


    @Test
    public void shouldNotBeAbleToLocateMovieWithoutInventory(){
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 0, 10.0),
                new Filme("Filme 2", 0, 10.0)
        );
        try {
            locacaoService.alugarFilme(new Usuario("Fabio"), filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is(equalTo("Filme sem estoque")));
        }
    }

    @Test
    public void userCannotBeNull() {
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 10.0),
                new Filme("Filme 2", 2, 10.0),
                new Filme("Filme 3", 2, 10.0)
        );

        try {
            locacaoService.alugarFilme(null, filmes);
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