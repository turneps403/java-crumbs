# Forward Auth: middleware for Traefik 

There is application to play with ``Forward Auth`` middleware.

#### About
It implemets only two handlers:
0. `/jwt/auth` to authenticate JWT and if it would be successfully recognized then the value will be set to the header
1. `/jwt/login` to create JWT after success authentication

#### DickerHub
https://hub.docker.com/repository/docker/turneps403/jwt-spring-middleware

#### Useful links
* https://github.com/auth0/java-jwt
