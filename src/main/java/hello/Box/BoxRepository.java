package hello.Box;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.OutDoc.OutDoc;


@Repository
public interface BoxRepository extends JpaRepository<Box,String>{

	List<Box> findByIdNotIn(ArrayList<String> boxesId);
	List<Box> findByMasterDataId(long id);
	List<Box> findByMasterDataIdIn(List<Long> id);
	Box findByMasterDataIdAndNumBox(long id, int NumBox) ;
	Optional<Box> getByMasterDataIdAndNumBox(long id, int NumBox) ;
	List<Box> findByArchive(Boolean archive);
	List<Box> findByDateGreaterThan(Date date);
	List<Box> findBySentToMasterDateIsNotNull ();
	List<Box> findByReceivedFromMobileDateGreaterThan(Date date);
	Page<Box> findByReceivedFromMobileDateGreaterThan(Date date, Pageable pageable);
	List<Box> findByIdIn(List<String> boxesId);
	List<Box> findBySentToMasterDateIsNotNullOrderByDate();
	List<Box> findByArchiveOrderByDate(boolean b);

}
