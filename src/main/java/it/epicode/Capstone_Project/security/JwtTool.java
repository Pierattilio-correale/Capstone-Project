package it.epicode.Capstone_Project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.Capstone_Project.exception.NotFoundException;
import it.epicode.Capstone_Project.model.User;
import it.epicode.Capstone_Project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtTool {
    @Autowired
    private UserService userService;

    @Value("${jwt.duration}")
private Long durata;

    @Value("${jwt.secret}")
private String chiaveSegreta;

    public String createToken(User user){



      return   Jwts.builder().issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+durata)).
                subject(user.getId()+"").signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).compact();

    }


    public void validateToken(String token){

        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).build().parse(token);
    }
    public User getUserFromToken(String token) throws NotFoundException {
        int id = Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes())).build().parseSignedClaims(token).getPayload().getSubject());
return userService.getUser(id);
    }
}
