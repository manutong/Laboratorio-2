import java.util.*;

public class Main {
    public static void main(String[] args) {
        UrnaElectoral urna = new UrnaElectoral(); //Se crea la urna electoral.

        // Se crean 3 candidatos con sus respectivas iDs y Partidos.
        Candidato candidato1 = new Candidato(1, "Luciano", "Partido de la Gente");
        Candidato candidato2 = new Candidato(2, "Benjamin", "Partido Comunista");
        Candidato candidato3 = new Candidato(3, "Valentina", "Partido Republicano");

        // Se agregan los candidatos a la urna.
        System.out.println(urna.agregarCandidato(candidato1));
        System.out.println(urna.agregarCandidato(candidato2));
        System.out.println(urna.agregarCandidato(candidato3));

        // Se crean los votantes con sus iD
        Votante votante1 = new Votante(10, "Manu");
        Votante votante2 = new Votante(15, "Jose");
        Votante votante3 = new Votante(12, "Rocio");

        // Se realizan los votos de los votantes a los candidatos mediante el Id de estos.
        
        System.out.println(urna.registrarVoto(votante1, 1)); 
        System.out.println(urna.registrarVoto(votante2, 2)); 
        System.out.println(urna.registrarVoto(votante3, 1)); 

        // Se ntentar votar de nuevo
        System.out.println(urna.registrarVoto(votante1, 2)); 

        // Se reporta el voto
        System.out.println(urna.reportarVoto(candidato1, 0)); 

        // Se muestran los resultados en pantalla de la votacion
        System.out.println("\nResultados:");
        for (Map.Entry<Candidato, Integer> entry : urna.obtenerResultados().entrySet()) {
            System.out.println(entry.getKey().getNombre() + " (" + entry.getKey().getPartido() + "): " + entry.getValue() + " votos.");
        }
    }
}
