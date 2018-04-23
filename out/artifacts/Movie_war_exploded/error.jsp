
<%--
  Created by IntelliJ IDEA.
  User: eightant
  Date: 2016/12/16
  Time: 10:17
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.lang.Math" %>
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
        <title>No Result</title>
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
                        <i class="material-icons toolbar-search-icon" onclick="SubmitForm()">search</i>
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
                    function SubmitForm() {
                        var query = document.getElementById("query").value;
                        var filter = "";
                        var filt = document.getElementsByName("filt");
                        for (var i = 0; i < filt.length; i++) {
                            if (filt[i].checked) {
                                filter += filt[i].value + ",";
                            }
                        }
                        var location = "search?query=" + query + "&field=" + filter;
                        //window.navigate(location);
                        window.location.href = location;
                    }
                </script>
            </div>
        </div>
        <div class="body-wrapper">
            <div class="whole-wrapper">
                <div class="totaldoc-wrapper">
                    None Result Found.
                </div>
            </div>
        </div>
    </body>
</html>

