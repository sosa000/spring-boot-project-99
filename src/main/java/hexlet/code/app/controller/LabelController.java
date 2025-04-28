package hexlet.code.app.controller;

import hexlet.code.app.dto.label.LabelCreateDTO;
import hexlet.code.app.dto.label.LabelDTO;
import hexlet.code.app.dto.label.LabelUpdateDTO;
import hexlet.code.app.sevice.LabelService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class LabelController {
    @Autowired
    private LabelService labelService;

    @GetMapping(path = "/labels")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LabelDTO>> index() {
        var labels = labelService.findAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(labels.size()))
                .body(labels);
    }

    @GetMapping(path = "/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LabelDTO> show(@PathVariable Long id) {
        var label = labelService.findById(id);

        return ResponseEntity.ok()
                .body(label);
    }

    @PostMapping(path = "/labels")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LabelDTO> create(@Valid @RequestBody LabelCreateDTO dto) {
        var label = labelService.create(dto);

        return ResponseEntity.ok()
                .body(label);
    }

    @PutMapping(path = "/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LabelDTO> update(@Valid @RequestBody LabelUpdateDTO dto, @PathVariable Long id) {
        var label = labelService.update(dto, id);

        return ResponseEntity.ok()
                .body(label);
    }

    @DeleteMapping(path = "/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) throws BadRequestException {
        labelService.delete(id);
    }
}
