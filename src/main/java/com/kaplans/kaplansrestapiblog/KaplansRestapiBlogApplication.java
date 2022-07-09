package com.kaplans.kaplansrestapiblog;

import com.kaplans.kaplansrestapiblog.data.entity.Role;
import com.kaplans.kaplansrestapiblog.data.entity.User;
import com.kaplans.kaplansrestapiblog.data.repository.IRoleRepository;
import com.kaplans.kaplansrestapiblog.data.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@PropertySource("classpath:application-aws.properties")
public class KaplansRestapiBlogApplication implements CommandLineRunner {

    public KaplansRestapiBlogApplication(IRoleRepository iRoleRepository, IUserRepository iUserRepository, PasswordEncoder passwordEncoder) {
        this.iRoleRepository = iRoleRepository;
        this.iUserRepository = iUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(KaplansRestapiBlogApplication.class, args);
    }

    private final IRoleRepository iRoleRepository;
    private final IUserRepository iUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        Role roleUser= new Role();
        roleUser.setName("ROLE_USER");
        Role roleAdmin= new Role();
        roleAdmin.setName("ROLE_ADMIN");
        iRoleRepository.save(roleUser);
        iRoleRepository.save(roleAdmin);


        User admin = new User();
        admin.setUserName("admin");
        admin.setEMail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.getRoles().add(roleAdmin);
        iUserRepository.save(admin);

        User kaplan = new User();
        kaplan.setUserName("kaplan ");
        kaplan.setEMail("kaplan@gmail.com");
        kaplan.setPassword(passwordEncoder.encode("kaplan"));
        kaplan.getRoles().add(roleUser);
        iUserRepository.save(kaplan);

    }
}
