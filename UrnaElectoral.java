import java.util.*; //Clases del sistema electoral
import java.io.*;
 class Voto {
    private int Id;
    private int VotanteId;
    private int CandidatoId;
    private String TimeStamp;

    public Voto(int Id, int VotanteId, int CandidatoId, String TimeStamp) {
        this.Id = Id;
        this.VotanteId = VotanteId;
        this.CandidatoId = CandidatoId;
        this.TimeStamp = TimeStamp;
    }

    public int getId() {
        return Id;
    }

    public int getVotanteId() {
        return VotanteId;
    }

    public int getCandidatoId() {
        return CandidatoId;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setVotanteId(int VotanteId) {
        this.VotanteId = VotanteId;
    }

    public void setCandidatoId(int CandidatoId) {
        this.CandidatoId = CandidatoId;
    }

    public void setTimeStamp(String TimeStamp) {
        this.TimeStamp = TimeStamp;
    }
}
class Votante {
    private int Id;
    private String Nombre;
    private boolean yaVoto;

    public Votante(int Id, String Nombre) {
        this.Id = Id;
        this.Nombre = Nombre;
        this.yaVoto = false;
    }

    public int getId() {
        return Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public boolean isYaVoto() {
        return yaVoto;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setYaVoto(boolean yaVoto) {
        this.yaVoto = yaVoto;
    }

    public void marcarComoVotado() {
        this.yaVoto = true;
    }
}
class Candidato {
    private int id;
    private String nombre;
    private String partido;
    private Queue<Voto> votosRecibidos;

    public Candidato(int id, String nombre, String partido) {
        this.id = id;
        this.nombre = nombre;
        this.partido = partido;
        this.votosRecibidos = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPartido() {
        return partido;
    }

    public int getCantidadVotos() {
        return votosRecibidos.size();
    }

    public Queue<Voto> getVotosRecibidos() {
        return votosRecibidos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public void agregarVoto(Voto v) {
        this.votosRecibidos.offer(v);
    }

    public Voto removerVotoPorID(int idVoto) {
        for (Voto v : votosRecibidos) {
            if (v.getId() == idVoto) {
                votosRecibidos.remove(v);
                return v;
            }
        }
        return null;
    }
}
public class UrnaElectoral {
    private LinkedList<Candidato> listaCandidatos;
    private Deque<Voto> historialVotos;
    private Queue<Voto> votosReportados;
    private int idCounter;

    public UrnaElectoral() {
        this.listaCandidatos = new LinkedList<>();
        this.historialVotos = new ArrayDeque<>();
        this.votosReportados = new LinkedList<>();
        this.idCounter = 0;
    }

    public String agregarCandidato(Candidato candidato) {
        for (Candidato c : listaCandidatos) {
            if (c.getId() == candidato.getId()) {
                return "El candidato ya esta registrado.";
            }
        }
        listaCandidatos.add(candidato);
        return "Candidato agregado con exito.";
    }

    public boolean verificarVotante(Votante votante) {
        return votante.isYaVoto();
    }

    public String registrarVoto(Votante votante, int candidatoID) {
        if (verificarVotante(votante)) {
            return "El votante ya ha votado.";
        }

        Candidato candidato = buscarCandidatoPorID(candidatoID);
        if (candidato == null) {
            return "Candidato no encontrado.";
        }

        String timestamp = java.time.LocalTime.now().toString();
        Voto nuevoVoto = new Voto(idCounter++, votante.getId(), candidatoID, timestamp);
        candidato.agregarVoto(nuevoVoto);
        historialVotos.push(nuevoVoto);
        votante.marcarComoVotado();

        return "Voto registrado con exito.";
    }

    public String reportarVoto(Candidato candidato, int idVoto) {
        Voto votoReportado = candidato.removerVotoPorID(idVoto);
        if (votoReportado == null) {
            return "Voto no encontrado o ya fue reportado.";
        }

        for (Voto v : votosReportados) {
            if (v.getId() == idVoto) {
                return "El voto fue reportado anteriormente.";
            }
        }

        votosReportados.offer(votoReportado);
        return "Voto reportado con exito.";
    }

    public Map<Candidato, Integer> obtenerResultados() {
        Map<Candidato, Integer> resultados = new LinkedHashMap<>();
        for (Candidato candidato : listaCandidatos) {
            resultados.put(candidato, candidato.getCantidadVotos());
        }
        return resultados;
    }

    private Candidato buscarCandidatoPorID(int id) {
        for (Candidato c : listaCandidatos) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
}
