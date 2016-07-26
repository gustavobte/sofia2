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
import main.java.com.calmandev.model.Enderecos;

public class LuceneService {

	static final int HITS_PER_PAGE = 50;
	static final String INDEX_DIRECTORY = "index";

	public Directory directory;
	public Query query;
	public StandardAnalyzer analyzer;
	public IndexSearcher searcher;
	public ScoreDoc[] hits;

	private LuceneServiceBuildResult lsBuildResult;
	private LuceneServiceSearch lsSearch;
	private LuceneServiceBuildQuery lsBuildQuery;
	private LuceneServiceIndexer lsIndexer;

	public LuceneService() {
		this.analyzer = new StandardAnalyzer();
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

		this.directory = this.lsIndexer.indexToDirectory(analyzer, enderecosComparacaoJson);
		this.query = this.lsBuildQuery.buildQuery(analyzer, enderecoCandidatoJson);

		IndexReader reader = DirectoryReader.open(directory);

		this.searcher = new IndexSearcher(reader);
		this.hits = lsSearch.search(query, searcher);

		lsBuildResult.buildResult(reader, searcher, hits);
	}

}
