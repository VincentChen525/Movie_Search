package Index;

import Classes.MovieDoc;
import com.google.gson.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.stream.JsonReader;


import Classes.Path;


public class CreateIndex {
    public static void main(String[] args) throws Exception {
        // 第一步：创建分词器
        Analyzer analyzer = new StandardAnalyzer();
        // 第二步：创建indexWriter配置信息
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        // 第三步：设置索引的打开方式
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // 第四步：设置索引第路径
        Directory directory = null;
        // 第五步:创建indexWriter,用于索引第增删改.
        IndexWriter indexWriter = null;
        String index = "/Users/VincentChan/Desktop/Pitt/IR/Movie/out/artifacts/Movie_war_exploded/index";
        try {
            directory = FSDirectory.open(FileSystems.getDefault().getPath(index));
            indexWriter = new IndexWriter(directory, indexWriterConfig);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 循环创建索引
        File file = new File("/Users/VincentChan/Desktop/Pitt/IR/Movie/out/artifacts/Movie_war_exploded/data/test.json");

        InputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");

        //create JsonReader object
        JsonReader reader = new JsonReader(isr);
        try {
            List<MovieDoc> movies = readMovieArray(reader);
            for(MovieDoc movie : movies) {
                Document doc = new Document();
                if (movie != null) {
                    System.out.println(movie.getId());
                    doc.add(new TextField("movies_actors", movie.getActors(), Field.Store.YES));

                    doc.add(new TextField("movies_id", movie.getId(), Field.Store.YES));
                    doc.add(new TextField("movies_title", movie.getTitle(), Field.Store.YES));
                    doc.add(new TextField("movies_director", movie.getDirector(), Field.Store.YES));
                    doc.add(new TextField("movies_genres", movie.getGenres(), Field.Store.YES));
                    doc.add(new TextField("movies_rating", movie.getRaing(), Field.Store.YES));
                    doc.add(new TextField("movies_synopsis", movie.getSynopsis(), Field.Store.YES));
                    doc.add(new TextField("movies_url", movie.getURL(), Field.Store.YES));
                    doc.add(new TextField("movies_poster", movie.getPoster(), Field.Store.YES));
                    doc.add(new TextField("movies_duration", movie.getDuration(), Field.Store.YES));
                    doc.add(new TextField("movies_releaseDate", movie.getReleaseDate(),Field.Store.YES));
                    try {
                        indexWriter.addDocument(doc);
                        indexWriter.commit();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            reader.close();
            indexWriter.close();
            directory.close();
        }
    }

    private static List<MovieDoc> readMovieArray(JsonReader reader) throws IOException {
        List<MovieDoc> messages = new ArrayList<MovieDoc>();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(readMovie(reader));
        }
        reader.endArray();
        return messages;
    }

    private static MovieDoc readMovie(JsonReader reader) throws IOException {
        String id = null;
        String IMDB_Link = null;
        String rating = null;
        String title = null;
        String director = null;
        String genres = null;
        String actors = null;
        String synopsis = null;
        String poster = null;
        String duration = null;
        String releaseDate = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("MovieId")) {
                id = reader.nextString();
            } else if (name.equals("IMDb_URL")) {
                IMDB_Link = reader.nextString();
            } else if (name.equals("Rating")) {
                rating = reader.nextString();
            } else if (name.equals("Title")) {
                title = reader.nextString();
            } else if (name.equals("Directors")) {
                director = reader.nextString();
            } else if (name.equals("Genres")) {
                genres = reader.nextString();
            } else if (name.equals("Actors")) {
                actors = reader.nextString();
            } else if(name.equals("Synopsis")){
                synopsis = reader.nextString();
            }else if(name.equals("Poster")){
                poster = reader.nextString();
            }else if(name.equals("Duration")){
                duration = reader.nextString();
            }else if(name.equals("ReleaseDate")){
                releaseDate = reader.nextString();
            }
            else{
                reader.skipValue();
            }

        }
        reader.endObject();
        return new MovieDoc(id, title, rating, director, synopsis, genres,
                IMDB_Link, actors, poster, duration,releaseDate);
    }
}