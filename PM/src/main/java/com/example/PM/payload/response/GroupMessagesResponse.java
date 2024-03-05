package com.example.PM.payload.response;

import com.example.PM.entity.Messages;

import java.util.List;
import java.util.Objects;

public class GroupMessagesResponse {
    private int groupId;
    private String groupName;
    private String teamAdmin;
    private String professor;
    private List<Messages> messagesList;

    public GroupMessagesResponse() {
    }

    public GroupMessagesResponse(int groupId, String groupName, String teamAdmin, String professor, List<Messages> messagesList) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.teamAdmin = teamAdmin;
        this.professor = professor;
        this.messagesList = messagesList;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }
}
