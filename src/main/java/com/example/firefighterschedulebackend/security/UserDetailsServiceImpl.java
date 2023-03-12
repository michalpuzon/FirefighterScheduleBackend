package com.example.firefighterschedulebackend.security;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final FirefighterRepository firefighterRepository;

    public UserDetailsServiceImpl(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String workNumber) throws UsernameNotFoundException {
        int workNumberInt = Integer.parseInt(workNumber);
        Firefighter user = firefighterRepository.findByWorkNumber(workNumberInt).orElseThrow();
        if (user == null) {
            throw new UsernameNotFoundException("Firefighter not found");
        }
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getWorkNumber()), user.getPassword(), Collections.emptyList());
    }

    public UserDetails loadUserByWorkNumber(int workNumber) throws UsernameNotFoundException {
        Firefighter user = firefighterRepository.findByWorkNumber(workNumber).orElseThrow();
        if (user == null) {
            throw new UsernameNotFoundException("Firefighter not found");
        }
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getWorkNumber()), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
    }
}
