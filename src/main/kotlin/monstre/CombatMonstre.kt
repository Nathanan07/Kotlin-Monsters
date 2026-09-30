package monstre

import joueur
import monstre.IndividuMonstre
import kotlin.math.exp

class CombatMonstre (var monstreJoueur : IndividuMonstre, var monstreSauvage : IndividuMonstre) {
    var round : Int = 1

    fun gameOver(): Boolean {
        var defaite : Boolean = false
        if (monstreJoueur.pv < 0) {
            defaite = true
            return defaite
        } else {
            return false
        }
    }

    fun joueurGagne() : Boolean{
        if (monstreSauvage.pv <= 0) {
            println("${joueur.nom} à gagné")
            var gainExp = monstreSauvage.exp * 0.20
            monstreSauvage.exp += gainExp
            println("${monstreSauvage.nom} gagne ${gainExp} exp.")
            return true
        } else {
            if (monstreSauvage.entraineur == joueur) {
                println("${monstreSauvage.nom} à été capturé")
                return true
            } else {
                return false
            }
        }
        return TODO("Provide the return value")
    }

    fun  actionAdversaire() {

    }

    fun actionJoueur() {

    }

    fun afficheCombat() {

    }

    fun jouer() {

    }

    fun lancerCombat() {

    }
}