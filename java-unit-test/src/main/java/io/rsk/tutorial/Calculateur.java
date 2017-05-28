package io.rsk.tutorial;

import java.util.stream.Stream;

class Calculateur{
    public Integer calculerSomme(Integer ...mesNombresAAdditionner){
        return Stream.of(mesNombresAAdditionner)
                     .reduce(0, (nombre1,nombre2) -> nombre1 + nombre2);
    }
}