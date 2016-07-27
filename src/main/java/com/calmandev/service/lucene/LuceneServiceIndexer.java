package main.java.com.calmandev.service.lucene;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private Path pathIndexDirectory;

	public LuceneServiceIndexer() {
		this.lsAddress = new LuceneServiceAddress();
		this.pathIndexDirectory = Paths.get(LuceneService.INDEX_DIRECTORY);
	}

	/**
	 * Indexa em diretorio
	 * 
	 * @param analyzer
	 * @return
	 */
	public Directory indexToDirectory(StandardAnalyzer analyzer, List<Endereco> enderecosComparacao) {
		Directory directory = null;
		try {
			directory = new MMapDirectory(pathIndexDirectory);
			removeOldIndexedFiles();

			IndexWriterConfig config = new IndexWriterConfig(analyzer);

			IndexWriter indexWriter = new IndexWriter(directory, config);
			for (Endereco endereco : enderecosComparacao) {
				this.lsAddress.addAddress(indexWriter, endereco.endereco);
			}
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directory;
	}

	/**
	 * Limpa diretorio dos arquivos indexados
	 * 
	 */
	public void removeOldIndexedFiles() {
		try {
			Files.newDirectoryStream(pathIndexDirectory).forEach(file -> {
				try {
					Files.delete(file);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}