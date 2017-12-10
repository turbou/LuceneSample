import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Main2 {

    public static void main(String[] args) throws IOException, ParseException {
        Path rootPath = Paths.get("C:\\Users\\turbou\\Desktop\\Private\\tabocom");
        String outputDir = "output";
        Directory directory = FSDirectory.open(Paths.get(outputDir));
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        JapaneseAnalyzer japaneseAnalyzer = new JapaneseAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(japaneseAnalyzer);
        config.setOpenMode(OpenMode.CREATE);
        // Create a writer
        IndexWriter writer = new IndexWriter(directory, config);

        MyFileVisitor myVisitor = new MyFileVisitor(writer);

        try {
            Files.walkFileTree(rootPath, myVisitor);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        writer.close();

        // Now let's try to search for Hello
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("content", japaneseAnalyzer);
        Query query = parser.parse("oyoyo");
        TopDocs results = searcher.search(query, 1);
        System.out.println("Hits for oyoyo --> " + results.totalHits);
    }

}
