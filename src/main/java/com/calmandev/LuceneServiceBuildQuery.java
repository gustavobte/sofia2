package main.java.com.calmandev;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

public class LuceneServiceBuildQuery {

	/**
	 * Monta consulta
	 * 
	 * @param analyzer
	 * @return
	 */
	Query buildQuery(StandardAnalyzer analyzer) {
		QueryParser queryParser = new QueryParser("endereco", analyzer);
		Query query = null;
		try {
			query = queryParser.parse("899");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}
}