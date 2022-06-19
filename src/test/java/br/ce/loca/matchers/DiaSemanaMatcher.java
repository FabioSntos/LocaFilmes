package br.ce.loca.matchers;

import br.ce.loca.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Date;

public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {
    private int diaSemana;

    public DiaSemanaMatcher(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.verificarDiaSemana(date, diaSemana);
    }

    @Override
    public void describeTo(Description description) {

    }
}
