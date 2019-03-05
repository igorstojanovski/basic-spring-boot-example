package org.igorski.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany
    private Set<User> members;
}
