package it.samu3l.server.service;

import it.samu3l.server.model.Alias;
import it.samu3l.server.repository.AliasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AliasService {

    @Autowired
    private AliasRepository aliasRepository;

    public Optional<Alias> getAlias(String subdomain) {
        return aliasRepository.findByAlias(subdomain);
    }

    public Alias saveAlias(Alias alias) {
        return aliasRepository.save(alias);
    }

    public void deleteAlias(Long id) {
        aliasRepository.deleteById(id);
    }

    public Optional<Alias> getAliasById(Long id) {
        return aliasRepository.findById(id);
    }

    public Iterable<Alias> getAllAliases() {
        return aliasRepository.findAllByOrderByIdDesc();
    }
}