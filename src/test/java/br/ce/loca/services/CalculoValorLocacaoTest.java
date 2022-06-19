package br.ce.loca.services;

import br.ce.loca.entities.Filme;
import br.ce.loca.entities.Locacao;
import br.ce.loca.entities.Usuario;
import br.ce.loca.exceptions.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    private LocacaoService locacaoService;

    @Parameterized.Parameter
    public List<Filme> filmes;

    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;

    private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
    private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
    private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
    private static Filme filme4 = new Filme("Filme 3", 2, 4.0);
    private static Filme filme5 = new Filme("Filme 3", 2, 4.0);
    private static Filme filme6 = new Filme("Filme 3", 2, 4.0);
    private static Filme filme7 = new Filme("Filme 3", 2, 4.0);


    @Before
    public void setup(){
        locacaoService = new LocacaoService();

    }

    @Parameterized.Parameters(name="{2}")
    public static Collection<Object[]> getParametros(){
        return Arrays.asList(new Object[][]{
                {Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem desconto"},
                {Arrays.asList(filme1, filme2,filme3), 11.0, "3 Filmes: 25%"},
                {Arrays.asList(filme1, filme2,filme3, filme4), 13.0, "4 Filmes: 50%"},
                {Arrays.asList(filme1, filme2,filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
                {Arrays.asList(filme1, filme2,filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
                {Arrays.asList(filme1, filme2,filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: sem desconto"},
        });
    }


    @Test
    public void shouldCalcLocationValueConsideringDiscounts() throws LocadoraException {
        //cenario
        Usuario usuario = new Usuario("Fabio");

        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        //verificacao

        assertThat(locacao.getValor(), is(equalTo(valorLocacao)));
    }
}
