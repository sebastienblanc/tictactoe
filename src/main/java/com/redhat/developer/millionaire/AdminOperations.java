package com.redhat.developer.millionaire;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.redhat.developer.tictactoe.dto.BoardDTO;
import com.redhat.developer.tictactoe.dto.ContestDTO;
import com.redhat.developer.tictactoe.dto.ScoreDTO;
import com.redhat.developer.tictactoe.dto.ServerSideEventMessage;
import com.redhat.developer.tictactoe.model.Contest;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.annotations.SseElementType;

import io.smallrye.mutiny.Multi;


@Path("/admin")
@Transactional
@ApplicationScoped
public class AdminOperations {

    @Inject
    IdentifierGenerator identifierGenerator;

    @Inject
    ContestState state;

    @Inject
    Statistics statistics;

    @Inject
    ScoreInformation scoreInformation;

    @Inject
    GamerManager gamerManager;

    @Inject
    VoteRoller task;

    @Inject @Channel("admin-stream") Multi<String> streamOfAdmin;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<String> stream() {
        return streamOfAdmin;
    }

    @POST
    @Path("/import")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response importContest(ContestDTO contestDTO) {
        Contest contest = persistContest(contestDTO);
        return Response.status(Status.CREATED).entity(contest.contestId).build();
    }

    private Contest persistContest(ContestDTO contestDTO) {
        final Contest contest = new Contest();
        contest.creationTime = new Date();
        contest.name = contestDTO.getName();
        contest.contestId = identifierGenerator.generateId(contest.name);
        if (contestDTO.getDurationBetweenRounds() != null) {
            contest.timeBetweenRounds = contestDTO.getDurationBetweenRounds();
        }


        contest.initContest();
        contest.persist();
    

        return contest;
    }


    @POST
    @Path("/init/{contestId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response initContest(@PathParam("contestId") String contestId) {

        System.out.println("Initializing " + contestId);

        return Contest.findByContestId(contestId)
            .map(contest -> {
                state.startContest(contest);
                return Response.ok().build();
            })
            .orElseGet(() -> Response.status(Status.NOT_FOUND)
                            .entity("Contest Id" + contestId + "not found.").build()
            );
    }



    @POST
    @Path("/start/{contestId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response startContest(@PathParam("contestId") String contestId) {

        System.out.println("Starting " + contestId);

        Response response = state.getCurrentContest()
            .filter(c -> c.contestId.equals(contestId))
            .map(c -> {
                task.sendBoard(BoardDTO.of(c.board));
                return Response.ok().entity(BoardDTO.of(c.board)).build();
            })
            .orElseGet(() -> Response.status(Status.NOT_FOUND)
                            .entity("Contest Id" + contestId + "not found.").build());
        
        return response;
        
    }

    
    @GET
    @Path("/score")
    @Produces(MediaType.APPLICATION_JSON)
    public ScoreDTO getScore(@QueryParam("limit") int limit) {
        return new ScoreDTO(gamerManager.getUsernameScore(limit));
    }
   
    @POST
    @Path("/reset")
    public Response endContest() {
        state.reset();
        statistics.reset();
        scoreInformation.reset();

        return Response.ok().build();
    }
}