package com.example.PM.controllers;

import com.example.PM.entity.Messages;
import com.example.PM.payload.request.NewMessageRecord;
import com.example.PM.payload.response.GroupMessagesResponse;
import com.example.PM.payload.response.MessageResponse;
import com.example.PM.services.MessagesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {

    private final MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @GetMapping("/team/{teamId}")
    public GroupMessagesResponse getAllMessagesFromTeam(@PathVariable int teamId) {
        return messagesService.getAllMessagesFromTeam(teamId);
    }

    @GetMapping("/message/{messageId}")
    public Messages getMessage(@PathVariable int messageId) {
        return messagesService.getMessage(messageId);
    }

    @PostMapping("team/{teamId}/add-message")
    public ResponseEntity<?> postMessage(
            @RequestBody NewMessageRecord newMessage,
            @PathVariable int teamId
    ) {
        messagesService.sendMessage(newMessage, teamId);

        return new ResponseEntity<>(
                new MessageResponse("Message posted successfully"), HttpStatus.CREATED
        );
    }

    @PutMapping("/message/{messageId}")
    public ResponseEntity<?> editMessage(@RequestBody NewMessageRecord editMessage, @PathVariable int messageId) {
        messagesService.editMessage(editMessage, messageId);

        return new ResponseEntity<>(new MessageResponse("Message edited!!"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<?> deleteById(@PathVariable int messageId) {
        messagesService.deleteMessage(messageId);

        return ResponseEntity.ok(new MessageResponse("Message deleted!!"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAllMessages() {
        messagesService.deleteAllMessages();

        return ResponseEntity.ok(new MessageResponse("All Messages deleted!!"));
    }
}
