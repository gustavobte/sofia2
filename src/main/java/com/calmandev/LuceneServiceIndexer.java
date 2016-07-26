package main.java.com.calmandev;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

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
	Directory indexToDirectory(StandardAnalyzer analyzer) {
		Directory index = null;
		try {
			index = new MMapDirectory(Paths.get(LuceneService.INDEX_DIRECTORY));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);

			IndexWriter indexWriter = new IndexWriter(index, config);
			this.lsAddress.addAddress(indexWriter,
					"899 38415-099, 	Rua Biotita, 	Dona Zulmira,Uberlândia	30/06/2014;");
			this.lsAddress.addAddress(indexWriter, "899 Bath Avenue, Chautauqua, Wyoming, 3393");
			this.lsAddress.addAddress(indexWriter, " 899 69058-411,	Travessa 2,		Bairro Joao,Goiânia, 21/08/2008");
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return index;
	}
}