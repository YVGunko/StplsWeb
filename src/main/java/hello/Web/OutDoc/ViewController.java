package hello.Web.OutDoc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hello.utils;
import hello.OutDoc.OutDoc;
import hello.OutDoc.OutDocRepository;
import hello.PartBox.PartBox;
import hello.PartBox.PartBoxRepository;
import hello.BoxMoves.BoxMovesRepository;
 
@Controller
@RequestMapping("/viewClean")
public class ViewController {

@Autowired
OutDocRepository outdocRepository;
@Autowired
PartBoxRepository partBoxRepository;
@Autowired
BoxMovesRepository boxMovesRepository;

Command command = new Command();

@GetMapping
public ModelAndView get() throws ParseException {

	return foobarPost(command);

}

@PostMapping(params="action=filter")
public ModelAndView foobarPost(

    @ModelAttribute("command") Command command ) throws ParseException {
		this.command = command;
		SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		ModelAndView mv = new ModelAndView();
		
			if (command.getDivision() == null || command.getOperation() == null){
				List<OutDocWeb> outDocWeb = new ArrayList<>();
				List<OutDoc> outDoc = new ArrayList<>();
				outDoc = outdocRepository.findByDivisionCodeAndOperationIdAndDateBetweenOrderByDate("00-000002", 2,
						utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
						utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())));
				if (outDoc!=null) {
					for (OutDoc b : outDoc) {
						int quantitySum = 0;
						long boxCount = boxMovesRepository.findCountBoxByPartBoxOutDocId(b);
						List<PartBox> partBox = partBoxRepository.findByOutDocId(b.getId());
						if (partBox!=null) {
							for (PartBox pb : partBox) {
								quantitySum = quantitySum+pb.getQuantity();
							}
						}
						outDocWeb.add(new OutDocWeb(b.getDate(), mdyFormat.format(b.getDate()),b.getComment(),(int)(boxCount),quantitySum));
					}
				}
				mv.addObject("outdocs", outDocWeb);
			} else {
				mv.addObject("outdocs", outdocRepository.findByDivisionCodeAndOperationIdAndDateBetweenOrderByDate(command.getDivision().getCode(), 
				command.getOperation().getId(),
				utils.atStartOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateS())),
				utils.atEndOfDay(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDateE())))
			);
			}
		mv.addObject("command", command);
		mv.setViewName("webClean");
		
		return mv;

	}
}

 

/*@PostMapping(params="action=print")
public Object pdfGet(HttpServletRequest request,
		
		@ModelAttribute("command") Command command,

        HttpServletResponse response) throws IOException, ParseException {
	List<Sticker> stickers = outdocRepository.findByNumberOfOrderAndDateAfter(command.getIdDoc(),command.getIdSticker(), Config.getStartOfDayLong(new SimpleDateFormat("yyyy-MM-dd").parse(command.getDate())));
	
	if (stickers.isEmpty()) {
		return foobarPost(command);
	}
	else {
		ByteArrayInputStream bis = GeneratePdfReport.stickerReport(stickers);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=stickers.pdf");

		return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
	}
}*/

