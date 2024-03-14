package org.pebiblioteca

import sun.util.locale.LocaleUtils.isEmpty
import java.security.KeyStore.TrustedCertificateEntry
import java.time.LocalDateTime

interface Prestable{
    fun prestar()
    fun devolver()
}

interface IGestorPrestamos{
    fun agregarLibro(libro:Libro)
    fun eliminarLibro(tituloLibro: String)
    fun registrarPrestamo(libro: Libro)
    fun devolverLibro(libro: Libro)
    fun consultarDisponibilidad(tituloLibro: String)


}

class GestorElementos<T>{
    var elementos: MutableList<T> = mutableListOf()

    fun agregarElemento(elemento: T){
        elementos.add(elemento)
    }

    fun eliminarElemento(): T? {
        if (elementos.isNotEmpty()) {
            return elementos.removeAt(elementos.size - 1)
        } else {
            return null
        }
    }


}

open class Catalogo(id: String, titulo: String, estado: String = "disponible"){

}

abstract class ElementoBiblioteca(id: String, titulo: String, estado: String = "disponible")

open class Libro(
    var id: String,
    val titulo: String,
    private val autor: String,
    val año: Int,
    private val tematica: String,
    var estado: String = "disponible"
) : ElementoBiblioteca(id, titulo, estado) , Prestable{

    init {
        require(id.isNotBlank()) { "El id no puede estar vacío" }
        require(titulo.isNotBlank()) { "El nombre no puede estar vacío" }
        require(autor.isNotBlank()) { "El autor no puede estar vacío" }
        require(año.equals("")) { "El año no puede estar vacío" }
        require(tematica.isNotBlank()) { "La temática no puede estar vacío" }
        require(estado.isNotBlank()) { "El estado no puede estar vacío" }
    }

    override fun prestar() {
        estado="prestado"
    }

    override fun devolver() {
        estado="disponible"
    }
}

open class DVD(
    var id: String,
    val titulo: String,
    private val autor: String,
    val año: Int,
    var estado: String = "disponible"
) : ElementoBiblioteca(id, titulo, estado) {

    init {
        require(id.isNotBlank()) { "El id no puede estar vacío" }
        require(titulo.isNotBlank()) { "El nombre no puede estar vacío" }
        require(autor.isNotBlank()) { "El autor no puede estar vacío" }
        require(año.equals("")) { "El año no puede estar vacío" }
        require(estado.isNotBlank()) { "El estado no puede estar vacío" }
    }
}

open class Revista(
    var id: String,
    val titulo: String,
    private val autor: String,
    val año: Int,
    private val tematica: String,
    var estado: String = "disponible"
) : ElementoBiblioteca(id, titulo, estado) {

    init {
        require(id.isNotBlank()) { "El id no puede estar vacío" }
        require(titulo.isNotBlank()) { "El nombre no puede estar vacío" }
        require(autor.isNotBlank()) { "El autor no puede estar vacío" }
        require(año.equals("")) { "El año no puede estar vacío" }
        require(tematica.isNotBlank()) { "La temática no puede estar vacío" }
        require(estado.isNotBlank()) { "El estado no puede estar vacío" }
    }
}


open class GestorBiblioteca(var catalogoLibros: MutableList<Libro>): IGestorPrestamos{

    override fun agregarLibro(libro: Libro) {
        UtilidadesBiblioteca.generarIdentificadorUnico(libro)
        catalogoLibros.add(libro)
    }

    override fun eliminarLibro(tituloLibro: String) {
        catalogoLibros.removeAll { it.titulo == tituloLibro }
    }

    override fun registrarPrestamo(libro: Libro) {
        if (libro.estado == "disponible") {
            libro.estado = "prestado"
        } else {
            println("Error")
        }
    }

    override fun devolverLibro(libro: Libro) {
        if (libro.estado == "prestado") {
            libro.estado = "disponible"
        } else {
            println("Error")
        }
    }

    override fun consultarDisponibilidad(tituloLibro: String) {
        val libro = catalogoLibros.find { it.titulo == tituloLibro }
        if (libro != null) {
            println(libro.estado)
        }
    }

    fun mostrarLibrosCriterio() {

    }


}

open class UtilidadesBiblioteca() {
    companion object {
        open fun generarIdentificadorUnico(libro: Libro) {
            val fecha = LocalDateTime.now()
            val substring1 = fecha.toString().substring(0, 3)
            val subString2 = fecha.toString().substring(14, 18)
            val codigo = substring1 + subString2

            libro.id = codigo
        }
    }
}

open class Usuario(val id: String, val nombre: String, var listaPrestados: MutableList<Libro>) {

    fun agregarLibro(libro: Libro) {
        listaPrestados.add(libro)
    }

    fun eliminarLibro(tituloLibro: String) {
        listaPrestados.removeAll { it.titulo == tituloLibro }
    }

    fun consultarLibros() {
        for (i in listaPrestados) {
            println(i)
        }
    }

}

open class RegistroPrestamos(val registro: MutableList<Libro>, var historial: MutableList<Libro>) {

    fun registrarPrestamo(nombre: Usuario, libro: Libro) {
        if (libro.estado == "disponible") {
            nombre.listaPrestados.add(libro)
        } else {
            println("Libro no disponible")
        }

    }

    fun devolverLibro(nombre: Usuario, libro: Libro) {
        if (libro.estado == "prestado") {
            nombre.listaPrestados.removeAll { it.titulo == libro.toString() }
        } else {
            println("Libro no disponible")
        }
    }


}


fun main() {
    var libro1 = Libro("001", "El Señor de los Anillos", "JRR Tolkien", 1954, "Fantasía", "disponible")
    var libro2 = Libro("002", "El Resplandor", "Stephen King", 1977, "Terror", "disponible")
    var libro3 = Libro("003", "Cosmos", "Carl Sagan", 1980, "Ciencia", "Disponible")
    var libro4 = Libro("004", "Lengua Castellana y Literatura", "Natalia Bernabeu", 1994, "Educación", "Disponible")

    var listaLibros = mutableListOf(libro1)
    var gestion = GestorBiblioteca(listaLibros)

    gestion.agregarLibro(libro2)
    gestion.agregarLibro(libro3)
    gestion.agregarLibro(libro4)

    gestion.registrarPrestamo(libro3)
    gestion.registrarPrestamo(libro3)
    gestion.devolverLibro(libro1)
    gestion.devolverLibro(libro3)


}