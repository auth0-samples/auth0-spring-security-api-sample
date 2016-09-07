package com.auth0.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Secured Service
 */
@Service
public class ProfileService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected ProfileRepositoryStub profileRepository;

    private Auth0Client auth0Client;

    @Autowired
    public ProfileService(final Auth0Client auth0Client, final ProfileRepositoryStub profileRepository) {
        this.auth0Client = auth0Client;
        this.profileRepository = profileRepository;
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public List<Profile> list() {
        return profileRepository.list();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Profile create(Profile profile) {
        return profileRepository.create(profile);
    }

    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public Profile get(Long id) {
        return profileRepository.get(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Profile update(Long id, Profile profile) {
        return profileRepository.update(id, profile);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Profile delete(Long id) {
        return profileRepository.delete(id);
    }

}


