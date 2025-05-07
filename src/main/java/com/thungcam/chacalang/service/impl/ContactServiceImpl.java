package com.thungcam.chacalang.service.impl;

import com.thungcam.chacalang.entity.Contact;
import com.thungcam.chacalang.repository.ContactRepository;
import com.thungcam.chacalang.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    final private ContactRepository contactRepository;


    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public void save(Contact contact) {
        contact.setCreatedAt(LocalDateTime.now());
        contactRepository.save(contact);
    }
}
