package hexlet.code.app.repository;

import hexlet.code.app.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    boolean existsByAssignee_Id(Long id);
    boolean existsByTaskStatusId(Long id);

    Page<Task> findAll(Specification<Task> spec, Pageable pageable);
}
