<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lost in curly braces</title>
    <style>
        @import url('https://fonts.googleapis.com/css?family=Roboto+Mono');

        .text-container {
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            position: absolute;
        }

        html, body {
            font-family: 'Roboto Mono', monospace;
            font-size: 16px;
        }

        html {
            box-sizing: border-box;
            user-select: none;
        }

        body {
            background-color: #000;
        }

        *, *:before, *:after {
            box-sizing: inherit;
        }

        p {
            color: #fff;
            font-size: 24px;
            letter-spacing: 2px;
            margin: 0;
        }

    </style>
</head>
<body>
<div class="text-container">
    <p>
        <%
            String msg;
            int statusCode = response.getStatus();
            switch (statusCode) {
                case 400:
                    msg = "{\"message\": \"400, bad request\"}";
                    break;
                case 404:
                    msg = "{\"message\": \"404, page not found\"}";
                    break;
                case 500:
                    msg = "{\"message\": \"500, internal server error\"}";
                    break;
                default:
                    msg = "{\"message\": \"an unspecified error occurred\"}";
                    break;
            }
        %>
        <%=msg%>
    </p>
</div>
</body>
</html>
