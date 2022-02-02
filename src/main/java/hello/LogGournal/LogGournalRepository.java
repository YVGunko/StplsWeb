package hello.LogGournal;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogGournalRepository extends JpaRepository<LogGournal,Long>{

	List<LogGournal> findByDtBetweenOrderByDt(Date atStartOfDay, Date atEndOfDay);


}
