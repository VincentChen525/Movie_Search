package com.eight.search;

import Classes.MovieDoc;
import Classes.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import IndexingLucene.MyIndexReader;
import PseudoRFSearch.*;
import SearchLucene.*;



@WebServlet(name = "com.eight.search.SearchServlet")
public class SearchServlet extends HttpServlet {
    private static int totalDocs = 0;
    private static final int PAGE_SIZE = 2;// 指定每一页显示10条记录
    private int pageCount = 1;// 总共的页数
    private int rowCount = 1;// 总共有多少条记录
    private int pageNow = 1;// 当前要显示的页数，初始化为1
    private String indexPathStr;

    @Override
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        //创建索引
        Path.Index = config.getServletContext().getRealPath("/index");
        System.out.println("indexPathStr=" + indexPathStr);
        String dataPath = config.getServletContext().getRealPath("/data/test.json");
        System.out.println("dataPath=" + dataPath);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String query = request.getParameter("query");
        String field = request.getParameter("field");
        System.out.println("查询： " + query);

        // 计算查询时间
        long starTime = System.currentTimeMillis();// start time

        if (!"".equals(query) && query != null) {
            //ArrayList<Docs> docList = getSearch(query, indexPathStr);
            List<Classes.MovieDoc> results = new ArrayList<Classes.MovieDoc>();
            try{
                results = getTopDoc(query, Path.Index,request);
//                results = PRFSearchModel.RetrieveQuery(query, 20, 100, 0.4);
            }catch (Exception e){
                e.printStackTrace();
            }

            //test
            if (results != null) {
                for (Classes.MovieDoc result : results) {
                    System.out.println(result.getDirector());
                    break;
                }
            }

            totalDocs = results.size();
            System.out.println("条数： " + totalDocs);
            //分页
            String temp_pageNow = request.getParameter("pageNow");
            if (temp_pageNow != null) {
                pageNow = Integer.parseInt(temp_pageNow);
            }
            rowCount = totalDocs;//条数
            pageCount = (rowCount - 1) / PAGE_SIZE + 1;//页数

            List<Classes.MovieDoc> pagelist = results.subList(PAGE_SIZE * (pageNow - 1),
                    PAGE_SIZE * pageNow < rowCount ? PAGE_SIZE * pageNow : rowCount);

            // return request
            if (results.size() != 0) {
                //System.out.println("文档长度servlet docList length:" + docList.size());
                request.setAttribute("query", query);
                request.setAttribute("field",field);
                request.setAttribute("docList", pagelist);
                request.setAttribute("totalDocs", totalDocs);
                long endTime = System.currentTimeMillis();// end time
                long Time = endTime - starTime;
                request.setAttribute("time", (double) Time / 1000);

                request.setAttribute("pageNow", pageNow);
                request.setAttribute("pageCount", pageCount);

                request.getRequestDispatcher("movieDetail.jsp").forward(request, response);
            }
            else {
                System.out.println("error");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else {
            System.out.println("error");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public static List<MovieDoc> getTopDoc(String key, String indexpathStr, HttpServletRequest request) {
        ArrayList<MovieDoc> movieList = new ArrayList<MovieDoc>();

        Directory directory = null;
        try {
            // 设置要查询的索引目录
            directory = FSDirectory.open(FileSystems.getDefault().getPath(indexpathStr));
            // 创建indexSearcher
            DirectoryReader dReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(dReader);

            String field = request.getParameter("field");
            String[] fields = {"movies_title","movies_genres","movies_director","movies_synopsis","movies_actors"};
            if (!field.isEmpty()){
                fields = request.getParameter("field").split(",");
            }
            System.out.println(fields.toString());
            // 设置分词方式
            Analyzer analyzer = new StandardAnalyzer();// 标准分词
            MultiFieldQueryParser parser2 = new MultiFieldQueryParser(fields, analyzer);
            Query query2 = parser2.parse(key);

            QueryScorer scorer = new QueryScorer(query2);
            SimpleHTMLFormatter fors = new SimpleHTMLFormatter("<span style=\"color:#dd2e31;\">", "</span>");
            Highlighter highlighter = new Highlighter(fors, scorer);
            // 返回前10条
            TopDocs topDocs = searcher.search(query2, 20);

            if (topDocs != null) {
                long totalMoive = topDocs.totalHits;
                System.out.println("符合条件第文档总数：" + totalMoive);
                for (int i = 0; i < topDocs.scoreDocs.length; i++) {
                    Document doc = searcher.doc(topDocs.scoreDocs[i].doc);

                    Fragmenter fragment = new SimpleSpanFragmenter(scorer);

                    highlighter.setTextFragmenter(fragment);

                    String hl_title = highlighter.getBestFragment(TokenSources.getAnyTokenStream(searcher.getIndexReader(),
                            topDocs.scoreDocs[i].doc, "movies_title", analyzer), doc.get("movies_title"));
                    String hl_genres = highlighter.getBestFragment(TokenSources.getAnyTokenStream(searcher.getIndexReader(),
                            topDocs.scoreDocs[i].doc, "movies_genres", analyzer), doc.get("movies_genres"));
                    String hl_director = highlighter.getBestFragment(TokenSources.getAnyTokenStream(searcher.getIndexReader(),
                            topDocs.scoreDocs[i].doc, "movies_director", analyzer), doc.get("movies_director"));
                    String hl_summary = highlighter.getBestFragment(TokenSources.getAnyTokenStream(searcher.getIndexReader(),
                    topDocs.scoreDocs[i].doc, "movies_synopsis", analyzer), doc.get("movies_synopsis"));
                    String hl_actors = highlighter.getBestFragment(TokenSources.getAnyTokenStream(searcher.getIndexReader(),
                            topDocs.scoreDocs[i].doc, "movies_actors", analyzer), doc.get("movies_actors"));

                    MovieDoc movie = new MovieDoc(doc.get("movies_id"),
                            hl_title == null ? doc.get("movies_title") : hl_title,
                            doc.get("movies_rating"),
                            hl_director == null ? doc.get("movies_director") : hl_director,
                            hl_summary == null ? doc.get("movies_synopsis") : hl_summary,
                            hl_genres == null ? doc.get("movies_genres") : hl_genres,
                            doc.get("movies_url"),
                            hl_actors == null ? doc.get("movies_actors") : hl_actors,
                            doc.get("movies_poster"),
                            doc.get("movies_duration"),
                            doc.get("movies_releaseDate"));
                    movieList.add(movie);
                    System.out.println(movie);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieList;
    }
}