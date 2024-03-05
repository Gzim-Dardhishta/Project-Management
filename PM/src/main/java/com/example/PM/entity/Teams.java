package com.example.PM.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String groupName;
    private int groupGrade;
    private String teamAdmin;
    private String professor;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<User> members = new ArrayList<>();

    public Teams () {}

    public Teams(int id, String groupName, int groupGrade, String teamAdmin, String professor, List<User> members) {
        this.id = id;
        this.groupName = groupName;
        this.teamAdmin = teamAdmin;
        this.professor = professor;
        this.groupGrade = groupGrade;
        this.members = members;
    }

    public void removeMembersFromTeam() {
        this.members.clear();
    }

    public void addMember(User user) {
        this.members.add(user);
    }

    public void addUserToGroup(User user) {
        members.add(user);
    }

    public void removeAllUsersFromGroup() {
        this.members.clear();
    }

    public void removeOneUserFromGroup(int index) {
        this.members.remove(index);
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTeamAdmin() {
        return teamAdmin;
    }

    public void setTeamAdmin(String teamAdmin) {
        this.teamAdmin = teamAdmin;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public int getGroupGrade() {
        return groupGrade;
    }

    public void setGroupGrade(int groupGrade) {
        this.groupGrade = groupGrade;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teams team)) return false;
        return getId() == team.getId() && getGroupGrade() == team.getGroupGrade() && Objects.equals(getGroupName(), team.getGroupName()) && Objects.equals(getMembers(), team.getMembers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGroupName(), getGroupGrade(), getMembers());
    }

    @Override
    public String toString() {
        return "Teams{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", groupGrade=" + groupGrade +
                ", teamAdmin=" + teamAdmin +
                ", members=" + members +
                '}';
    }
}
