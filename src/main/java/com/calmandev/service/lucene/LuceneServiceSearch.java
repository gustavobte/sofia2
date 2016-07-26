package main.java.com.calmandev.service.lucene;

import java.io.IOException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;

public class LuceneServiceSearch {

	/**
	 * Realiza a pesquisa retornando os resultados
	 * 
	 * @param query
	 * @param searcher
	 * @return
	 * @throws IOException
	 */
	public ScoreDoc[] search(Query query, IndexSearcher searcher) throws IOException {
		TopScoreDocCollector collector = TopScoreDocCollector.create(LuceneService.HITS_PER_PAGE);
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}
}