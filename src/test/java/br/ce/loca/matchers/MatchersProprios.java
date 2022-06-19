package br.ce.loca.matchers;

public class MatchersProprios {
    public static DiaSemanaMatcher diaSemana(int diaSemana) {
        return new DiaSemanaMatcher(diaSemana);
    }
}
