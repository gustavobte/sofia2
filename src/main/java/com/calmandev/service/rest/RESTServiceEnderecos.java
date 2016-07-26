package main.java.com.calmandev.service.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import main.java.com.calmandev.model.Enderecos;
import main.java.com.calmandev.service.lucene.LuceneService;

@Path("enderecos")
public class RESTServiceEnderecos {

	private LuceneService ls;

	public RESTServiceEnderecos() {
		ls = new LuceneService();
	}

	@POST
	@Path("calculaScore")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response calculaScore(String enderecosJson) {
		Gson gson = new Gson();
		Enderecos enderecos = gson.fromJson(enderecosJson, Enderecos.class);
		try {
			ls.generateScore(enderecos.candidato, enderecos.comparacao);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Response.status(200).entity("").build();
	}
}
