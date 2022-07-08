package com.kaplans.kaplansrestapiblog.controller;


import com.kaplans.kaplansrestapiblog.data.entity.Role;
import com.kaplans.kaplansrestapiblog.data.entity.User;
import com.kaplans.kaplansrestapiblog.data.repository.IRoleRepository;
import com.kaplans.kaplansrestapiblog.data.repository.IUserRepository;
import com.kaplans.kaplansrestapiblog.dto.JWTAuthResponse;
import com.kaplans.kaplansrestapiblog.dto.LoginDTO;
import com.kaplans.kaplansrestapiblog.dto.SignUpDTO;
import com.kaplans.kaplansrestapiblog.security.JWTTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@Api(value = "Auto controller exposes sign in and sign up REST API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final IUserRepository iUserRepository;
    private final IRoleRepository iRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JWTTokenProvider jwtTokenProvider;

    //

    public AuthController(AuthenticationManager authenticationManager, IUserRepository iUserRepository, IRoleRepository iRoleRepository, PasswordEncoder passwordEncoder, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.iUserRepository = iUserRepository;
        this.iRoleRepository = iRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    //before jwt
    @PostMapping("/signin")
    public ResponseEntity<String> autehnticateUser(@RequestBody LoginDTO loginDTO){
       Authentication authentication= authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserNameOrEMail(),
                        loginDTO.getPassword() ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User signed in successfully", HttpStatus.OK);
    }




//
//    @ApiOperation("REST API to sign or or sign up user to Blog app")
//    @PostMapping("/signin")
//    public ResponseEntity<JWTAuthResponse> autehnticateUser(@RequestBody LoginDTO loginDTO) {
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserNameOrEMail(),
//                        loginDTO.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        //get token from provider
//        String token = jwtTokenProvider.generateToken(authentication);
//        return ResponseEntity.ok(new JWTAuthResponse(token));
//    }

    @ApiOperation("REST API to sign or or sign up user to Blog app")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        //add check for username exists in db
        if (iUserRepository.existsByUserName(signUpDTO.getUserName())) {
            return new ResponseEntity<>("username is already taken ", HttpStatus.BAD_REQUEST);
        }
        // add check for e mail in db
        if (iUserRepository.existsByEMail(signUpDTO.getEMail())) {
            return new ResponseEntity<>("eMail is already taken ", HttpStatus.BAD_REQUEST);
        }
        //create user

        User user = new User();
        user.setUserName(signUpDTO.getUserName());
        user.setEMail(signUpDTO.getEMail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Optional<Role> roles = iRoleRepository.findByName("ROLE_ADMIN");
        if (roles.isPresent()) {
            user.setRoles(Collections.singleton(roles.get()));
            iUserRepository.save(user);
            return new ResponseEntity<>("user successfully registered!", HttpStatus.OK);
        }
        return new ResponseEntity<>("there is a problem", HttpStatus.BAD_REQUEST);


    }


}
