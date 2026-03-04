<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <style>
            .card{
                width: 350px;
            }

            .d-flex{
                display: flex;
                flex-direction: column;
            }
            .form-control{
                width: 50%;
                min-width: 220px;
            }
            .btn{
                transition: all 0.5s;
                transform-origin: center;
                margin-bottom: 16px;
                width: 25%;
            }

            .btn:hover{
                transform: scale(1.05);
            }

            h5{
                margin-top: 10px;
            }

            @media (max-width: 318px) {
                .btn{
                    width: 50%;
                }
            }
            h1{
                text-align: center;
                color: red;
            }
        </style>
        <div class="container-fluid" style="display: flex; justify-content: center; align-items: center; height: 100vh;">
            <div class="card" style="width: 600px;">
                <h5 class="card-title" style="text-align: center;">Log in</h5>
                <form action="MainController" method="post" class="d-flex">
                    <input class="form-control mx-auto mt-3 mb-0" type="text" placeholder="Enter your user ID" aria-label="userID" name="txtUsername" required>
                    <input class="form-control mx-auto mt-3 mb-0" type="password" placeholder="Enter your password" aria-label="password" name="txtPassword" required>
                    <button class="btn btn-outline-success mx-auto mt-3 mb-0" type="submit" name="action" value="Login">Submit</button>
                    <p style="text-align: center;">Forgot password? <a>Click here</a></p>
                    <p style="text-align: center;">Don't have an account? <a>Sign up</a> here</p>
                    <%
                        String error = (String) request.getAttribute("ERROR");
                        if (error != null) {
                    %>
                    <h1><%= error%></h1>
                    <%
                        }
                    %>

                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/js/bootstrap.bundle.min.js" integrity="sha384-ndDqU0Gzau9qJ1lfW4pNLlhNTkCfHzAVBReH9diLvGRem5+R9g2FzA8ZGN954O5Q" crossorigin="anonymous"></script>
    </body>
</html>