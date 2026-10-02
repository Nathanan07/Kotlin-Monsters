package Jeu

import dressseur.Entraineur
import especeAquamy
import especeFlamkip
import especeSpringleaf
import monde.Zone
import monstre.IndividuMonstre

class Partie (var id : Int,
              var joueur : Entraineur,
              var zone: Zone) {

    fun choixStarter() {
        val monstre1 = IndividuMonstre(1, "springleaf", 2145.53, especeSpringleaf)
        val monstre2 = IndividuMonstre(2, "Flamkip", 1800.95, especeFlamkip)
        val monstre3 = IndividuMonstre(3, "Aquamy", 2500.17, especeAquamy)

        var choixSelection : Int

        var starter : IndividuMonstre

        do {
            println(monstre1)
            println(monstre1)
            println(monstre1)

            println("Selectionner votre monstre : (1..3)")

            choixSelection = readln().toIntOrNull() ?: 0

        } while (choixSelection !in 1..3)

        starter = when (choixSelection) {
            1 -> monstre1
            2 -> monstre2
            else -> monstre3
        }

        starter.renommer()

        joueur.equipeMonstre.add(starter)
        starter.entraineur = joueur

    }

    fun modifierOrdreEquipe() {

    }

    fun examineEquipe() {

    }

    fun jouer() {

    }

}