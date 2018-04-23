
<%--
  Created by IntelliJ IDEA.
  User: eightant
  Date: 2016/12/16
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.Math" %>
<%@ page import="Classes.MovieDoc" %>
<%--<%@ page import="com.eight.search.Docs" %>
<%@ page import="Classes.Document" %>--%>

<%
    String query = (String) request.getAttribute("query");
    List<MovieDoc> list = (List<MovieDoc>) request.getAttribute("docList");
    int totalDoc = (int) request.getAttribute("totalDocs");
    double time = Double.parseDouble(request.getAttribute("time").toString());
    int pageNow = (int) request.getAttribute("pageNow");
    int pageCount = (int) request.getAttribute("pageCount");
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="movieDetail.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Anton|Fira+Sans+Condensed" rel="stylesheet">
        <title>Movie Detail</title>
    </head>
    <body>
        <div class="toolbar">
            <a href="index.jsp" class="toolbar-icon">
                <i class="material-icons">home</i>
            </a>
        </div>
        <div class="header">
                <div class="search-wrapper">
                        <form action="" method="" class="form-flex">
                            <div class="search-input-wrapper">
                                <input class="search-field form-control" type="text" name="query" id="query" value="">
                                <!--<button type="button" class="btn btn-color" onclick="doSubmitForm()">Search</button>-->
                                <i class="material-icons toolbar-search-icon" onclick="doSubmitForm()">search</i>
                            </div>
                            <!--<div style="width:100%"></div>-->
                            <div class="filter-wrapper">
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="filt" value="movies_title" id="movies_title">
                                    <label class="form-check-label" for="movies_title">Movie Title</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="filt" value="movies_genres" id="movies_genres">
                                    <label class="form-check-label" for="movies_genres">Genres</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="filt" value="movies_director" id="movies_director">
                                    <label class="form-check-label" for="movies_director">Director</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="filt" value="movies_actors" id="movies_actors">
                                    <label class="form-check-label" for="movies_actors">Actor</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="checkbox" name="filt" value="movies_synopsis" id="movies_synopsis">
                                    <label class="form-check-label" for="movies_synopsis">Synopsis</label>
                                </div>
                            </div>
                        </form>
        
                        <script>
                            function doSubmitForm() {
                                var query = document.getElementById("query").value;
                                var filter = "";
                                var filt = document.getElementsByName("filt");
                                for (var i = 0; i < filt.length; i++) {
                                    if (filt[i].checked) {
                                        filter += filt[i].value + ",";
                                    }
                                }
                                var location = "movieDetail.jsp?query=" + query + "&field=" + filter;
                                //window.navigate(location);
                                window.location.href = location;
                            }
                        </script>
        </div>
        <div class="body-wrapper">
            <div class="whole-wrapper">
                <div class="totaldoc-wrapper">
                    About <span><%=totalDoc%></span> Result (<span><%=time%></span> seconds)
                </div>
                <%
                    if (list.size() > 0) {
                        Iterator<MovieDoc> iter = list.iterator();
                        MovieDoc movie;
                        while (iter.hasNext()) {
                            movie = iter.next();
                %>
                <span>
                    <a href="<%=movie.getURL()%>">
                        <span class="title"><%=movie.getTitle()%></span>
                    </a>
                    <span class="year">(<%=movie.getId()%>)</span>
                </span>
                <div class="rate-wrapper">
                    <% for (int i = 0; i < (Math.floor(Double.valueOf(movie.getRaing()) / 2.0)); i++) {%>
                    <i class="star material-icons">star</i>
                    <%}%>
                    <% for (int i = 0; i < (Math.round(Double.valueOf(movie.getRaing()) % 2.0)); i++) {%>
                    <i class="star material-icons">star_half</i>
                    <%}%>
                    <% for (int i = 0; i < (5 - Math.floor(Double.valueOf(movie.getRaing()) / 2.0) - Math.round(Double.valueOf(movie.getRaing()) % 2.0)); i++) {%>
                    <i class="star material-icons">star_border</i>
                    <%}%>
                    &nbsp;<%=movie.getRaing()%> ratings
                </div>
                <div>
                    <div class="left-wrapper">
                        <p>Duration: <%=movie.getId()%> min</p>
                        <p>Director: <%=movie.getDirector()%></p>
                        <p>Genres:
                            <% for (int i = 0; i < movie.getGenres().split(" ").length; i++) {%>
                            <%=movie.getGenres().split(" ")[i]%>
                            <% if (i != movie.getGenres().split(" ").length - 1) {%>
                            <%=","%>
                            <%}%>
                            <%}%>
                        </p>
                        // !!!!!!1
                        <p>Stars:
                            <%String parseMovie = movie.getActors().replaceAll("[{'}]","");%>
                            <%String[] actors = parseMovie.split(",");%>
                            <%for (int i = 0; i < actors.length; i++) {%>
                            <%=actors[i]%>
                            <% if (i != actors.length - 1) {%>
                            <%=","%>
                            <%}%>
                            <%}%>
                        </p>
                        <p>Date: <%=movie.getId()%></p>
                    </div>
                    <div class="right-wrapper">
                        <img class="poster" src=<%=movie.getPoster()%>>
                    </div>
                </div>
                <br />
                <hr />
                <br />
                <div class="section-wrapper">
                    <a class="section-title"><%=movie.getTitle()%>'s Overview · · · · · ·</a>
                    <p><%=movie.getSynopsis()%></p>
                </div>
                <%}%>
            <%}%>
            </div>

        </div>
        <br />
        <br />
        <div>
            <ul class="pagination justify-content-center">

                <%
                    if (pageNow != 1) {
                %>
                <li class="page-item"><a class="page-link" href="movieDetail.jsp?query=<%=query%>&&pageNow=<%=pageNow - 1%>">Previous</a></li>
                    <%
                        }
                    %>

                <%
                    for (int i = 1; i <= pageCount; i++) {
                        if (i != pageNow) {
                %>
                <li class="page-item"><a class="page-link" href="movieDetail.jsp?query=<%=query%>&&pageNow=<%=i%>"><%=i%></a></li>
                    <%
                    } else {
                    %>
                <li class="page-item active"><a class="page-link" href="movieDetail.jsp?query=<%=query%>&&pageNow=<%=i%>"><%=i%></a></li>
                    <%
                            }
                        }
                    %>
                    <%
                        if (pageNow != pageCount) {
                    %>
                <li class="page-item"><a class="page-link" href="movieDetail.jsp?query=<%=query%>&&pageNow=<%=pageNow + 1%>">Next</a></li>
                    <%
                        }
                    %>
            </ul>
            <hr>
        </div>
        <script>
            function doSubmitForm() {
                var query = document.getElementById("query").value;
                var location = "movieDetail.jsp?query=" + query;
                //window.navigate(location);
                window.location.href = location;
            }
        </script>
    </body>
</html>

