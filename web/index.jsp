<%-- 
    Document   : index
    Created on : Apr 21, 2018, 6:26:35 PM
    Author     : chracyc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="index.css">
        <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script src="ios-parallax.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#top-image').iosParallax({
                    movementFactor: 40,
                    dampenFactor: 36
                });
            });
        </script>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Anton|Fira+Sans+Condensed" rel="stylesheet">
        <title>Search</title>
    </head>
    <body>
        <div id="top-image">
            <div class="toolbar">
                <a href="index.jsp" class="toolbar-icon">
                    <i class="material-icons">home</i>
                </a>
            </div>
            <div class="header">
                <h1 class="header-title">Search Movie</h1>
            </div>
            <div class="wrapper">
                <div class="search-wrapper">
                    <%--
                        String[] filter = (String[]) request.getParameterValues("filt");
                        String test = "";
                        for (int i = 0; i < filter.length; i++) {
                            test += filter[i];
                        }
                    
                    <form action="movieDetail.jsp?q=<%=test%>" method="post" class="form-flex">--%>
                    <form action="" method="" class="form-flex">
                        <div class="search-input-wrapper">
                            <input class="search-field form-control" type="text" name="query" id="query" value="">
                            <button type="button" class="btn btn-color" onclick="doSubmitForm()">Search</button>

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
                                    filter += filt[i].value+",";
                                }
                            }
                            var location = "movieDetail.jsp?query="+query+"&field="+filter;
                            //window.navigate(location);
                            window.location.href=location;
                        }
                    </script>
                    <%--
                    for (int i = 0; i < filter.length; i++) {
                        System.out.println(filter[i]);
                    }
                    --%>
                </div>
            </div>
        </div>
    </body>
</html>
