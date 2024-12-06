package it.samu3l.redirect.service;

import it.samu3l.redirect.model.Alias;
import it.samu3l.redirect.repository.AliasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedirectService {

    @Autowired
    private AliasRepository aliasRepository;

    public Optional<Alias> getAlias(String subdomain) {
        return aliasRepository.findByAlias(subdomain);
    }
}