interface Reservable {
    fun reserver()
    fun annulerReservation()
}

enum class TypeChambre{
    SIMPLE,
    DOUBLE,
    SUITE
}

class Chambre(
    val numero: Int,
    val type: TypeChambre, // "simple", "double", "suite"
    val prixParNuit: Double
) : Reservable {

    var estReservee: Boolean = false

    fun calculerPrix(sejour: Int): Double {
        return prixParNuit * sejour
    }

    override fun reserver() {
        if (estReservee) {
            throw Exception("Chambre déjà réservée.")
        }
        estReservee = true
    }

    override fun annulerReservation() {
        if (!estReservee) {
            throw Exception("Aucune réservation à annuler.")
        }
        estReservee = false
    }

    override fun toString(): String {
        return "Chambre(numero=$numero, type='${type.toString()}', prixParNuit=$prixParNuit, estReservee=$estReservee)"
    }
}

data class Client(
    val id: Int,
    val nom: String,
    val prenom: String
)

class Reservation(
    val client: Client,
    val chambre: Chambre,
    val duree: Int
) {
    override fun toString(): String {
        return "Reservation(client=$client, chambre=${chambre.numero}, duree=$duree)"
    }
}
class GestionReservation {
    private val chambres = mutableListOf<Chambre>()
    private val reservations = mutableListOf<Reservation>()

    fun ajouterChambre(chambre: Chambre) {
        chambres.add(chambre)
    }

    fun afficherChambresDisponibles(){
        chambres.filter { !it.estReservee }.forEach { it.toString() }
    }

    fun reserverChambre(numeroChambre: Int, client: Client, duree: Int) {
        val chambre = chambres.find { it.numero == numeroChambre }
            ?: throw Exception("Chambre inexistante.")
        chambre.reserver()
        val reservation = Reservation(client, chambre, duree)
        reservations.add(reservation)
    }

    fun annulerReservation(chambre: Chambre) {
        if (!chambres.contains(chambre)) {
            throw Exception("Chambre inexistante.")
        }
        chambre.annulerReservation()
        reservations.removeIf { it.chambre == chambre }
    }

    fun afficherReservations() {
        for (reservation in reservations) {
            println(reservation)
        }
    }
}