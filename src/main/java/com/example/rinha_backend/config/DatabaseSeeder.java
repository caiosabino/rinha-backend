//package com.example.rinha_backend.config;
//
//import com.example.rinha_backend.domain.entities.Usuario;
//import com.example.rinha_backend.domain.repository.UsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DatabaseSeeder implements CommandLineRunner {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        usuarioRepository.save(new Usuario("1",100000L, 0L));
//        usuarioRepository.save(new Usuario("2",80000L, 0L));
//        usuarioRepository.save(new Usuario("3",1000000L, 0L));
//        usuarioRepository.save(new Usuario("4",10000000L, 0L));
//        usuarioRepository.save(new Usuario("5",500000L, 0L));
//    }
//}
