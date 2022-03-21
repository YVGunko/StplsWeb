package hello.MasterData;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RepositoryRestController
public class MasterDataController {

	@Autowired
    MasterDataService service;
	@Autowired
	MasterDataRepository masterDataRepository;

    @PostMapping(path="masterDatas")
    public @ResponseBody MasterData saveOrUpdate(@RequestBody EntityModel<MasterData> masterData) throws Exception {
    		return service.saveOrUpdate(masterData.getContent());
    }
	/* spring-boot 2.0.4
	 *    @PostMapping(path="masterDatas")
    public @ResponseBody MasterData saveOrUpdate(@RequestBody Resource<MasterData> masterData) throws Exception {
    		return service.saveOrUpdate(masterData.getContent());
    }*/
    @PostMapping(path="masterDatas/Delete")
    public @ResponseBody void deleteByid1C(@RequestBody ArrayList<String> id1C) throws Exception {
    		service.delete(id1C);
    }
    @PostMapping(path="masterDatas/Update")
    public @ResponseBody void updateByid1C(@RequestBody ArrayList<String> id1C) throws Exception {
    }
    @PostMapping(path="masterDatas/setArchive")
    public @ResponseBody void setArchiveByid1C(@RequestBody ArrayList<String> id1C, @RequestParam(value="state", required=true) boolean state) throws Exception {
    		service.setArchiveByid1c(id1C, state);
    }


}