package main.java.com.calmandev.service.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

import main.java.com.calmandev.model.Endereco;

public class LuceneServiceIndexer {

	private LuceneServiceAddress lsAddress;

	public LuceneServiceIndexer() {
		this.lsAddress = new LuceneServiceAddress();
	}

	/**
	 * Indexa em diretorio
	 * 
	 * @param analyzer
	 * @return
	 */
	Directory indexToDirectory(StandardAnalyzer analyzer, List<Endereco> enderecosComparacao) {
		Directory index = null;
		try {
			index = new MMapDirectory(Paths.get(LuceneService.INDEX_DIRECTORY));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);

			IndexWriter indexWriter = new IndexWriter(index, config);
			for (Endereco endereco : enderecosComparacao) {
				this.lsAddress.addAddress(indexWriter, endereco.endereco);
			}
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return index;
	}
}