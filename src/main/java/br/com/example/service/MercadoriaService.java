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
import br.com.example.domain.model.Mercadoria;
import br.com.example.domain.repository.MercadoriaRepository;

@Service
public class MercadoriaService {
	
	static Logger logger = Logger.getLogger(Controller.class.getName());
	static final String MERCADORIA_DESTINATION_REQUEST = "merc-from-rest-to-service";
	

    ObjectMapper mapper = new ObjectMapper();
	
    @Autowired
    private MercadoriaRepository mercadoriaRepository;
	
	@Autowired
    JmsTemplate jmsTemplate;    
  
	@Bean
    JmsListenerContainerFactory<?> jmsContainerFactory(ConnectionFactory connectionFactory) {
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }	
    
	////////////////// jms receiver para adição assíncrona de mercadorias
    
	@JmsListener(destination = MERCADORIA_DESTINATION_REQUEST, containerFactory = "jmsContainerFactory")
    void receiveMercadoria(String message) {        
    	logger.info(String.format("Message da mercadoria recebida %s: '%s'", MERCADORIA_DESTINATION_REQUEST, message));
    	try {			
    		Mercadoria m = mapper.readValue(message, Mercadoria.class);    	    
    		this.include(m);
    		
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	            
    }  
	
	//////////////////service business
	
	public Mercadoria include(Mercadoria m){		

		return mercadoriaRepository.save(m);
	}
	
	public Collection<Mercadoria> findAll(){		
		
		return mercadoriaRepository.findAll();
	}
	
	public Mercadoria findById(int id){		
		
		return mercadoriaRepository.findOne(id);
	}
	
	public void remove(Mercadoria m){		
		
		mercadoriaRepository.delete(m);
	}
	
	public Mercadoria update(Mercadoria m){		
		
		return mercadoriaRepository.save(m);
	}
}
