package project.mediavault.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import project.mediavault.model.TVShow;

@Repository
public interface TVShowRepository extends JpaRepository<TVShow, Integer>, JpaSpecificationExecutor<TVShow> {
    //
}
