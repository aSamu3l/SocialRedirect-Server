package it.samu3l.redirect.repository;

import it.samu3l.redirect.model.Alias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AliasRepository extends JpaRepository<Alias, Long> {
    Optional<Alias> findByAlias(String alias);
}