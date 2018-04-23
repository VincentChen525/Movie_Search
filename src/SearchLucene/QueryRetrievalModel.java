package SearchLucene;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import Classes.*;
import IndexingLucene.MyIndexReader;

public class QueryRetrievalModel {

	protected MyIndexReader indexReader;

	private Directory directory;
	private DirectoryReader ireader;
	private IndexSearcher indexSearcher;

	public QueryRetrievalModel(MyIndexReader ixreader) {
		try {
			directory = FSDirectory.open(Paths.get(Path.Index));
			ireader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(ireader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<MovieDoc> retrieveQuery(String aQuery, int TopN) throws Exception {
		List<MovieDoc> results = new ArrayList<MovieDoc>();
		Query theQ = new QueryParser("CONTENT", new WhitespaceAnalyzer()).parse(aQuery);
		ScoreDoc[] scoreDoc = indexSearcher.search(theQ, TopN).scoreDocs;
		for (ScoreDoc score : scoreDoc) {
			//results.add(new MovieDoc(score.doc + "", ireader.document(score.doc).get("DOCNO"), ireader.document(score.doc).get("test_content"),score.score));
		}
		return results;
	}

}