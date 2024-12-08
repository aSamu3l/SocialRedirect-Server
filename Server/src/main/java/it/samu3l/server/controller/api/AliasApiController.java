package it.samu3l.server.controller.api;

import it.samu3l.server.model.Alias;
import it.samu3l.server.service.AliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/admin/api/aliases")
public class AliasApiController {
    @Autowired
    private AliasService aliasService;

    public AliasApiController() {
    }

    @GetMapping("")
    public Iterable<Alias> getAll() {
        return aliasService.getAllAliases();
    }

    @GetMapping("/{id}")
    public Alias getById(@PathVariable Long id) {
        Optional<Alias> alias = aliasService.getAliasById(id);

        if (alias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Alias");
        }

        return alias.get();
    }

    @GetMapping("/alias/{alias}")
    public Alias getByAlias(@PathVariable String alias) {
        Optional<Alias> aliasOptional = aliasService.getAlias(alias);

        if (aliasOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Alias");
        }

        return aliasOptional.get();
    }

    @PostMapping(value = "")
    public Alias addAlias(@RequestBody Alias a) {
        return aliasService.saveAlias(a);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAlias(@PathVariable Long id) {
        aliasService.deleteAlias(id);
    }
}
