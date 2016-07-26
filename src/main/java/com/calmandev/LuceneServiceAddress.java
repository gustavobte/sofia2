package main.java.com.calmandev;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

public class LuceneServiceAddress {

	/**
	 * @param indexWriterw
	 * @param endereco
	 * @throws IOException
	 */
	public void addAddress(IndexWriter indexWriterw, String endereco) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("endereco", endereco, Field.Store.YES));
		indexWriterw.addDocument(doc);
	}
}