package com.thungcam.chacalang.service;

import com.thungcam.chacalang.entity.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getAllContacts();

    void save(Contact contact);

    List<Contact> getContactsByBranch(Long branchId);
}
