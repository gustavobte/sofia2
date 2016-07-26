package main.java.com.calmandev;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("enderecos")
public class RESTServiceEnderecos {

	private LuceneService ls;

	public RESTServiceEnderecos() {
		ls = new LuceneService();
	}

	@POST
	@Path("/calculaScore")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response calculaScore(String enderecosJson) {
		Gson gson = new Gson();
		TypeDTO enderecos = gson.fromJson(enderecosJson, TypeDTO.class);
		enderecos.candidatos = SearchFiles.calculaScore(enderecos);

		return Response.status(200).entity(gson.toJson(enderecos.candidatos)).build();
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
