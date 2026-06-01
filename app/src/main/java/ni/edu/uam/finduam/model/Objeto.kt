package ni.edu.uam.finduam.model



// Esta clase representa las categorías definidas por el sistema.
// Se usa como clase porque el usuario no escribe la categoría libremente,
// sino que la selecciona de una lista.
data class Categoria(
    // Identificador único de la categoría.
    val idCategoria: Int,

    // Nombre de la categoría.
    val nombre: String
)