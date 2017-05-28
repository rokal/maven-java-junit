package io.rsk.tutorial;

import static org.junit.Assert.*;

import org.junit.Test;

public class CalculateurTest {
	
	private Calculateur calculateur;

    @Test
    public void etantDonneUnSeulNombreAAdditionner_quandOnCalculeLaSomme_alorsRetournerLeNombre(){
        calculateur = new Calculateur();
        Integer nombre1  = 2;

        Integer resultat = calculateur.calculerSomme(nombre1);

        assertTrue(nombre1.equals(resultat));
    }

}