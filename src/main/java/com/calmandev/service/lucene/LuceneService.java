package main.java.com.calmandev.service.lucene;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import main.java.com.calmandev.model.Endereco;

public class LuceneService {

	static final int HITS_PER_PAGE = 50;
	static final String INDEX_DIRECTORY = "index";

	private Directory directory;
	private IndexSearcher indexSearcher;
	private Query query;
	private ScoreDoc[] scoreDocs;
	private StandardAnalyzer standardAnalyzer;

	private LuceneServiceBuildResult lsBuildResult;
	private LuceneServiceSearch lsSearch;
	private LuceneServiceBuildQuery lsBuildQuery;
	private LuceneServiceIndexer lsIndexer;

	public LuceneService() {
		this.standardAnalyzer = new StandardAnalyzer();
		this.lsBuildResult = new LuceneServiceBuildResult();
		this.lsSearch = new LuceneServiceSearch();
		this.lsBuildQuery = new LuceneServiceBuildQuery();
		this.lsIndexer = new LuceneServiceIndexer();
	}

	/**
	 * Gera a pontuação dos endereços
	 * 
	 * @param enderecoCandidatoJson
	 * @param enderecosComparacaoJson
	 * @throws IOException
	 */
	public void generateScore(Endereco enderecoCandidatoJson, ArrayList<Endereco> enderecosComparacaoJson)
			throws IOException {

		this.directory = this.lsIndexer.indexToDirectory(standardAnalyzer, enderecosComparacaoJson);
		this.query = this.lsBuildQuery.buildQuery(standardAnalyzer, enderecoCandidatoJson);

		IndexReader reader = DirectoryReader.open(directory);

		this.indexSearcher = new IndexSearcher(reader);
		this.scoreDocs = lsSearch.search(query, indexSearcher);

		lsBuildResult.buildResult(reader, indexSearcher, scoreDocs);
	}

}
