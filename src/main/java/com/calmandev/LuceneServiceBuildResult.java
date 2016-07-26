package main.java.com.calmandev;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;

public class LuceneServiceBuildResult {

	/**
	 * Gera resultado
	 * 
	 * @param reader
	 * @param searcher
	 * @param hits
	 * @throws IOException
	 */
	// TODO Retornar JSON com o endereço candidato, endereços comparados, sua
	// pontuação e seus matchs
	public void buildResult(IndexReader reader, IndexSearcher searcher, ScoreDoc[] hits) throws IOException {
		System.out.println("Achamos " + hits.length + " resultados.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". \t" + d.get("endereco") + " score: " + hits[i].score);
		}
		reader.close();
	}
}