package br.ce.loca.services;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;

import br.ce.loca.exceptions.LocadoraException;

import br.ce.loca.matchers.DiaSemanaMatcher;
import br.ce.loca.matchers.MatchersProprios;
import br.ce.loca.utils.DataUtils;
import org.junit.*;


import java.util.*;

import static br.ce.loca.matchers.MatchersProprios.diaSemana;
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
    public void shouldVerifyLocationMoviesPrice() throws LocadoraException {
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 10.0),
                new Filme("Filme 2", 2, 10.0),
                new Filme("Filme 3", 2, 10.0)
        );
        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filmes);
        assertThat(locacao.getValor(), is(equalTo(27.50)));

    }

    @Test()
    public void shouldReturnLocationDateEqualNow() throws LocadoraException {
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 10.0),
                new Filme("Filme 2", 2, 10.0),
                new Filme("Filme 3", 2, 10.0)
        );

        Locacao locacao = locacaoService.alugarFilme(new Usuario("Fabio"), filmes);
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

    }

    @Test
    public void shouldBeTrueThatReturnDateIsTomorrow() throws LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.MONDAY));
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
    public void shouldThrowExceptionWhenUserIsNull() {
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
    public void shouldThrowExceptionWhenmovieIsNull(){

        try {
            locacaoService.alugarFilme(new Usuario("Fabio"), null);
            Assert.fail();
        }catch (LocadoraException e) {
            assertThat(e.getMessage(), is(equalTo("Filme vazio")));
        }
    }

    @Test
    public void shouldNotGiveBackMovieOnSunday() throws LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
        Usuario usuario = new Usuario("Fabio");
        List<Filme> filmes = Arrays.asList(
                new Filme("Filme 1", 2, 4.0));

        Locacao retorno = locacaoService.alugarFilme(usuario, filmes);
        assertThat(retorno.getDataRetorno(), diaSemana(Calendar.MONDAY));
    }
}