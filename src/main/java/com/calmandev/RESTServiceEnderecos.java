package main.java.com.calmandev;

import java.io.InputStream;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/enderecos")
public class RESTServiceEnderecos {

	@GET
	@Path("/indexar")
	@Produces(MediaType.TEXT_PLAIN)
	public Response indexarEnderecos(InputStream incomingData) {
		IndexFiles.indexar();
		return Response.status(200).entity("Indexado com sucesso !").build();
	}

	@POST
	@Path("/calculaScoreCandidatos")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response calculaScoreCandidatos(String enderecos) {
		Gson gson = new Gson();
		TypeDTO myTypes = gson.fromJson(enderecos, TypeDTO.class);
		myTypes.candidatos = SearchFiles.calculaScore(myTypes);

		return Response.status(200).entity(gson.toJson(myTypes.candidatos)).build();
	}

	@POST
	@Path("/calculaScoreComparacao")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response calculaScoreComparacao(String enderecoComparacao) {
		Gson gson = new Gson();
		TypeDTO[] myTypes = gson.fromJson(enderecoComparacao, TypeDTO[].class);
		myTypes[0].candidatos = SearchFiles.calculaScore(myTypes[0]);

		return Response.status(200).entity(gson.toJson(myTypes[0].candidatos)).build();
	}
}

class TypeDTO {
	ArrayList<ItemDTO> candidatos;
	ArrayList<ItemDTO> comparacao;
}

class ItemDTO {
	Long id;
	String endereco;
	Double pontuacao;
}
