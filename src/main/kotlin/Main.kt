package org.pebiblioteca

import java.security.KeyStore.TrustedCertificateEntry

open class Libro(
    var id: String,
    val titulo: String,
    val autor: String,
    val año: Int,
    val tematica: String,
    var estado: String = "disponible"
) {

    init {
        require(id.isNotBlank()) { "El id no puede estar vacío" }
        require(titulo.isNotBlank()) { "El nombre no puede estar vacío" }
        require(autor.isNotBlank()) { "El autor no puede estar vacío" }
        require(año.equals("")) { "El año no puede estar vacío" }
        require(tematica.isNotBlank()) { "La temática no puede estar vacío" }
        require(estado.isNotBlank()) { "El estado no puede estar vacío" }
    }
}


open class GestorBiblioteca(var catalogoLibros: MutableList<Libro>) {

    fun agregarLibro(libro: Libro) {
        catalogoLibros.add(libro)
    }

    fun eliminarLibro(tituloLibro: String) {
        catalogoLibros.removeAll { it.titulo == tituloLibro }
    }

    fun registrarPrestamo(libro: Libro) {
        if(libro.estado=="disponible"){
            libro.estado="prestado"
        }else{
            println("Error")
        }
    }

    fun devolverLibro(libro: Libro) {
        if(libro.estado=="prestado"){
            libro.estado="disponible"
        }else{
            println("Error")
        }
    }

    fun consultarDisponibilidad(tituloLibro: String){
        val libro=catalogoLibros.find { it.titulo==tituloLibro }
        if (libro != null) {
            println(libro.estado)
        }
    }

    fun mostrarLibrosCriterio(){

    }

}

fun main() {
    var libro1=Libro("001","El Señor de los Anillos","JRR Tolkien", 1954, "Fantasía", "disponible")
    var libro2=Libro("002", "El Resplandor", "Stephen King", 1977, "Terror", "disponible")
    var libro3=Libro("003", "Cosmos", "Carl Sagan", 1980, "Ciencia", "Disponible")
    var libro4=Libro("004", "Lengua Castellana y Literatura", "Natalia Bernabeu", 1994, "Educación", "Disponible")

    var listaLibros= mutableListOf(libro1)
    var gestion=GestorBiblioteca(listaLibros)

    gestion.agregarLibro(libro2)
    gestion.agregarLibro(libro3)
    gestion.agregarLibro(libro4)

    gestion.registrarPrestamo(libro3)
    gestion.registrarPrestamo(libro3)
    gestion.devolverLibro(libro1)
    gestion.devolverLibro(libro3)



}