package monde

import monstre.EspeceMonstre

/**
 * Représente une zone du monde dans laquelle le joueur peut se déplacer
 * et rencontrer différentes espèces de monstres.
 *
 * Chaque zone possède une expérience associée et une liste d'espèces de monstres
 * pouvant y être rencontrées. Les zones peuvent être reliées entre elles afin
 * de permettre au joueur de progresser dans le monde du jeu.
 *
 * @property id L'identifiant unique de la zone.
 * @property nom Le nom de la zone.
 * @property expZone Le nombre de points d'expérience associés à la zone.
 * @property especesMonstres La liste des espèces de monstres pouvant être rencontrées dans cette zone.
 * Par défaut, cette liste est vide.
 * @property zoneSuivante La zone accessible après cette zone, ou null si aucune zone ne la suit.
 * @property zonePrecedante La zone accessible avant cette zone, ou null si aucune zone ne la précède.
 *
 */

class Zone (
    val id : Int,
    val nom : String,
    val expZone : Int,
    val especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedante: Zone? = null

    // TODO genereMonstre()
    // TODO rencontreMonstre()

)