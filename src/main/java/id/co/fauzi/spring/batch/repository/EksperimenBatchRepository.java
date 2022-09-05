package id.co.fauzi.spring.batch.repository;

import id.co.fauzi.spring.batch.entity.EksperimenBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface EksperimenBatchRepository extends JpaRepository<EksperimenBatch, Long> {
}
