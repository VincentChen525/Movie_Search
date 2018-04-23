package com.eight.search;

public class Docs {
    private String title;
    private String content;
    private String docURL;
    private int totalDocs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDocURL() {
        return docURL;
    }

    public void setDocURL(String dosURL) {
        this.docURL = dosURL;
    }

    public Docs() {
        super();
    }

    public Docs(String title,String docURL, String content ,int totalDocs){
        super();
        this.title = title;
        this.content = content;
        this.docURL = docURL;
        this.totalDocs = totalDocs;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getTotalDocs() {
        return totalDocs;
    }

    public void setTotalDocs(int totalDocs) {
        this.totalDocs = totalDocs;
    }
}
