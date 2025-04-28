package hexlet.code.app.sevice;

import hexlet.code.app.dto.label.LabelCreateDTO;
import hexlet.code.app.dto.label.LabelDTO;
import hexlet.code.app.dto.label.LabelUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.label.LabelMapper;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.repository.TaskLabelRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskLabelRepository taskLabelRepository;

    public List<LabelDTO> findAll() {
        return labelRepository.findAll()
                .stream()
                .map(labelMapper::map)
                .toList();
    }

    public LabelDTO findById(Long id) {
        var model = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id " + id + " not found"));

        return labelMapper.map(model);
    }

    public LabelDTO create(LabelCreateDTO dto) {
        var model = labelMapper.map(dto);
        labelRepository.save(model);

        return labelMapper.map(model);
    }

    public LabelDTO update(LabelUpdateDTO dto, Long id) {
        var model = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id " + id + " not found"));

        labelMapper.update(dto, model);
        labelRepository.save(model);

        return labelMapper.map(model);
    }

    public void delete(Long id) throws BadRequestException {
        if (taskLabelRepository.existsByLabelId(id)) {
            throw new BadRequestException("You can not delete label with task");
        }
        labelRepository.deleteById(id);
    }
}
