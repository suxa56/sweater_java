package com.example.sweater.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usr")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;
    private boolean active;

    @Email(message = "Email is not correct")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    private String activationCode;

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

//    @ElementCollection позволяет избавиться от головной боли по формированию дополнительной таблицы для ENUM
//    fetch - метод подгрузки ролей;
//      lazy - медленная и подгружается по мере необходимости
//      eager - жадная, всё сразу подгружает
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable создает отдельную таблицу, для которой мы не прописывали mapping
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated Показывает, в каком виде хранить данные
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    //    UserDetails methods
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
