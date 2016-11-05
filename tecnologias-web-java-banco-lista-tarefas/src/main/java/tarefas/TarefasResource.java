package tarefas;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/tarefas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TarefasResource {

    @PersistenceContext(unitName = "TarefasPU")
    private EntityManager entityManager;
    
    @POST
    public Tarefa adicionar(Tarefa tarefa) {
        entityManager.persist(tarefa);
        return tarefa;
    }

    @GET
    public List<Tarefa> getTarefas() {
        return entityManager.createQuery("SELECT t FROM Tarefa t", Tarefa.class).getResultList();
    }

    @GET
    @Path("{id}")
    public Tarefa getTarefa(@PathParam("id") Integer id) {
        return entityManager.find(Tarefa.class, id);
    }

    @PUT
    @Path("{id}")
    public Tarefa atualizar(@PathParam("id") Integer id, Tarefa tarefaAtualizada) {
        Tarefa tarefaEncontrada = entityManager.find(Tarefa.class, id);
        tarefaEncontrada.setDescricao(tarefaAtualizada.getDescricao());
        tarefaEncontrada.setConcluida(tarefaAtualizada.getConcluida());
        return tarefaEncontrada;
    }

    @DELETE
    @Path("{id}")
    public Tarefa excluir(@PathParam("id") Integer id) {
        Tarefa tarefa = getTarefa(id);
        entityManager.remove(tarefa);
        return tarefa;
    }
}
