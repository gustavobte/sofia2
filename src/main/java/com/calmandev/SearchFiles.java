package main.java.com.calmandev;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class SearchFiles {

	private static final String INDEX = "C:\\bd\\index\\";
	private static final String FIELD = "contents";

	public static final ArrayList<ItemDTO> calculaScore(TypeDTO myTypes) {
		int hitsPerPage = 100;
		IndexReader reader;
		try {
			reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX)));

			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();

			QueryParser parser = new QueryParser(FIELD, analyzer);

			for (int i = 0; i < myTypes.candidatos.size(); i++) {
				Query query = parser.parse(myTypes.candidatos.get(i).endereco);
				System.out.println("Avaliando endereços com dados: " + query.toString(FIELD));
				searcher.search(query, 100);
				myTypes.candidatos.get(i).pontuacao = doSearch(searcher, query, hitsPerPage);
			}
			reader.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return myTypes.candidatos;
	}

	public static Double doSearch(IndexSearcher searcher, Query query, int hitsPerPage) throws IOException {

		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println("Achamos " + numTotalHits + " coincidências");

		int end = Math.min(numTotalHits, hitsPerPage);

		Double scores = 0D;

		for (int i = 0; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String path = doc.get("path");
			if (path != null) {
				System.out.println((i + 1) + ". " + path + " \t Pontuação: " + results.scoreDocs[i].score);
				if (i < 5) {
					scores += results.scoreDocs[i].score;
				}
			} else {
				System.out.println((i + 1) + ". " + "Não foi encontrado o diretorio !");
			}
		}

		return scores / 5;
	}
}
