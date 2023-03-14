package com.ram.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="libros")
public class Libro {

	//Propiedades
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer referencia; 
	
	@NotEmpty
	@Column(name = "Titulo")
	private String titulo; 
	
	@NotEmpty
	@Column(name = "Autor")
	private String autor; 
	
	@Column(name = "Sinopsis")
	private String sinopsis;
	
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String fotoPortada; 
	
	@NotNull
	@Column(name = "Fecha de adquisicion")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaAdquisicion;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Socio socio;

	//Constructores
	
	public Libro() {
		super();
	}
	
	public Libro(Integer referencia, @NotEmpty String titulo, @NotEmpty String autor, String sinopsis,
			String fotoPortada, @NotEmpty Date fechaAdquisicion) {
		super();
		this.referencia = referencia;
		this.titulo = titulo;
		this.autor = autor;
		this.sinopsis = sinopsis;
		this.fotoPortada = fotoPortada;
		this.fechaAdquisicion = fechaAdquisicion;
	}

	//Getters y Setters
	
	public Integer getReferencia() {
		return referencia;
	}

	public void setReferencia(Integer referencia) {
		this.referencia = referencia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public String getFotoPortada() {
		return fotoPortada;
	}
	
	public void setFotoPortada(String fotoPortada)
	{
		this.fotoPortada = fotoPortada;
	}

	public void setFotoPortada(MultipartFile file) throws IOException
	{
		this.fotoPortada = Base64.getEncoder().encodeToString(file.getBytes());		
	}	
	
	public void setFotoPortada(File file) throws IOException
	{
		FileInputStream fl = new FileInputStream(file);

		byte[] arr = new byte[(int) file.length()];

		fl.read(arr);

		fl.close();
		this.fotoPortada = Base64.getEncoder().encodeToString(arr);
	}
	public Date getFechaAdquisicion() {
		return fechaAdquisicion;
	}

	public void setFechaAdquisicion(Date fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

}
