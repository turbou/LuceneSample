import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private IndexWriter writer;;

    public MyFileVisitor(IndexWriter writer) {
        this.writer = writer;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return super.postVisitDirectory(dir, exc);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return super.preVisitDirectory(dir, attrs);
    }

    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
        String fileName = filePath.getFileName().toString();
        File file = filePath.toFile();
        if (fileName.endsWith(".xlsx") || fileName.endsWith(".pdf")) {
            System.out.format("%s\n", file);
            Document document = new Document();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                document.add(new TextField("content", br));
                writer.addDocument(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.visitFile(filePath, attrs);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        // TODO Auto-generated method stub
        return super.visitFileFailed(file, exc);
    }

}
