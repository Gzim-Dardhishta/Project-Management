package com.example.PM.services;

import com.example.PM.entity.DTO.UserDTO;
import com.example.PM.entity.ERole;
import com.example.PM.entity.Messages;
import com.example.PM.entity.Teams;
import com.example.PM.entity.User;
import com.example.PM.exeptions.ResourceNotFoundException;
import com.example.PM.payload.request.NewMessageRecord;
import com.example.PM.payload.response.GroupMessagesResponse;
import com.example.PM.repository.MessagesRepository;
import com.example.PM.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final TeamsService teamsService;
    private final UserService userService;
    private final UserRepository userRepository;

    public MessagesService(MessagesRepository messagesRepository, TeamsService teamsService, UserService userService, UserRepository userRepository) {
        this.messagesRepository = messagesRepository;
        this.teamsService = teamsService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public GroupMessagesResponse getAllMessagesFromTeam(int teamId) {
        Teams team = teamsService.getTeam(teamId);

        List<Messages> messages = messagesRepository.getMessagesByTeamId(teamId);

        return new GroupMessagesResponse(
            team.getId(), team.getGroupName(), team.getTeamAdmin(), team.getProfessor(), messages
        );
    }

    public Messages getMessage(int messageId) {
        return messagesRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message with id %s not found".formatted(messageId)));
    }

    public void sendMessage(NewMessageRecord newMessage, int teamId) {

        UserDetails loggedUser = userService.getLoggedInUser().get();
        User user = userRepository.findByUsername(loggedUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("No user found"));

        Teams team = teamsService.getTeam(teamId);
        Messages message = new Messages();

        message.setMessages(newMessage.message());
        message.setAuthor(user.getName() + " " + user.getLastName() + " - " + user.getUsername());
        message.setTime(LocalDateTime.now());
        message.setTeam(team);

        messagesRepository.save(message);
    }

    public void editMessage(NewMessageRecord editMessage, int messageId) {
        Messages message = getMessage(messageId);

        message.setMessages(editMessage.message());

        messagesRepository.save(message);
    }

    public void deleteMessage(int messageId) {
        messagesRepository.deleteById(messageId);
    }

    public void deleteAllMessages() {
        messagesRepository.deleteAll();
    }
}
