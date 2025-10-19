package com.dmitry.NauJava.domain.group;

import com.dmitry.NauJava.domain.goal.Goal;
import com.dmitry.NauJava.domain.user.User;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Группа - объединение пользователей под 1 группой,
 * у группы могут быть общие цели
 */
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String organisation;
    private Boolean isPublic;
    @ManyToMany
    @JoinTable(
            name = "groups_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "groups_goals",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "goal_id")
    )
    private List<Goal> goals = new ArrayList<>();

    public Group(String title,
                 String organisation, Boolean isPublic,
                 Set<User> users, List<Goal> goals) {
        this.title = title;
        this.organisation = organisation;
        this.isPublic = isPublic;
        this.users = users;
        this.goals = goals;
    }

    public Group() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }
}