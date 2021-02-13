<!doctype html>
<html lang="en">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Days+One&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/home.css">
    <title>Hello, world!</title>
</head>

<body>

    <!-- NAVBAR -->

    <div class="navbar">
        <p id="logo">PORISKAR</p>
        <div class="navbar-right">
            <ul>
                <li><a id="home_link" href="/">Home</a></li>
                <li><a href="/drivers">Drivers</a></li>
                <li><a href="/routes">Routes</a></li>

                <% if(user) { %>
                    <li><a id="login_link" href="/logout">Log out</a></li>
                    <% } else { %>
                        <li><a id="login_link" href="/login">Log in</a></li>
                        <% } %>

            </ul>
        </div>
    </div>