package br.com.example.controller;

import java.util.Collection;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.example.domain.model.Mercadoria;
import br.com.example.domain.model.NotaFiscal;
import br.com.example.service.MercadoriaService;
import br.com.example.service.NotaFiscalService;


@EnableJms
@RestController
public class Controller {		
	
	static final String NOTA_FISCAL_DESTINATION_REQUEST = "nota-from-rest-to-service";
	static final String MERCADORIA_DESTINATION_REQUEST = "merc-from-rest-to-service";
	
	static Logger logger = Logger.getLogger(Controller.class.getName());
    
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private NotaFiscalService notaFiscalService;
	
	@Autowired
	private MercadoriaService mercadoriaService;
		
	@Autowired
    JmsTemplate jmsTemplate;


	
	//////////////////////////// Nota Fiscal	
	
	
	@RequestMapping(method=RequestMethod.POST, value="/notasFiscais", 
			consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> includeNotaFiscaEndPoint(@RequestBody NotaFiscal nf){

		try {			
			String jsonInString  = mapper.writeValueAsString(nf);		
			jmsTemplate.send(NOTA_FISCAL_DESTINATION_REQUEST, session ->  session.createTextMessage(jsonInString));		
			logger.info(String.format("Nota fiscal enviada para fila de inclusão %s: '%s'", NOTA_FISCAL_DESTINATION_REQUEST, jsonInString));
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		} 
		return new ResponseEntity<String>("Nota fiscal enviada para fila de inclusão", HttpStatus.CREATED);
	}	
	
/*
 *  Inclusão sem JMS
 * 
	@RequestMapping(method=RequestMethod.POST, value="/notasFiscais", 
			consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NotaFiscal> includeNotaFiscaEndPoint(@RequestBody NotaFiscal nf){
		
		NotaFiscal notaFiscalIncluida = this.notaFiscalService.include(nf); 
		System.out.println("Registro incluído!");
		return new ResponseEntity<NotaFiscal>(notaFiscalIncluida, HttpStatus.CREATED);
	}	
*/
	
	@RequestMapping(method=RequestMethod.GET, value="/notasFiscais", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<NotaFiscal>> findAllNotaFiscalEndPoint(){
		
		Collection<NotaFiscal> nfes = this.notaFiscalService.findAll();	
		System.out.println("Dados recuperados!");
		return new ResponseEntity<>(nfes, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/notasFiscais/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NotaFiscal> findByIdNotaFiscalEndPoint(@PathVariable int id){
		
		NotaFiscal nfe = this.notaFiscalService.findById(id);	
		System.out.println("Dados recuperados!");
		return new ResponseEntity<>(nfe, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/notasFiscais/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NotaFiscal> removeNotaFiscalEndPoint(@PathVariable int id){
		
		NotaFiscal nf = this.notaFiscalService.findById(id);		
		if(nf==null){
			System.out.println("Registro inexistente para excluir!");
			return new ResponseEntity<NotaFiscal>(nf, HttpStatus.NOT_FOUND);
		}
		this.notaFiscalService.remove(nf);	
		System.out.println("Registro removido!");
		return new ResponseEntity<NotaFiscal>(nf, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/notasFiscais", 
			consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NotaFiscal> updateNotaFiscaEndPoint(@RequestBody NotaFiscal nf){
		
		NotaFiscal _nf = this.notaFiscalService.findById(nf.getId());		
		if(_nf==null){
			System.out.println("Registro inexistente para alterar!");
			return new ResponseEntity<NotaFiscal>(_nf, HttpStatus.NOT_FOUND);
		}		
		NotaFiscal notaFiscalUpdated = this.notaFiscalService.update(nf); 
		System.out.println("Registro alterado!");
		return new ResponseEntity<NotaFiscal>(notaFiscalUpdated, HttpStatus.OK);
	}
		
	
	////////////////////// Mercadoria
	
	@RequestMapping(method=RequestMethod.POST, value="/mercadorias", 
			consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> includeMercadoriaEndPoint(@RequestBody Mercadoria m){
		try {			
			String jsonInString  = mapper.writeValueAsString(m);		
			jmsTemplate.send(MERCADORIA_DESTINATION_REQUEST, session ->  session.createTextMessage(jsonInString));		
			logger.info(String.format("Mercadoria enviada para fila de inclusão %s: '%s'", MERCADORIA_DESTINATION_REQUEST, jsonInString));
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		} 
		return new ResponseEntity<String>("Mercadoria enviada para fila de inclusão", HttpStatus.CREATED);
	}		
	
	/*
	@RequestMapping(method=RequestMethod.POST, value="/mercadorias", 
			consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mercadoria> includeMercadoriaEndPoint(@RequestBody Mercadoria m){
		
		Mercadoria mIncluded = this.mercadoriaService.include(m); 
		System.out.println("Registro incluído!");
		return new ResponseEntity<Mercadoria>(mIncluded, HttpStatus.CREATED);
	}	
	*/
	
	@RequestMapping(method=RequestMethod.GET, value="/mercadorias", 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Mercadoria>> findAllMercadoriaEndPoint(){
		
		Collection<Mercadoria> nfes = this.mercadoriaService.findAll();	
		System.out.println("Dados recuperados!");
		return new ResponseEntity<>(nfes, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/mercadorias/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mercadoria> findByIdMercadoriaEndPoint(@PathVariable int id){
		
		Mercadoria m = this.mercadoriaService.findById(id);	
		System.out.println("Dados recuperados!");
		return new ResponseEntity<>(m, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/mercadorias/{id}", 
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mercadoria> removeMercadoriaEndPoint(@PathVariable int id){
		
		Mercadoria _m = this.mercadoriaService.findById(id);		
		if(_m==null){
			System.out.println("Registro inexistente para excluir!");
			return new ResponseEntity<Mercadoria>(_m, HttpStatus.NOT_FOUND);
		}
		this.mercadoriaService.remove(_m);	
		System.out.println("Registro removido!");
		return new ResponseEntity<Mercadoria>(_m, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/mercadorias", 
			consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mercadoria> updateMercadoriaEndPoint(@RequestBody Mercadoria m){
		
		Mercadoria _m = this.mercadoriaService.findById(m.getId());		
		if(_m==null){
			System.out.println("Registro inexistente para alterar!");
			return new ResponseEntity<Mercadoria>(_m, HttpStatus.NOT_FOUND);
		}		
		Mercadoria mUpdated = this.mercadoriaService.update(m); 
		System.out.println("Registro alterado!");
		return new ResponseEntity<Mercadoria>(mUpdated, HttpStatus.OK);
	}	
}

