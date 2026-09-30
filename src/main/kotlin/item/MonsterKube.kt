package item

import dressseur.Entraineur
import joueur
import monstre.IndividuMonstre
import kotlin.random.Random

class MonsterKube (
    id: Int,
    nom: String,
    description: String,
    var chanceCapture: Double,
    val name : IndividuMonstre,

) : Item(id, nom, description), Utilisable {
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