package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final MailSender mailSender;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(MailSender mailSender, UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.mailSender = mailSender;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Value("${hostname}")
    private String hostname;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public boolean addUser(User user) {
//        Создается новая переменная, которой присваивается
//        пользователь из базы данных совпадающий с получаемым в форме регистрации
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

//        Если пользователь новый(нет совпадений), то ему задается активность, дается роль,
//        сохраняется через репозиторий и перенапрявляется на страницу входа
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
//        Добавляет поле с рандомным кодом активации
//        При совпадении почта будет подтверждена
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
//        Отправляет письмо для подтверждения
        sendMessage(user);

        return true;
    }


    public boolean activateUser(String code) {
//        Ищет пользователя по коду активации
        User user = userRepo.findByActivationCode(code);

//        Если нету такого, то возвращает null
        if (user == null) {
            return false;
        }
//        Если же есть, то анулирует код, чтобы не мог повторно вктивировать
        user.setActivationCode(null);

//        Сохраняет в базу данных и возвращает true
        userRepo.save(user);

        return true;
    }

//    Поиск всех пользователей
    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

//        Получаем список ролей
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

//        Убираем роли у пользователя, для добавления по новой
        user.getRoles().clear();

//        Добавление ролей
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

//    Обновление профиля
    public void updateProfile(User user, String password, String email) {
        String userEmail = user.getEmail();

//        Проверка и изменение почты
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

//        Если почта поменялась, то обновляем у профился
//        Если пользователь не удалил почту, то задаем код активации
        if (isEmailChanged) {
            user.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
               user.setActivationCode(UUID.randomUUID().toString());
            }
        }

//        Если пароль не пуст, то обновляем его и сразу шифруем
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }

//        Сохраняем пользователя
        userRepo.save(user);

//        Если почта изменена, то высылаем код активации для подтверждения
        if (isEmailChanged) {
            sendMessage(user);
        }
    }


    private void sendMessage(User user) {
        //        Если поле почты не пустой, то отправляет пользователю почту
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://%s/activate/%s",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode()
            );

//            отправляет письмо на почту(user,getEmail()), с темой "Activation", и текстом message
            mailSender.send(user.getEmail(), "Activation", message);
        }
    }
}


