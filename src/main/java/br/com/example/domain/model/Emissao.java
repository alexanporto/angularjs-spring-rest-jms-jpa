package br.com.example.domain.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "emissao")
public class Emissao implements Serializable{

	private static final long serialVersionUID = -2344348241231970964L;
	private NotaFiscal notaFiscal;
    private Mercadoria mercadoria;
    private String emissor;

    @Id
    @ManyToOne
    @JoinColumn(name = "notafiscal_id")
    public NotaFiscal getNotaFiscal() {
		return notaFiscal;
	}
	
	public void setNotaFiscal(NotaFiscal notaFiscal) {
		this.notaFiscal = notaFiscal;
	}

    @Id
    @ManyToOne
    @JoinColumn(name = "mercadoria_id")
	public Mercadoria getMercadoria() {
		return mercadoria;
	}
	
	public void setMercadoria(Mercadoria mercadoria) {
		this.mercadoria = mercadoria;
	}
	
	public String getEmissor() {
		return emissor;
	}
	
	public void setEmissor(String emissor) {
		this.emissor = emissor;
	}
}
