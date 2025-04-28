package hexlet.code.app.repository;

import hexlet.code.app.model.TaskLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
    boolean existsByLabelId(Long id);
}
