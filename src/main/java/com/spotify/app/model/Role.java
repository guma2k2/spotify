package com.spotify.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`role`")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @Column(length = 20,unique = true)
    private String name ;


    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true , mappedBy = "role")
    @JsonManagedReference
    @Builder.Default
    private List<User> users = new ArrayList<>() ;

    public void addUser(User user) {
        if(users == null) {
            users = new ArrayList<>();
        }
        users.add(user) ;
        user.setRole(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setRole(null);
    }
}
