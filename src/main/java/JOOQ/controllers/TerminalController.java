package JOOQ.controllers;


import JOOQ.response.SuccessResponse;
import JOOQ.service.TerminalService;
import JOOQ.service.TerminalServiceImpl;
import lombok.RequiredArgsConstructor;
import no.ntnu.bachelor_group3.dataaccessevaluation.jooq.model.tables.pojos.Terminal;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController(value = "TerminalControllerDao")
@RequestMapping("/api/terminal")
public class TerminalController {

    private final TerminalService terminalService;

    @PostMapping()
    public ResponseEntity<SuccessResponse> create(@RequestBody @Valid Terminal terminal) throws Exception {
        if (!ObjectUtils.isEmpty(terminal.getTerminalId())) {
            throw new Exception("New data can't already have an ID");
        }

        return new ResponseEntity<>(
                new SuccessResponse(terminalService.create(terminal), "Successfully created a new Terminal"),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAll() {
        List<Terminal> terminals = terminalService.getAll();

        return new ResponseEntity<>(new SuccessResponse(terminals, MessageFormat.format("{0} Results found", terminals.size())), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getOne(@PathVariable("id") int id) {
        Terminal terminal = TerminalService.getOne(id);
        return new ResponseEntity<>(
                new SuccessResponse(terminal, "Result found"), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable("id") int id) {
        terminalService.deleteById(id);
        return new ResponseEntity<>(
                new SuccessResponse(null, "Deletion completed successfully"), HttpStatus.OK);
    }

}
