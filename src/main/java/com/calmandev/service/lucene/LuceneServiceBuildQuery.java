package main.java.com.calmandev.service.lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import main.java.com.calmandev.model.Endereco;

public class LuceneServiceBuildQuery {

	/**
	 * Monta consulta
	 * 
	 * @param analyzer
	 * @return
	 */
	Query buildQuery(StandardAnalyzer analyzer, Endereco enderecoCandidato) {
		QueryParser queryParser = new QueryParser("endereco", analyzer);
		Query query = null;
		try {
			query = queryParser.parse(enderecoCandidato.endereco);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}
}