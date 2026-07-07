package org.generation.caliope.controller;
import org.generation.caliope.dto.ContactsRequest;
import org.generation.caliope.model.Contacts;
import lombok.AllArgsConstructor;
import org.generation.caliope.service.ContactsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/contacts") //// http://localhost:8080/api/contacts
@AllArgsConstructor
public class ContactsController {
    private final ContactsService contactsService;

    @PostMapping
    private Contacts addContact(@RequestBody ContactsRequest contactsRequest){
        return contactsService.createContact(contactsRequest);
    }

    @GetMapping
    public List<Contacts> getAllContacts() {return contactsService.getAllContacts();}

    @GetMapping(path = "{contactId}")
    public Contacts getContactById(@PathVariable("contactId") Long id){
        return contactsService.getContactById(id);
    }
    @DeleteMapping(path = "/{contactId}")
    public Contacts deleteContact(@PathVariable("contactId") Long id) {
        return contactsService.deleteContact(id);
    }
}