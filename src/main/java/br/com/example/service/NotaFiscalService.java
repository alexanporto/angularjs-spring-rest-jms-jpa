package br.com.example.service;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.example.controller.Controller;
import br.com.example.domain.model.NotaFiscal;
import br.com.example.domain.repository.NotaFiscalRepository;

@Service
public class NotaFiscalService {
	
	static Logger logger = Logger.getLogger(Controller.class.getName());
	static final String NOTA_FISCAL_DESTINATION_REQUEST = "nota-from-rest-to-service";
	

    ObjectMapper mapper = new ObjectMapper();
	
    @Autowired
    private NotaFiscalRepository notaFiscalRepository;
	
	@Autowired
    JmsTemplate jmsTemplate;    
  
	@Bean
    JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }	
    	
	////////////////// jms receiver para adição assíncrona de notas fiscais
    
	@JmsListener(destination = NOTA_FISCAL_DESTINATION_REQUEST, containerFactory = "myJmsContainerFactory")
    void receiveNotaFiscal(String message) {        
    	logger.info(String.format("Message da nota fiscal recebida %s: '%s'", NOTA_FISCAL_DESTINATION_REQUEST, message));
    	try {			
    		NotaFiscal nf = mapper.readValue(message, NotaFiscal.class);    	    
    		this.include(nf);
    		
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	            
    }
	    
	////////////////// service business
	
	public NotaFiscal include(NotaFiscal nf){		
		
		return notaFiscalRepository.save(nf);
	}
	
	public Collection<NotaFiscal> findAll(){		
		
		return notaFiscalRepository.findAll();
	}
	
	public NotaFiscal findById(int id){		

		return notaFiscalRepository.findOne(id);
	}
	
	public void remove(NotaFiscal nf){		
		
		notaFiscalRepository.delete(nf);
	}
	
	public NotaFiscal update(NotaFiscal nf){	        
		
		return notaFiscalRepository.save(nf);
	}
}
