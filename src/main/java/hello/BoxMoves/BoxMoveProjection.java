package hello.BoxMoves;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.fasterxml.jackson.annotation.JsonProperty;

@Projection(name = "BoxMoveProjection", types = BoxMove.class)
public interface BoxMoveProjection {
	@JsonProperty("_id")
	int getId();
	
	@JsonProperty("_Id_b")
	@Value("#{target.box.id}")
	int getIdBox();
	
	@JsonProperty("_Id_o")
	@Value("#{target.operation.id}")
	int getIdOperation();
	
	
	@JsonProperty("DT")
	Date getDate();
}
