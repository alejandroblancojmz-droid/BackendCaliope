package org.generation.caliope.service;
import aj.org.objectweb.asm.ClassReader;
import  org.generation.caliope.dto.ContactsRequest;
import org.generation.caliope.model.Contacts;
import  lombok.AllArgsConstructor;
import org.generation.caliope.repository.ContactsRepository;
import  org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import  java.util.List;


@Service
@AllArgsConstructor
public class ContactsService {
    private final ContactsRepository contactsRepository;

    public Contacts createContact(ContactsRequest request){

        Contacts contact = new Contacts();

        contact.setName(request.name());
        contact.setSubject(request.subject());
        contact.setMessage(request.message());
        contact.setEmail(request.email());
        contact.setSend(LocalDateTime.now());

        return contactsRepository.save(contact);
    }

    public List<Contacts> getAllContacts(){
        return contactsRepository.findAll();
    }

    public Contacts getContactById(Long id){
        return contactsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No se encontró la solicitud")
        );
    }

    public Contacts deleteContact(Long id){
        Contacts contact =  contactsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("No se encontró la solicitud")
        );
        contactsRepository.delete(contact);
        return contact;
    }
}
