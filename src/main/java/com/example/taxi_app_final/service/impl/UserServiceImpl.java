package com.example.taxi_app_final.service.impl;

import com.example.taxi_app_final.model.Car;
import com.example.taxi_app_final.model.DriverStatus;
import com.example.taxi_app_final.model.Role;
import com.example.taxi_app_final.model.User;
import com.example.taxi_app_final.repository.UserRepository;
import com.example.taxi_app_final.service.UserSerivce;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserSerivce {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
//    }
    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s).orElseThrow(()->new UsernameNotFoundException(s));
    }

    @Override
    public List<User> findAllDrivers() {
        return userRepository.findByRole(Role.ROLE_ADMIN);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findByRoleAndId(Role.ROLE_ADMIN,id);
    }

    @Override
    public List<User> findDriverByStatus() {
        return userRepository.findAllByStatusAndRole(DriverStatus.AVAILABLE, Role.ROLE_ADMIN);
    }

    @Override
    public List<Car> findCarsForPassengers(int capacity) {
        return userRepository.findAvailableCarsByCapacity(capacity, DriverStatus.AVAILABLE);
    }

    @Override
    public User setStatus(Long id) {
        User user = findById(id).orElseThrow(RuntimeException::new);
        user.setStatus(DriverStatus.UNAVAILABLE);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByCar(Car car) {
        return userRepository.findByCar(car);
    }

    @Override
    public User update(Long id, String name, String surname, String username) {
        User user = userRepository.findById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setUsername(username);
        // Re-authenticate the user
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return userRepository.save(user);
    }


    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role userRole) {
        /*if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);*/
        User user = new User(username,passwordEncoder.encode(password),name,surname,userRole);
        return userRepository.save(user);
    }
    

}