package main.java.com.calmandev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import javax.annotation.processing.FilerException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LegacyLongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexFiles {

	private static final String INDEX_PATH = "C:\\bd\\index";
	private static final String DATA_PATH = "C:\\bd\\test data";
	private static final Boolean CREATE = true;

	public static final void indexar() {
		Path absolutePath = Paths.get(DATA_PATH).toAbsolutePath();

		if (!Files.isReadable(absolutePath)) {
			trataErro(new FilerException("Diretorio '" + DATA_PATH + "' não está disponível."));
			System.exit(1);
		}

		Date inicio = new Date();
		try {
			System.out.println("Indexando para o diretorio '" + INDEX_PATH + "'...");

			Directory dir = FSDirectory.open(Paths.get(INDEX_PATH));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(CREATE ? OpenMode.CREATE : OpenMode.CREATE_OR_APPEND);
			iwc.setRAMBufferSizeMB(256.0);

			IndexWriter writer = new IndexWriter(dir, iwc);
			escolherMetodoIndexacao(writer, absolutePath);
			writer.forceMerge(1);

			writer.close();

			Date fim = new Date();
			System.out.println(fim.getTime() - inicio.getTime() + " ms");

		} catch (IOException e) {
			trataErro(e);
		}
	}

	static void escolherMetodoIndexacao(IndexWriter writer, Path absolutePath) {
		try {
			if (Files.isDirectory(absolutePath)) {
				indexDocs(writer, absolutePath);
			} else {
				indexDoc(writer, absolutePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void indexDocs(IndexWriter writer, Path path) {
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					try {
						indexDoc(writer, file);
					} catch (IOException ignore) {
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			trataErro(e);
		}
	}

	static void indexDoc(IndexWriter writer, Path path) throws IOException {
		long lastModified = Files.getLastModifiedTime(path).toMillis();
		try (InputStream stream = Files.newInputStream(path)) {

			Document doc = new Document();

			Field pathField = new StringField("path", path.toString(), Field.Store.YES);
			doc.add(pathField);
			doc.add(new LegacyLongField("modified", lastModified, Field.Store.NO));
			doc.add(new TextField("contents",
					new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

			if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
				System.out.println(" + " + path);
				writer.addDocument(doc);
			} else {
				System.out.println(" u " + path);
				writer.updateDocument(new Term("path", path.toString()), doc);
			}
		}

	}

	private static void trataErro(Exception e) {
		System.out.println(" Erro na classe " + e.getClass() + "\n mensagem: " + e.getMessage());
	}

}
