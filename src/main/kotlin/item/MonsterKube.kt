package item

import dressseur.Entraineur
import joueur
import monstre.IndividuMonstre
import kotlin.random.Random

/**

Représente un Monster Kube utilisable pour tenter de capturer un monstre.

Un Monster Kube possède un identifiant, un nom, une description ainsi

qu'une chance de capture. Il peut être utilisé sur un monstre afin

de tenter de le capturer et de l'ajouter à l'équipe ou à la boîte

du joueur.

@property id L'identifiant unique du Monster Kube.

@property nom Le nom du Monster Kube.

@property description La description du Monster Kube.

@property chanceCapture La probabilité de réussite de la capture.

@property name Le monstre associé au Monster Kube.
 */

class MonsterKube (
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double,
    val name : IndividuMonstre,

) : Item(id, nom, description), Utilisable {

    /**

    Utilise le Monster Kube sur un monstre afin de tenter de le capturer.

    La méthode vérifie d'abord si le monstre possède déjà un entraîneur.

    Si c'est le cas, il ne peut pas être capturé.

    Sinon, un nombre aléatoire est généré afin de déterminer si la capture

    réussit ou échoue. En cas de réussite, le monstre est renommé puis

    ajouté à l'équipe du joueur ou à sa boîte si l'équipe est déjà complète.

    Le monstre capturé est ensuite associé au joueur en tant qu'entraîneur.

    @param cible Le monstre sur lequel le Monster Kube est utilisé.

    @return true si la capture est réussie, false sinon.
     */

    override fun utiliser(cible: IndividuMonstre): Boolean {

        println("Vous lancez le Monster Kube !")

        if (cible.entraineur != null) {
            println("Le monstre ne peut pas être capturé.")
        } else {
            var nbAleatoire : Int = Random.nextInt(0, 100)
            if (nbAleatoire < chanceCapture) {
                println("Presque ! Le Kube n'a pas pu capturer le monstre !")
            } else {
                println("le monstre est capturé !")
                name.renommer()
            }
        }

        if (joueur.equipeMonstre.size >= 6) {
            joueur.boiteMonstre.add(cible)
        } else {
            joueur.equipeMonstre.add(cible)
        }

        cible.entraineur=joueur



        return TODO("Provide the return value")
    }

}