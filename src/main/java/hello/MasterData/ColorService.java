package hello.MasterData;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
	@Autowired
	private ColorRepository repository;

	
	@Transactional
    public Color saveOrUpdate(Color color) throws Exception {
		
		if ( (color.getDivision().getCode().equals("00-000025")) || (color.getDivision().getCode().equals("00-000002")) ){
			Optional<Color> tmp = repository.findById(color.getId());
			
			if (tmp.isPresent()) { // по ид есть
				if (tmp.equals(color)) // тот же
					return tmp.get();
				else {
					if ((tmp.get().getOccurrence()== null || "".equals(tmp.get().getOccurrence())) & 
							(color.getOccurrence()== null || "".equals(color.getOccurrence()))) {
						//Запись есть, но создавалась ранее без occurrence а пришла с occurrence.
					} else {
						if (tmp.get().getDivision().getCode().equals(color.getDivision().getCode())) return tmp.get(); // имя не совпадает, изменили в 1с.
						if (tmp.get().getName().equals(color.getName())) return tmp.get();
					}
				}
			}else {
				
				//точку на конце проверить и не дублировать запись
				//System.out.println("color.getName().lastIndexOf(\".\") "+color.getName().lastIndexOf("."));
				//System.out.println("color.getName().length() "+color.getName().length());
				if (color.getName().lastIndexOf(".")==color.getName().length()-1)
					tmp = repository.findOneByNameAndDivisionCode(color.getName().substring(0, color.getName().length()-1), color.getDivision().getCode());
				if (color.getName().lastIndexOf(".")!=color.getName().length()-1)
					tmp = repository.findOneByNameAndDivisionCode(color.getName().concat("."), color.getDivision().getCode());
				if (tmp.isPresent()) return tmp.get();
				else {
					tmp = repository.findOneByNameAndDivisionCode(color.getName(), color.getDivision().getCode());
					if (tmp.isPresent()) return tmp.get();
				}
			}
	
			return repository.save(color);
	    } else return color;
	}
}
